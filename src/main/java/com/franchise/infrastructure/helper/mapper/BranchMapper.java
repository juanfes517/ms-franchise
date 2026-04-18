package com.franchise.infrastructure.helper.mapper;

import com.franchise.domain.model.Branch;
import com.franchise.infrastructure.adapter.output.dynamodb.entity.BranchEntity;
import com.franchise.infrastructure.helper.constants.DynamoAdapterConstants;

public class BranchMapper {

    private BranchMapper() {}

    public static BranchEntity toBranchEntity(Branch branch) {
        return BranchEntity.builder()
                .partitionKey(DynamoAdapterConstants.PREFIX_FRANCHISE + branch.getId())
                .sortKey(branch.getFranchiseId())
                .name(branch.getName())
                .build();
    }

    public static Branch toDomain(BranchEntity branchEntity) {
        return Branch.builder()
                .id(branchEntity.getPartitionKey())
                .name(branchEntity.getName())
                .franchiseId(branchEntity.getSortKey())
                .build();
    }
}
