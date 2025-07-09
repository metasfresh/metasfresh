#!/bin/bash

NODE_VERSION=20
echo "NODE version: ${NODE_VERSION}"

set -e

export NODE_OPTIONS=--openssl-legacy-provider
npx "--package=node@${NODE_VERSION}" yarn install
npx "--package=node@${NODE_VERSION}" yarn start
