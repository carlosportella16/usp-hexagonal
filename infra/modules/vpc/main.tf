terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
  required_version = ">= 1.3.0"
}

provider "aws" {
  region = "us-east-1"
}

################################
# 1) Criar a VPC
################################
resource "aws_vpc" "this" {
  cidr_block           = var.vpc_cidr_block
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name = "my-vpc"
  }
}

################################
# 2) Internet Gateway
################################
resource "aws_internet_gateway" "this" {
  vpc_id = aws_vpc.this.id

  tags = {
    Name = "my-igw"
  }
}

################################
# 3) Subnets Públicas
################################
resource "aws_subnet" "public" {
  # Agora, for_each é o map var.public_subnets_cidrs
  # key -> AZ (ex: "us-east-1a"), value -> CIDR (ex: "10.0.0.0/24")
  for_each = var.public_subnets_cidrs

  cidr_block              = each.value
  vpc_id                  = aws_vpc.this.id
  availability_zone       = each.key
  map_public_ip_on_launch = true

  tags = {
    Name = "public-${each.key}"
  }
}

################################
# 4) Subnets Privadas
################################
resource "aws_subnet" "private" {
  # Mesmo esquema: key -> AZ, value -> CIDR
  for_each = var.private_subnets_cidrs

  cidr_block              = each.value
  vpc_id                  = aws_vpc.this.id
  availability_zone       = each.key
  map_public_ip_on_launch = false

  tags = {
    Name = "private-${each.key}"
  }
}

################################
# 5) Route Table Pública
################################
resource "aws_route_table" "public" {
  vpc_id = aws_vpc.this.id

  tags = {
    Name = "public-rt"
  }
}

resource "aws_route" "public_internet_access" {
  route_table_id         = aws_route_table.public.id
  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = aws_internet_gateway.this.id
}

resource "aws_route_table_association" "public_association" {
  for_each      = aws_subnet.public
  subnet_id     = each.value.id
  route_table_id = aws_route_table.public.id
}

################################
# 6) Criar EIP + NAT Gateway
################################
resource "aws_eip" "nat" {
  domain = "vpc"

  depends_on = [aws_internet_gateway.this]

  tags = {
    Name = "nat-eip"
  }
}

resource "aws_nat_gateway" "this" {
  allocation_id = aws_eip.nat.id

  # Usa a primeira subnet pública para criar o NAT
  # (exemplo: pega a 1a entry do map "public". Se tiver 2 AZs públicas, aqui estará a do "us-east-1a")
  subnet_id  = values(aws_subnet.public)[0].id

  depends_on = [aws_internet_gateway.this]

  tags = {
    Name = "nat-gw"
  }
}

################################
# 7) Route Table Privada
################################
resource "aws_route_table" "private" {
  vpc_id = aws_vpc.this.id

  tags = {
    Name = "private-rt"
  }
}

resource "aws_route" "private_outbound" {
  route_table_id         = aws_route_table.private.id
  destination_cidr_block = "0.0.0.0/0"
  nat_gateway_id         = aws_nat_gateway.this.id
}

resource "aws_route_table_association" "private_association" {
  for_each      = aws_subnet.private
  subnet_id     = each.value.id
  route_table_id = aws_route_table.private.id
}
