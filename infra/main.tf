terraform {
  required_version = ">= 1.0.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  backend "s3" {
    bucket         = "carlosportella16-sa-east-1-terraform-state-file"  # Substitua pelo seu bucket
    key            = "prod/infra.tfstate"         # Caminho do arquivo de state no bucket
    region         = "sa-east-1"                   # Região do bucket S3
    dynamodb_table = "carlosportella-sa-east-1-terraform-lock"          # Tabela DynamoDB para lock
    encrypt        = true
  }
}


provider "aws" {
  region  = var.aws_region
}

# Cria VPC
module "vpc" {
  source                = "./modules/vpc"
  vpc_name              = var.vpc_name
  vpc_cidr              = var.vpc_cidr
  az_count              = var.az_count
  public_subnet_cidrs  = var.public_subnet_cidrs
  private_subnet_cidrs = var.private_subnet_cidrs
  enable_dns_hostnames = var.enable_dns_hostnames
  enable_dns_support   = var.enable_dns_support
  tags                  = var.tags
}

# Security Group do ALB
resource "aws_security_group" "alb_sg" {
  name        = "${var.application_name}-alb-sg"
  description = "SG do ALB"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description = "HTTP inbound"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = var.tags
}

# Security Group do ECS Service
resource "aws_security_group" "ecs_service_sg" {
  name        = "${var.application_name}-ecs-service-sg"
  description = "SG do ECS Service"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description     = "Inbound do ALB na porta 8080"
    from_port       = 8080
    to_port         = 8080
    protocol        = "tcp"
    security_groups = [aws_security_group.alb_sg.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = var.tags
}

module "cw_logs" {
  source      = "./modules/cloudwatch-logs"
  log_prefix  = var.application_name
  retention   = var.log_retention
}

module "alb" {
  source             = "./modules/alb"
  name               = "${var.application_name}-alb"
  vpc_id             = module.vpc.vpc_id
  subnets            = module.vpc.public_subnets
  security_groups    = [aws_security_group.alb_sg.id]
  port               = var.container_port
  #health_check_path  = "/actuator/health" # Remova se não for usar
  #target_port        = var.container_port # Remova se não for usar
}

module "ecr" {
  source            = "./modules/ecr"
  repository_name   = var.application_name
  image_tag_mutable = true
}

module "ecs" {
  source                  = "./modules/ecs"
  cluster_name            = "${var.application_name}-cluster"
  service_name            = "${var.application_name}-service"
  task_cpu                = var.task_cpu
  task_memory             = var.task_memory
  desired_count           = var.desired_count
  max_capacity            = var.max_capacity
  min_capacity            = var.min_capacity
  container_image         = "${module.ecr.repository_url}:${var.image_tag}"
  container_port          = var.container_port
  log_group_name          = module.cw_logs.log_group_name
  vpc_id                  = module.vpc.vpc_id
  subnet_ids              = module.vpc.private_subnets
  security_groups         = [aws_security_group.ecs_service_sg.id]
#  alb_target_group_arn    = module.alb.target_group_arn
  cpu_utilization_target  = var.cpu_utilization_target
  region                  = var.aws_region
}