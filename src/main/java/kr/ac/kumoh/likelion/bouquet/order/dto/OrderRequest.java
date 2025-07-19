package kr.ac.kumoh.likelion.bouquet.order.dto;

import java.util.List;

public record OrderRequest(
        Long shopId,
        List<Item> items,
        String content
) {
    public record Item(
        Long stockId,
        Integer quantity
    ) {
    }
}
