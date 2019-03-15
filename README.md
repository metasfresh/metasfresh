
# Metasfresh Front-end Application

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1caf080cc9364234ab636ed8ab4343e2)](https://www.codacy.com/app/metasfresh/metasfresh-webui-frontend?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=metasfresh/metasfresh-webui-frontend&amp;utm_campaign=Badge_Grade)
[![Join the chat at https://gitter.im/metasfresh/metasfresh-webui-frontend](https://badges.gitter.im/metasfresh/metasfresh-webui-frontend.svg)](https://gitter.im/metasfresh/metasfresh-webui-frontend?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Krihelimeter](http://krihelinator.xyz/badge/metasfresh/metasfresh-webui-frontend)](http://krihelinator.xyz)

## For webui-frontend developers

### Init
- Install dependencies
> npm install


- Create config. In that case run:
> cp src/config.js.dist src/config.js

### Dev environment

- install npm and node.js

- make sure you have all dependencies by:
> npm install

- Then remember of creating config:
> cp /config.js.dist /config.js

- Then you should run node server by:
> npm start

### Production environment
When running in production mode you will need to build the static version of the app and serve it from an http-compatible server. Here's a quick guide how you can run production mode locally.

#### Building
In case of static version building execute (you are going need [Webpack](https://www.npmjs.com/package/webpack) installed [globally](https://webpack.js.org/guides/installation/#global-installation)):
> webpack --config webpack.prod.js

And after that we need `config.js` in `dist` folder
> cp /config.js.dist /dist/

#### Running
The easiest way to test production build is by serving it via a simple [http-server](https://www.npmjs.com/package/http-server). You can install it globally with npm :
> npm install http-server -g

and then run it pointing to your dist folder:
> http-server ./dist

Now open your browser and go to `localhost:8080` to see the application running.

### Testing
Application comes with a set of tests, both unit as well as functional.

#### Cypress e2e tests

Right now, the actual cypress tests reside in the [metasfresh-webui-frontend](https://github.com/metasfresh/metasfresh-webui-frontend) repo.<br>
This is going to change as of issue [#7](https://github.com/metasfresh/metasfresh-e2e/issues/7) Move existing cypress tests into this repo.

We already moved our cypress-documentation from here to the metasfresh-e2e [README.md](https://github.com/metasfresh/metasfresh-e2e/blob/master/README.md).

### Contribution

Remember to ensure before contribution that your IDE supports `.editorconfig` file,
and if needed fix your file before commit changes.

Also remember to respect our code-schema rules. All of them are listed in __eslint__ and __stylelint__ config files. To use them, just run:
> npm run-script lint
> 
> npm run-script stylelint

(first one is also autofixing when possible)

### Dictionary

Project has a generic structure. Name of components and their containers should be strictly defined and keep for better understanding.

__MasterWindow__ - (e.g. `/window/143/1000000`) It is container for displaying single document view.

__DocList__ - (e.g. `/window/143/`) It's a view with a list of documents kept in table.

__DocumentList__ - It is a component that combining table for documents, filters, selection attributes, etc...

__Window__ - It is a component that is generating set of sections, columns, element's groups, element's lines and widgets (these are defined by backend layout)

__Widget__ - (__MasterWidget__, __RawWidget__) It is a component for getting user input.

__Header__ - It is a top navbar with logo.

__Subheader__ - It is a part of __Header__ and is toggled by *button with a home icon*.

__Sidelist__ - Toggled by *button with hamburger menu icon* in __Header__. It is collapsing panel situated on right side of 'browser window'.

__MenuOverlay__ - These are components that float over __Header__ and contain navigation links, triggered from breadcrumb.

__SelectionAttributes__ - It is a panel that might contain __Widgets__ and it is a side by side table in __DocumentList__.

### Schema
- MasterWindow
    - Container
    - Window
- DocList
    - Container
    - DocumentList
---
- Container
    - Header
    - Modal
    - RawModal
- Window
    - Widget
    - Tabs
- DocumentList
    - Table
    - Filters
    - SelectionAttributes
---
- Header
    - Subheader
    - Sidelist
    - Breadcrumb
        - MenuOverlay
- Modal
    - Window
    - Process
- RawModal
    - DocumentList

## For webui-api developers

If you are developing against the [metasfresh-webui-api](https://github.com/metasfresh/metasfresh-webui-api), 
you might want to run the webui-frontend without locally installing node and npm.
If you have a docker host, you can do so by checking out this repository and then following the instructions in the docker file `docker/nginx/Dockerfile`.
