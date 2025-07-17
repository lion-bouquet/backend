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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final ShopRepository shopRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse save(Long userId, OrderRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        FlowerShop shop = shopRepository.findById(request.shopId())
                .orElseThrow(() -> new ServiceException(ErrorCode.SHOP_NOT_FOUND));

        Order order = Order.builder()
                .user(user)
                .shop(shop)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();

        for (OrderRequest.Item item : request.items()) {
            Stock stock = stockRepository.findById(item.stockId())
                    .orElseThrow(() -> new ServiceException(ErrorCode.ORDER_NOT_FOUND));

            if (!Objects.equals(stock.getShop().getId(), stock.getShop().getId())) {
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
        }

        orderRepository.save(order);
        return OrderResponse.from(order);
    }

    public OrderResponse getOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
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

        order.cancel();
    }
}
