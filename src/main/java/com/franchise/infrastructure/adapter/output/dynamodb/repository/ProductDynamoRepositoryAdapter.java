package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Product;
import com.franchise.domain.spi.IProductPersistencePort;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.ProductEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;
import com.franchise.infrastructure.helper.mapper.BranchMapper;
import com.franchise.infrastructure.helper.mapper.ProductMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;

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
        ProductEntity productEntity = ProductMapper.toNewProductEntity(product);
        return Mono.fromFuture(table.putItem(productEntity))
                .thenReturn(ProductMapper.toDomain(productEntity));
    }

    @Override
    public Mono<Product> delete(String productId, String branchId) {
        return this.validateKeys(productId, branchId)
                .then(Mono.defer(() ->
                        Mono.fromFuture(table.deleteItem(r -> r
                                .key(key -> key
                                        .partitionValue(productId)
                                        .sortValue(branchId)
                                )
                        ))))
                .map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return this.validateKeys(product.getId(), product.getBranchId())
                .then(Mono.defer(() -> {
                    UpdateItemEnhancedRequest<ProductEntity> request = this.buildUpdateItemEnhancedRequest(product);
                    return Mono.fromFuture(table.updateItem(request));
                }))
                .onErrorResume(ConditionalCheckFailedException.class, error -> Mono.empty())
                .map(ProductMapper::toDomain);
    }

    @Override
    public Flux<Product> getAllProductsByBranch(String branchId) {
        if (!branchId.startsWith(DynamoAdapterConstants.PREFIX_BRANCH)) {
            return Flux.error(new IllegalArgumentException(DynamoAdapterConstants.INVALID_BRANCH_ID));
        }

        ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .filterExpression(Expression.builder()
                        .expression("begins_with(partitionKey, :pkPrefix) AND sortKey = :sk")
                        .putExpressionValue(":pkPrefix", AttributeValue.fromS(DynamoAdapterConstants.PREFIX_PRODUCT))
                        .putExpressionValue(":sk", AttributeValue.fromS(branchId))
                        .build())
                .build();

        return Flux.from(table.scan(request).items())
                .map(ProductMapper::toDomain);
    }

    private UpdateItemEnhancedRequest<ProductEntity> buildUpdateItemEnhancedRequest(Product product) {
        ProductEntity productEntity = ProductMapper.toProductEntity(product);
        return UpdateItemEnhancedRequest
                .builder(ProductEntity.class)
                .item(productEntity)
                .ignoreNulls(true)
                .conditionExpression(Expression.builder()
                        .expression(DynamoAdapterConstants.ATTRIBUTE_EXISTS_PARTITION_KEY)
                        .build())
                .build();
    }

    private Mono<Void> validateKeys(String productId, String branchId) {
        if (!productId.startsWith(DynamoAdapterConstants.PREFIX_PRODUCT)) {
            return Mono.error(new IllegalArgumentException(DynamoAdapterConstants.INVALID_PRODUCT_ID));
        }
        if (!branchId.startsWith(DynamoAdapterConstants.PREFIX_BRANCH)) {
            return Mono.error(new IllegalArgumentException(DynamoAdapterConstants.INVALID_BRANCH_ID));
        }

        return Mono.empty();
    }

}
