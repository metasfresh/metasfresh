var webpack = require('webpack');
var WebpackDevServer = require('webpack-dev-server');
var config = require('./webpack.config');

var listenHost = process.env.DOCKER ? '0.0.0.0' : 'localhost';

new WebpackDevServer(webpack(config), {
  publicPath: config.output.publicPath,
  hot: true,
  historyApiFallback: true,
}).listen(3000, listenHost, function(err) {
  if (err) {
    // eslint-disable-next-line no-console
    return console.error(err);
  }

  // eslint-disable-next-line no-console
  return console.warn('Listening at http://' + listenHost + ':3000/');
});
