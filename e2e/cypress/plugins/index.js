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

const webpackPre = require('@cypress/webpack-preprocessor');
const webpack = require('webpack');
const { initPlugin } = require('cypress-plugin-snapshots/plugin');
const task = require('cypress-skip-and-only-ui/task');
const ourConfig = require('../config.js');

module.exports = (on, config) => {
  const options = {
    // send in the options from your webpack.config.js, so it works the same
    // as your app's code
    webpackOptions: require('../../webpack.config'),
    watchOptions: {},
  };

  const opts = [
    ...options.webpackOptions.plugins,
    new webpack.DefinePlugin({
      config: JSON.stringify(config),
    }),
  ];

  options.webpackOptions.plugins = opts;
  on('file:preprocessor', webpackPre(options));
  on('task', task);

  initPlugin(on, config);

  // Uncomment for disabling CORS protection in chrome
  // on('before:browser:launch', (browser = {}, args) => {
  //   if (browser.name === 'chrome') {
  //     args.push('--disable-web-security --user-data-dir=/tmp/chrome')

  //     // whatever you return here becomes the new args
  //     return args
  //   }
  // })

  // modify base url if it exists in our config.js file located in /e2e/cypress/config.js
  if (ourConfig.FRONTEND_URL) {
    config.baseUrl = ourConfig.FRONTEND_URL;
  }

  return config;
};
