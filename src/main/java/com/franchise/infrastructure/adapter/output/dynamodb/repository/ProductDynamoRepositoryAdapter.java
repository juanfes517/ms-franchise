package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Product;
import com.franchise.domain.spi.IProductPersistencePort;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.ProductEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;
import com.franchise.infrastructure.helper.mapper.ProductMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
@RequiredArgsConstructor
public class ProductDynamoRepositoryAdapter implements IProductPersistencePort {

    private final DynamoDbEnhancedAsyncClient client;
    private DynamoDbAsyncTable<ProductEntity> table;

    @PostConstruct
    public void init() {
        table = client.table(DynamoAdapterConstants.DYNAMODB_TABLE_NAME, TableSchema.fromBean(ProductEntity.class));
    }

    @Override
    public Mono<Product> save(Product product) {
        ProductEntity productEntity = ProductMapper.toProductEntity(product);
        return Mono.fromFuture(table.putItem(productEntity))
                .thenReturn(ProductMapper.toDomain(productEntity));
    }
}
