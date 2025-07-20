package kr.ac.kumoh.likelion.bouquet.order.service;

import kr.ac.kumoh.likelion.bouquet.global.base.exception.ErrorCode;
import kr.ac.kumoh.likelion.bouquet.global.base.exception.ServiceException;
import kr.ac.kumoh.likelion.bouquet.order.domain.Order;
import kr.ac.kumoh.likelion.bouquet.order.domain.OrderDetail;
import kr.ac.kumoh.likelion.bouquet.order.domain.OrderStatus;
import kr.ac.kumoh.likelion.bouquet.order.dto.OrderRequest;
import kr.ac.kumoh.likelion.bouquet.order.dto.OrderResponse;
import kr.ac.kumoh.likelion.bouquet.order.repository.OrderRepository;
import kr.ac.kumoh.likelion.bouquet.shop.domain.FlowerShop;
import kr.ac.kumoh.likelion.bouquet.shop.repository.ShopRepository;
import kr.ac.kumoh.likelion.bouquet.stock.domain.Stock;
import kr.ac.kumoh.likelion.bouquet.stock.repository.StockRepository;
import kr.ac.kumoh.likelion.bouquet.user.domain.User;
import kr.ac.kumoh.likelion.bouquet.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.TemporalUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final ShopRepository shopRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    private static final String CHARACTERS = "0123456789";
    private static final int LENGTH = 4;

    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        FlowerShop shop = shopRepository.findById(request.shopId())
                .orElseThrow(() -> new ServiceException(ErrorCode.SHOP_NOT_FOUND));

        String orderCode = generateOrderCode();
        Order order = Order.builder()
                .orderCode(orderCode)
                .user(user)
                .shop(shop)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .phoneNumber(request.phone())
                .content(request.request())
                .build();

        long duration = 0L;
        for (OrderRequest.Item item : request.items()) {
            Stock stock = stockRepository.findById(item.stockId())
                    .orElseThrow(() -> new ServiceException(ErrorCode.ORDER_NOT_FOUND));

            if (!Objects.equals(stock.getShop().getId(), shop.getId())) {
                throw new ServiceException(ErrorCode.SHOP_ID_MISMATCH);
            }

            if (!Boolean.TRUE.equals(stock.getStatus())) {
                throw new ServiceException(ErrorCode.STOCK_NOT_AVAILABLE);
            }

            int unitPrice = stock.getPrice().intValue();

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .stock(stock)
                    .unitPrice(unitPrice)
                    .quantity(item.quantity())
                    .build();

            order.getOrderDetails().add(orderDetail);
            order.plusTotalPrice((long)unitPrice * item.quantity());

            duration += item.quantity();
        }

        // 주문이 들어오면 자동으로 수락하도록 함
        duration /= 10L;
        duration += 1L;
        order.accept(LocalDateTime.now().plusMinutes(30L).plusHours(duration));

        orderRepository.save(order);
        return OrderResponse.from(order);
    }

    private String generateOrderCode() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);

        String randomStr = "";
        Random random = new Random();
        do {
            // 숫자, 알파벳 대소문자로 이루어진 5자리 랜덤 문자열 생성
            StringBuilder sb = new StringBuilder(LENGTH);
            for (int i = 0; i < LENGTH; i++) {
                int randomIndex = random.nextInt(CHARACTERS.length());
                char randomChar = CHARACTERS.charAt(randomIndex);
                sb.append(randomChar);
                randomStr = sb.toString();
            }
        } while (orderRepository.existsByOrderCode("BB-" + formattedDate + "-" + randomStr));

        return "BB-" + formattedDate + "-" + randomStr;
    }

    public OrderResponse getOrder(Long userId, String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new ServiceException(ErrorCode.ORDER_NOT_FOUND));

        if (!Objects.equals(order.getUser().getId(), userId)) {
            throw new ServiceException(ErrorCode.JWT_INVALID);
        }

        return OrderResponse.from(order);
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findAllByUserId(userId).stream().map(OrderResponse::from).toList();
    }

    @Transactional
    public void cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByUserIdAndOrderId(userId, orderId)
                .orElseThrow(() -> new ServiceException(ErrorCode.ORDER_NOT_FOUND));

        if (!Objects.equals(order.getUser().getId(), userId)) {
            throw new ServiceException(ErrorCode.JWT_INVALID);
        }

        if (!order.isCancellable()) {
            throw new ServiceException(ErrorCode.ORDER_CANCEL_DENIED);
        }

        order.cancel();
    }
}
