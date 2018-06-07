// ***********************************************************
// This example plugins/index.js can be used to load plugins
//
// You can change the location of this file or turn off loading
// the plugins file with the 'pluginsFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/plugins-guide
// ***********************************************************

// This function is called when a project is opened or re-opened (e.g. due to
// the project's config changing)

// module.exports = () => {
//   // `on` is used to hook into various events Cypress emits
//   // `config` is the resolved Cypress config
// };

const webpackPre = require('@cypress/webpack-preprocessor')
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const APIconfig = require('../config');

console.log('config: ', APIconfig);

module.exports = (on) => {
  const options = {
    // send in the options from your webpack.config.js, so it works the same
    // as your app's code
    webpackOptions: require('../../webpack.config'),
    watchOptions: {},
  }

  options.webpackOptions.plugins.push(
    new webpack.DefinePlugin({
      config: APIconfig,
    })
  );

  on('file:preprocessor', webpackPre(options))
}
