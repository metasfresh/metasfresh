import express from 'express';
import { createProxyMiddleware } from 'http-proxy-middleware';

const app = express();

if (typeof process.env.DEV_SERVER === 'undefined') {
  console.log('####  You do not have a DEV_SERVER env variable set. ####');
  console.log('####  Mirage mockups will be used by default.        ####');
  console.log(
    '####  To set it use `export DEV_SERVER=https://remotehost.com/` then start again the dev with `yarn start`   '
  );
} else {
  app.use('/api', createProxyMiddleware({ target: process.env.DEV_SERVER, changeOrigin: true }));
  app.use('/rest', createProxyMiddleware({ target: process.env.DEV_SERVER, changeOrigin: true }));
  app.listen(5000); // this has to match the proxy set in the package.json !
}
export default app;
