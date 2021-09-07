var webpack = require('webpack');
var WebpackDevServer = require('webpack-dev-server');
var config = require('./webpack.config');
const path = require('path');

var listenHost = process.env.DOCKER ? '0.0.0.0' : 'localhost';

const devServer = new WebpackDevServer(
  {
    host: listenHost,
    port: 3000,
    static: {
      directory: path.join(__dirname, ''),
      publicPath: config.output.publicPath,
    },
    hot: true,
    historyApiFallback: true,
  },
  webpack(config)
);

/**
 * `listen` method is deprecated in v4 of wepack-dev-server. We need to use `startCallback` instead.
 */
devServer.startCallback((err) => {
  if (err) {
    // eslint-disable-next-line no-console
    return console.error(err);
  }
  // eslint-disable-next-line no-console
  return console.warn('Listening at http://' + listenHost + ':3000/');
});
