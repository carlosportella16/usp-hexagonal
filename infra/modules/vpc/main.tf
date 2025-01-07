resource "aws_vpc" "this" {
  cidr_block           = var.vpc_cidr
  enable_dns_support   = var.enable_dns_support
  enable_dns_hostnames = var.enable_dns_hostnames

  tags = merge(
    {
      Name = var.vpc_name
    },
    var.tags,
  )
}

resource "aws_internet_gateway" "this" {
  vpc_id = aws_vpc.this.id

  tags = merge(
    {
      Name = "${var.vpc_name}-igw"
    },
    var.tags,
  )
}

resource "aws_subnet" "public" {
  for_each = toset(var.public_subnet_cidrs)

  cidr_block             = each.value
  vpc_id                 = aws_vpc.this.id
  map_public_ip_on_launch = true

  tags = merge(
    {
      Name = "${var.vpc_name}-public-${each.key}"
    },
    var.tags,
  )
}

resource "aws_subnet" "private" {
  for_each = toset(var.private_subnet_cidrs)

  cidr_block             = each.value
  vpc_id                 = aws_vpc.this.id
  map_public_ip_on_launch = false

  tags = merge(
    {
      Name = "${var.vpc_name}-private-${each.key}"
    },
    var.tags,
  )
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.this.id

  tags = merge(
    {
      Name = "${var.vpc_name}-public-rt"
    },
    var.tags,
  )
}

resource "aws_route" "public_internet_access" {
  route_table_id         = aws_route_table.public.id
  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = aws_internet_gateway.this.id
}

resource "aws_route_table_association" "public_association" {
  for_each = aws_subnet.public

  subnet_id      = each.value.id
  route_table_id = aws_route_table.public.id
}

resource "aws_eip" "nat" {
  domain = "vpc"
  depends_on = [aws_internet_gateway.this]

  tags = merge(
    {
      Name = "${var.vpc_name}-nat-eip"
    },
    var.tags,
  )
}

resource "aws_nat_gateway" "this" {
  allocation_id = aws_eip.nat.id
  subnet_id     = element(values(aws_subnet.public), 0).id
  depends_on    = [aws_internet_gateway.this]

  tags = merge(
    {
      Name = "${var.vpc_name}-nat-gw"
    },
    var.tags,
  )
}

resource "aws_route_table" "private" {
  vpc_id = aws_vpc.this.id

  tags = merge(
    {
      Name = "${var.vpc_name}-private-rt"
    },
    var.tags,
  )
}

resource "aws_route" "private_outbound" {
  route_table_id         = aws_route_table.private.id
  destination_cidr_block = "0.0.0.0/0"
  nat_gateway_id         = aws_nat_gateway.this.id
}

resource "aws_route_table_association" "private_association" {
  for_each = aws_subnet.private

  subnet_id      = each.value.id
  route_table_id = aws_route_table.private.id
}