#!/bin/sh

echo If you want to start over HTTPS then pls run:
echo export HTTPS=true

#export HTTPS=true
export PORT=3001; yarn install && yarn start

