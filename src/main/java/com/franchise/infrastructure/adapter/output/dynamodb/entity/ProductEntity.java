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
public class ProductEntity {

    private String partitionKey;
    private String sortKey; // Branch id
    private String name;
    private Integer stock;

    @DynamoDbPartitionKey
    public String getPartitionKey() {
        return partitionKey;
    }

    @DynamoDbSortKey
    public String getSortKey() {
        return sortKey;
    }
}
