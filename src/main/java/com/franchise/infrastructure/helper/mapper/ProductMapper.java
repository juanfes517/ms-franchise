package com.franchise.infrastructure.helper.mapper;

import com.franchise.domain.model.Product;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.ProductEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;

import java.util.UUID;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductEntity toProductEntity(Product product) {
        return ProductEntity.builder()
                .partitionKey(DynamoAdapterConstants.PREFIX_PRODUCT + UUID.randomUUID())
                .sortKey(product.getBranchId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }

    public static Product toDomain(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getPartitionKey())
                .branchId(productEntity.getSortKey())
                .name(productEntity.getName())
                .stock(productEntity.getStock())
                .build();
    }
}
