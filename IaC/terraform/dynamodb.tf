terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.94.1"
    }
  }
}

provider "aws" {
  region = "us-east-2"
}

resource "aws_dynamodb_table" "franchise-system" {
  name         = "franchise-system"
  billing_mode = "PAY_PER_REQUEST"

  hash_key = "partitionKey"
  range_key = "sortKey"

  attribute {
    name = "partitionKey"
    type = "S"
  }

  attribute {
    name = "sortKey"
    type = "S"
  }

  tags = {
    Origin = "Terraform"
    Reason = "Accenture challenge"
  }
}