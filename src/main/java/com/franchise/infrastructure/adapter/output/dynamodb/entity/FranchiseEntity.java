package com.franchise.infrastructure.adapter.output.dynamodb.entity;


import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FranchiseEntity {

    private String partitionKey;
    private String sortKey;
    private String name;

    @DynamoDbPartitionKey
    public String getPartitionKey() {
        return partitionKey;
    }

    @DynamoDbSortKey
    public String getSortKey() {
        return sortKey;
    }
}
