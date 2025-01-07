variable "vpc_name" {
  type = string
  description = "O nome da VPC"
  default = "vpc-app-hexagonal"
}

variable "vpc_cidr" {
  type = string
  description = "O bloco CIDR para a VPC"
  default = "10.0.0.0/16"
}

variable "az_count" {
  type = number
  description = "O número de zonas de disponibilidade"
  default = 2
}

variable "public_subnet_cidrs" {
  type = list(string)
  description = "Os blocos CIDR para as sub-redes públicas"
  default = ["10.0.1.0/24", "10.0.2.0/24"]
}

variable "private_subnet_cidrs" {
  type = list(string)
  description = "Os blocos CIDR para as sub-redes privadas"
  default = ["10.0.101.0/24", "10.0.102.0/24"]
}

variable "enable_dns_hostnames" {
  type = bool
  description = "Habilitar nomes de host DNS"
  default = true
}

variable "enable_dns_support" {
  type = bool
  description = "Habilitar suporte a DNS"
  default = true
}

variable "tags" {
  type = map(string)
  description = "Tags para a VPC"
  default = {"environment": "dev"}
}