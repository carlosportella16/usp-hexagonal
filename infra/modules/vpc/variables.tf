variable "vpc_cidr_block" {
  type        = string
  description = "CIDR principal da VPC."
  default     = "10.0.0.0/16"
}

# Mapeia cada AZ para um bloco CIDR de subnet pública
variable "public_subnets_cidrs" {
  type = map(string)
  description = <<EOT
Mapeamento de AZ para CIDR de subnet pública.
Exemplo:
{
  "us-east-1a" = "10.0.0.0/24",
  "us-east-1b" = "10.0.1.0/24"
}
EOT
  default = {
    "us-east-1a" = "10.0.0.0/24"
    "us-east-1b" = "10.0.1.0/24"
  }
}

# Mapeia cada AZ para um bloco CIDR de subnet privada
variable "private_subnets_cidrs" {
  type = map(string)
  description = <<EOT
Mapeamento de AZ para CIDR de subnet privada.
Exemplo:
{
  "us-east-1a" = "10.0.2.0/24",
  "us-east-1b" = "10.0.3.0/24"
}
EOT
  default = {
    "us-east-1a" = "10.0.2.0/24"
    "us-east-1b" = "10.0.3.0/24"
  }
}
