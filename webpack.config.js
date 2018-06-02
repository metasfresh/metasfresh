var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var WebpackGitHash = require('webpack-git-hash');
var commitHash = require('child_process')
  .execSync('git rev-parse --short HEAD')
  .toString();
var fs = require('fs');

const plugins = [
  new webpack.DefinePlugin({
    COMMIT_HASH: JSON.stringify(commitHash),
  }),
  new webpack.HotModuleReplacementPlugin(),
  new webpack.NoEmitOnErrorsPlugin(),
  new HtmlWebpackPlugin({
    template: 'index.html',
  }),
  new WebpackGitHash(),
];

if (!fs.existsSync(path.join(__dirname, 'plugins.js'))) {
  plugins.push(
    new webpack.DefinePlugin({
      PLUGINS: JSON.stringify([]),
    })
  );
}

module.exports = {
  mode: 'development',
  devtool: 'eval',
  entry: [
    'webpack-dev-server/client?http://localhost:3000',
    'webpack/hot/only-dev-server',
    'babel-polyfill',
    './src/index.jsx',
  ],
  output: {
    path: '/',
    filename: 'bundle-[hash]-git-[githash].js',
    publicPath: '/',
  },
  plugins,
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
        include: path.join(__dirname, 'src'),
      },
      {
        test: /\.(jpg|png|svg|eot|woff|woff2|ttf|gif)$/,
        use: {
          loader: 'file-loader',
          options: {
            name: '[path][name].[hash].[ext]',
          },
        },
      },
      {
        test: /\.css$/,
        use: [
          'style-loader',
          { loader: 'css-loader', options: { importLoaders: 1 } },
          {
            loader: 'postcss-loader',
            options: {
              ident: 'postcss',
              plugins: () => [
                require('postcss-import')({
                  addDependencyTo: webpack,
                  path: ['node_modules', 'src/assets'],
                }),
                require('postcss-color-function'),
                require('postcss-url')(),
                require('precss')(),
                require('autoprefixer')({ browsers: ['last 2 versions'] }),
              ],
            },
          },
        ],
      },
      {
        test: /\.html$/,
        loader: 'html-loader',
      },
      {
        test: /\.json$/,
        loader: 'json-loader',
      },
    ],
  },
  resolve: {
    extensions: ['.js', '.json'],
  },
};
