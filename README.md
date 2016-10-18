# Metasfresh Front-end Application

[![build status](https://git.jazzy.pro/metasfresh/meta-frontend/badges/dev/build.svg)](https://git.jazzy.pro/metasfresh/meta-frontend/commits/dev)

## How to run client at first time
- Install dependencies
> npm install


- Create config. In that case run:
> cp src/config.js.dist src/config.js

## How to run dev env
- First make sure you have installed all of dependencies by:
> npm install

- Then remember of creating config: 
> cp src/config.js.dist src/config.js

- Then you should run node server by:
> npm start

## In case of build static version execute (you're gonna need webpack installed globally):
> webpack --config webpack.prod.js

### Notice: CI/CD legacy

Submodule meta-frontend-ansible.git and .gitlab-ci.yml file are legacy of CI/CD.
