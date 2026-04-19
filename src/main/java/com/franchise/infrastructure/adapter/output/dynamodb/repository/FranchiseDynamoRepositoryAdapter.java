package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Franchise;
import com.franchise.domain.spi.IFranchisePersistencePort;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.FranchiseEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;
import com.franchise.infrastructure.helper.mapper.FranchiseMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

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
        FranchiseEntity franchiseEntity = FranchiseMapper.toFranchiseEntity(franchise);
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

}
