[![Join the chat at https://gitter.im/metasfresh/metasfresh](https://badges.gitter.im/metasfresh/metasfresh.svg)](https://gitter.im/metasfresh/metasfresh?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Krihelimeter](http://krihelinator.xyz/badge/metasfresh/metasfresh-webui-frontend)](http://krihelinator.xyz)

# Metasfresh Front-end Application

## How to run client at first time
- Install dependencies
> npm install


- Create config. In that case run:
> cp src/config.js.dist src/config.js

## How to run dev env
- First make sure you have installed all of dependencies by:
> npm install

- Then remember of creating config:
> cp /config.js.dist /config.js

- Then you should run node server by:
> npm start

## Contribution info

Remember to ensure before contribution that your IDE supports `.editorconfig` file,
and if needed fix your file before commit changes.

## In case of build static version execute (you're gonna need webpack installed globally):
> webpack --config webpack.prod.js

## And after that we need `config.js` in `dist` folder
> cp /config.js.dist /dist/

### Notice: CI/CD legacy

Submodule meta-frontend-ansible.git and .gitlab-ci.yml file are legacy of CI/CD.
