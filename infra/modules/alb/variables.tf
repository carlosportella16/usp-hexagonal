variable "name" {
  type = string
  description = "Nome do Load Balancer"
}

variable "internal" {
  type = bool
  description = "Define se o Load Balancer é interno ou externo"
  default = false
}

variable "load_balancer_type" {
  type = string
  description = "Tipo de Load Balancer"
  default = "application"
}

variable "security_groups" {
  type = list(string)
  description = "Lista de IDs de Grupos de Segurança para o Load Balancer"
}

variable "subnets" {
  type = list(string)
  description = "Lista de IDs de Subnets para o Load Balancer"
}

variable "tags" {
  type = map(string)
  description = "Tags para o Load Balancer"
  default = {}
}

variable "port" {
  type = number
  description = "Porta do Target Group"
}

variable "vpc_id" {
  type = string
  description = "ID da VPC"
}