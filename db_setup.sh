#!/usr/bin/env bash

docker-compose down

docker-compose up --build -d

docker exec monesechallenge_db /wait_for_db.sh

mvn sql:execute
