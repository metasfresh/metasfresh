#!/bin/bash

NODE_VERSION=14
#export NODE_OPTIONS=--openssl-legacy-provider

set -e

npx "--package=node@${NODE_VERSION}" yarn install
npx "--package=node@${NODE_VERSION}" yarn start
