#!/bin/bash
awslocal dynamodb create-table \
  --table-name franchise-system \
  --billing-mode PAY_PER_REQUEST \
  --attribute-definitions \
      AttributeName=partitionKey,AttributeType=S \
      AttributeName=sortKey,AttributeType=S \
  --key-schema \
      AttributeName=partitionKey,KeyType=HASH \
      AttributeName=sortKey,KeyType=RANGE