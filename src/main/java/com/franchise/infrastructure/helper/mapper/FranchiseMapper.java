package com.franchise.infrastructure.helper.mapper;

import com.franchise.domain.model.Franchise;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.FranchiseEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;

public class FranchiseMapper {

    private FranchiseMapper() {}

    public static FranchiseEntity toFranchiseEntity(Franchise franchise) {
        return FranchiseEntity.builder()
                .partitionKey(DynamoAdapterConstants.PREFIX_FRANCHISE + franchise.getId())
                .sortKey(franchise.getId())
                .name(franchise.getName())
                .build();
    }
}
