#!/bin/bash

NODE_VERSION=18

npx "--package=node@$NODE_VERSION" yarn install
npx "--package=node@$NODE_VERSION" yarn playwright test --ui

