FROM node:17

RUN npm install -g webpack webpack-cli

WORKDIR /app 
COPY package.json /app 
# COPY yarn.lock /app

RUN yarn install

COPY . /app

RUN yarn lint
# RUN yarn test --ci
RUN webpack --config webpack.prod.js
