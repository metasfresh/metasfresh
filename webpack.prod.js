var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var WebpackGitHash = require('webpack-git-hash');
var commitHash = require('child_process')
  .execSync('git rev-parse --short HEAD')
  .toString();

module.exports = {
  devtool: 'cheap-module-source-map',
  entry: ['./src/index.jsx', './favicon.png'],
  output: {
    path: './dist',
    filename: 'bundle-[hash]-git-[githash].js',
    publicPath: '/',
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: JSON.stringify('production'),
      },
      COMMIT_HASH: JSON.stringify(commitHash),
    }),
    new HtmlWebpackPlugin({
      template: './index.html',
    }),
    new WebpackGitHash(),
  ],
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        loaders: ['babel'],
        include: path.join(__dirname, 'src'),
      },
      {
        test: /\.(jpg|png|svg|eot|woff|woff2|ttf|gif)$/,
        loader: 'file?name=[path][name].[ext]',
      },
      {
        test: /\.css$/,
        loaders: ['style-loader', 'css-loader', 'postcss-loader'],
      },
      {
        test: /\.html$/,
        loader: 'html',
      },
    ],
  },
  postcss: () => [
    require('postcss-import')({
      addDependencyTo: webpack,
      path: ['node_modules'],
    }),
    require('postcss-color-function'),
    require('postcss-url')(),
    require('autoprefixer')({ browsers: ['last 2 versions'] }),
    require('precss')(),
  ],
  resolve: {
    extensions: ['', '.js', '.json'],
  },
};
