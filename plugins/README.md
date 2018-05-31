
# Metasfresh Front-end Plugin system

Metasfresh ERP system can be further expanded with a set of plugins. This document describes the requirements and process of creating a custom plugin.

## Loading plugins in the application

All plugins are dynamically loaded on application start from separate script files. In order to pickup new plugins, user must :

1. Provide a plugins.js file, that will be leaded by the app. If file does not exist it can be created by copying the default config:

> cp /plugins.js.dist /dist/plugins.js

2. Add plugins names to the array inside the config, ie :

```javascript
//plugins.js

const PLUGINS = ['plugin1', 'plugin2'];
```

3. Copy your plugins scripts to folders named after values inserted in the config array and placed in the main plugins folder. Mind scripts are expected to have `index.js` name.

> cp index.js /plugins/plugin1/
> cp index.js /plugins/plugin2/

4. Build the application


## Building custom plugins

Since the plugins are imported as separate modules, they must be built in such a way that dynamic commonjs imports will work. Here's a short guide on how to configure [Webpack](https://webpack.js.org/) bundler with [Babel](https://babeljs.io/) precompiler in production mode (right now code minification is not supported for the plugins).

### Build configuration

For the purpose of this guide we will be using configurations as required by Babel 6.20.x and Webpack 4.7.x .

#### Babel

To compile javascript using Babel compiler you will need two plugins : `babel-plugin-add-module-exports` and `babel-plugin-syntax-dynamic-import`. Both can be installed via yarn or npm :

> npm install --save-dev babel-plugin-add-module-exports babel-plugin-syntax-dynamic-import

and then added to the `.babelrc` config file:

```javascript
{
  "plugins": [
    "add-module-exports",
    "babel-plugin-syntax-dynamic-import",
  ]
}
```

#### Webpack

This is a basic config for Webpack. One important thing to notice is the `libraryTarget` option for the output code.

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

### Plugin architecture basics

This section describes how the plugins code should be structured, available options, handling data etc.

#### API

Plugins need to provide a certain API to properly work with the Metasfresh application. This is a sample module code we will use to describe each of the options.

```javascript
const api = {
  // optional
  userDropdownLink: {
    text: 'MyPlugin',
    url: 'myplugin',
  },
  routes: [
    {
      path: '/myplugin',
      component: Main,
      breadcrumb: { caption: 'MainBreadcrumb', type: 'group' },
      // optional
      indexRoute: {
        component: IndexComponent,
      },
      childRoutes: [
        {
          path: '/myplugin/child-route',
          component: ChildComponent,
          breadcrumb: { caption: 'Some Child Route', type: 'page' },
        },
      ],
    },
  ],
  reducers: {
    name: 'myplugin',
    reducer,
  },
};
```

**userDropdownLink (optional)**

This option allows adding links to the user dropdown menu. It expects an object with two keys:
* text - what should be the link text
* url - path where the link should lead

**routes**

Metasfresh uses `react-router` (v3) for routing, which plugins can further extend. This option expects an array of [static routes](https://reacttraining.com/react-router/core/guides/philosophy/static-routing), which support all of the functionality provided by `react-router. Nested child routes require a full path, with parent's prefix, ie `/myparent/child`.

One additional option is the `breadcrumb` key :

```
breadcrumb: { caption: 'Some Child Route', type: 'page' },
```

As the name suggests this is what will be rendered in the breadcrumbs section. 
* caption - text displayed in the breadcrumb
* type - for branch routes use type `group`; for leaves use type `page`.

**reducers**



#### Data handling
```
    const { rawModal, modal, component, dispatch, breadcrumb } = this.props;
    const { store } = this.context;
    const TagName = component;
    const redirectPush = bindActionCreators(push, dispatch);

    return (
      <Container {...{ modal, rawModal }} breadcrumb={breadcrumb}>
        <div className="plugin-container" ref={c => (this.container = c)}>
          {TagName && (
            <TagName
              {...this.props}
              redirectPush={redirectPush}
              dispatch={dispatch}
              store={store}
            />
```
