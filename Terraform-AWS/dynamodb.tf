resource "aws_dynamodb_table" "songs-table" {
  name         = "songs"
  billing_mode = "PAY_PER_REQUEST"

  hash_key = "id"

  attribute {
    name = "id"
    type = "S"
  }

  tags = {
    Name = "songs"
  }
}
resource "aws_dynamodb_table" "playlist" {
  name         = "playlist"
  billing_mode = "PAY_PER_REQUEST"

  hash_key = "id"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "userEmail"
    type = "S"
  }

  attribute {
    name = "publicplaylist"
    type = "S"
  }

  global_secondary_index {
    name            = "UserEmailIndex"
    hash_key        = "userEmail"
    projection_type = "KEYS_ONLY"
  }

  global_secondary_index {
    name            = "PublicPlaylistIndex"
    hash_key        = "publicplaylist"
    projection_type = "KEYS_ONLY"
  }
}
resource "aws_dynamodb_table" "user" {
  name         = "User"
  billing_mode = "PAY_PER_REQUEST"

  hash_key = "email"

  attribute {
    name = "email"
    type = "S"
  }

  tags = {
    Name = "User"
  }
}