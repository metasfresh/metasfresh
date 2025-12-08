#!/bin/bash

## NOTE: we cannot use node >16 yet
NODE_VERSION=16
#export HTTPS=true
export PORT=3001

set -e

npx "--package=node@${NODE_VERSION}" yarn install
npx "--package=node@${NODE_VERSION}" yarn start
