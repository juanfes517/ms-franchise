package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Franchise;
import com.franchise.domain.spi.IFranchisePersistencePort;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.FranchiseEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;
import com.franchise.infrastructure.helper.mapper.FranchiseMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;

@Repository
@RequiredArgsConstructor
public class FranchiseDynamoRepositoryAdapter implements IFranchisePersistencePort {

    private final DynamoDbEnhancedAsyncClient client;
    private DynamoDbAsyncTable<FranchiseEntity> table;

    @PostConstruct
    public void init() {
        table = client.table(DynamoAdapterConstants.DYNAMODB_TABLE_NAME, TableSchema.fromBean(FranchiseEntity.class));
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity franchiseEntity = FranchiseMapper.toNewFranchiseEntity(franchise);
        return Mono.fromFuture(table.putItem(franchiseEntity))
                .thenReturn(FranchiseMapper.toDomain(franchiseEntity));
    }

    @Override
    public Mono<Franchise> findById(String id) {
        if (!id.startsWith(DynamoAdapterConstants.PREFIX_FRANCHISE)) {
            return Mono.error(new IllegalArgumentException(DynamoAdapterConstants.INVALID_FRANCHISE_ID));
        }

        Key key = Key.builder()
                .partitionValue(id)
                .sortValue(id)
                .build();

        return Mono.fromFuture(table.getItem(key))
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> update(Franchise franchise) {
        if (!franchise.getId().startsWith(DynamoAdapterConstants.PREFIX_FRANCHISE)) {
            return Mono.error(new IllegalArgumentException(DynamoAdapterConstants.INVALID_FRANCHISE_ID));
        }

        FranchiseEntity franchiseEntity = FranchiseMapper.toFranchiseEntity(franchise);
        return Mono.fromFuture(table.putItem(r -> r
                        .item(franchiseEntity)
                        .conditionExpression(Expression.builder()
                                .expression(DynamoAdapterConstants.ATTRIBUTE_EXISTS_PARTITION_KEY)
                                .build())))
                .onErrorResume(ConditionalCheckFailedException.class, ex -> Mono.empty())
                .thenReturn(franchise);
    }

}
