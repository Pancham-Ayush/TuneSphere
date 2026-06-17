resource "aws_s3_bucket" "bucket" {
  bucket = var.bucket-name
  tags = {
    name = "Production"
  }
}

