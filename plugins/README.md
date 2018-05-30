
# Metasfresh Front-end Plugin system

Metasfresh ERP system can be further expanded with a set of plugins. This document describes the requirements and process of creating a custom plugin.

## Loading plugins in the application

All plugins are dynamically loaded on application start from separate script files. In order to pickup new plugins, user must :

*1. Provide a plugins.js file, that will be leaded by the app. If file does not exist it can be created by copying the default config:

> cp /plugins.js.dist /dist/plugins.js

*2. Add plugins names to the array inside the config, ie :

```javascript
//plugins.js

const PLUGINS = ['plugin1', 'plugin2'];
```

*3. Copy your plugins scripts to folders named after values inserted in the config array and placed in the main plugins folder:

> cp index.js /plugins/plugin1/
> cp index.js /plugins/plugin2/

*4. Build the application


## Building custom plugins

Since the plugins are imported as separate modules, they must be built in such a way that dynamic commonjs imports will work. Here's a short guide on how to configure webpack with babel precompiler in production mode (right now code minification is not supported for the plugins).

### Build configuration

For the purpose of this guide we will be using configurations as required by Babel 6.20.x and Webpack 4.7.x .

#### Babel

For babel you will need two plugins : `babel-plugin-add-module-exports` and `babel-plugin-syntax-dynamic-import`. Both can be installed via yarn or npm :

> npm install --save-dev babel-plugin-add-module-exports babel-plugin-syntax-dynamic-import

```javascript
{
  "plugins": [
    "add-module-exports",
    "babel-plugin-syntax-dynamic-import",
  ]
}
```

#### Webpack

```javascript
var path = require('path');

module.exports = {
    mode: 'production',
    entry: [
        './index.jsx'
    ],
    optimization: {
        minimize: false,
    },

    output: {
        path: path.join(__dirname, 'dist'),
        filename: 'index.js',
        publicPath: '/',
        libraryTarget: 'commonjs2'
    },
    module: {
        rules: [{
            test: /\.jsx?$/,
            loader: 'babel-loader',
            include: path.join(__dirname, 'src')
        },
    ]},
    resolve: {
        extensions: ['.js', '.json']
    }
};
```


