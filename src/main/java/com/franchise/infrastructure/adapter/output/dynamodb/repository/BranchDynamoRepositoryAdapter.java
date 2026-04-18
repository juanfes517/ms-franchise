package com.franchise.infrastructure.adapter.output.dynamodb.repository;

import com.franchise.domain.model.Branch;
import com.franchise.domain.spi.IBranchPersistencePort;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.BranchEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;
import com.franchise.infrastructure.helper.mapper.BranchMapper;
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
public class BranchDynamoRepositoryAdapter implements IBranchPersistencePort {

    private final DynamoDbEnhancedAsyncClient client;
    private DynamoDbAsyncTable<BranchEntity> table;

    @PostConstruct
    public void init() {
        table = client.table(DynamoAdapterConstants.DYNAMODB_TABLE_NAME, TableSchema.fromBean(BranchEntity.class));
    }

    @Override
    public Mono<Branch> save(Branch branch) {
        BranchEntity branchEntity = BranchMapper.toBranchEntity(branch);
        return Mono.fromFuture(table.putItem(branchEntity))
                .thenReturn(BranchMapper.toDomain(branchEntity));
    }

    @Override
    public Mono<Branch> findById(String id) {
        if (!id.startsWith(DynamoAdapterConstants.PREFIX_BRANCH)) {
            return Mono.error(new IllegalArgumentException(DynamoAdapterConstants.INVALID_BRANCH_ID));
        }

        QueryConditional query = QueryConditional.keyEqualTo(
                Key.builder()
                        .partitionValue(id)
                        .build());

        return Flux.from(table.query(query).items())
                .next()
                .map(BranchMapper::toDomain);
    }
}
