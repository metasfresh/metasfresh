var webpack = require('webpack');
var WebpackDevServer = require('webpack-dev-server');
var config = require('./webpack.config');
const path = require('path');

// var listenHost = process.env.DOCKER ? '0.0.0.0' : 'localhost';
var listenHost = '0.0.0.0';

const devServer = new WebpackDevServer(
  {
    host: listenHost,
    port: process.env.PORT || 3000,
    allowedHosts: 'all', // 👈 allows any external host (like myapp3000.loca.lt)
    static: {
      directory: path.join(__dirname, ''),
      publicPath: config.output.publicPath,
    },
    hot: true,
    historyApiFallback: true,
    // Proxy is opt-in: set API_PROXY_TARGET to enable proxying /rest and /stomp
    // to a local backend (e.g., API_PROXY_TARGET=http://localhost:8080).
    ...(process.env.API_PROXY_TARGET
      ? {
          proxy: [
            {
              context: ['/rest', '/stomp'],
              target: process.env.API_PROXY_TARGET,
              changeOrigin: false,
              ws: true,
              cookieDomainRewrite: '',
              cookiePathRewrite: '/',
              onProxyRes: function (proxyRes) {
                var setCookie = proxyRes.headers['set-cookie'];
                if (setCookie) {
                  proxyRes.headers['set-cookie'] = setCookie.map(function (
                    cookie
                  ) {
                    return cookie
                      .replace(/;\s*SameSite=[^;]*/gi, '')
                      .replace(/;\s*Secure/gi, '');
                  });
                }
              },
            },
          ],
        }
      : {}),
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
  return console.warn(
    'Listening at http://' + listenHost + ':' + (process.env.PORT || 3000) + '/'
  );
});
