#!/bin/bash
sudo mvn clean install && sudo docker build ./vue-app --no-cache -t pl.carsoft.carbase/carbase-vuejs
