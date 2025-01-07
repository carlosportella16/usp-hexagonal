variable "aws_region" {
  type        = string
  default     = "sa-east-1"
  description = "Região AWS"
}

variable "aws_profile" {
  type        = string
  default     = "default"
  description = "Perfil AWS"
}

variable "application_name" {
  type        = string
  default     = "app-hexagonal"
  description = "Nome da aplicação"
}

variable "image_tag" {
  type        = string
  default     = "latest"
  description = "Tag da imagem no ECR"
}

variable "task_cpu" {
  type        = number
  default     = 256
  description = "CPU da task ECS"
}

variable "task_memory" {
  type        = number
  default     = 512
  description = "Memória da task ECS"
}

variable "desired_count" {
  type        = number
  default     = 1
  description = "Quantidade desejada de tarefas ECS"
}

variable "max_capacity" {
  type        = number
  default     = 2
  description = "Capacidade máxima para auto-scaling"
}

variable "min_capacity" {
  type        = number
  default     = 1
  description = "Capacidade mínima para auto-scaling"
}

variable "cpu_utilization_target" {
  type        = number
  default     = 50
  description = "Target de utilização de CPU para scaling"
}

variable "container_port" {
  type        = number
  default     = 8080
  description = "Porta do container"
}

variable "log_retention" {
  type        = number
  default     = 7
  description = "Retenção dos logs em dias"
}

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
  description = "Tags para os recursos"
  default = {
    environment = "dev"
  }
}