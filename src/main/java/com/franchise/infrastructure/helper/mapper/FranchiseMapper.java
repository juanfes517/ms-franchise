package com.franchise.infrastructure.helper.mapper;

import com.franchise.domain.model.Franchise;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.FranchiseEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;

import java.util.UUID;

public class FranchiseMapper {

    private FranchiseMapper() {}

    public static FranchiseEntity toFranchiseEntity(Franchise franchise) {
        String randomId = UUID.randomUUID().toString();
        return FranchiseEntity.builder()
                .partitionKey(DynamoAdapterConstants.PREFIX_FRANCHISE + randomId)
                .sortKey(DynamoAdapterConstants.PREFIX_FRANCHISE + randomId)
                .name(franchise.getName())
                .build();
    }

    public static Franchise toDomain(FranchiseEntity franchiseEntity) {
        return Franchise.builder()
                .id(franchiseEntity.getPartitionKey())
                .name(franchiseEntity.getName())
                .build();
    }
}
