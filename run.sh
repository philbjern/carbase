#!/bin/bash
sudo mvn clean install && sudo docker-compose build && sudo sh entrypoint.sh
