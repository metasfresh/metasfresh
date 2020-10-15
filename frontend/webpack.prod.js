var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var CopyWebpackPlugin = require('copy-webpack-plugin');
var fs = require('fs');
var WebpackGitHash = require('webpack-git-hash');
var commitHash = require('child_process')
  .execSync('git rev-parse --short HEAD')
  .toString();

const plugins = [
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
  new CopyWebpackPlugin({
    patterns: [
      {
        from: './plugins/**',
        to: './',
        globOptions: {
          ignore: ['*.md'],
        },
      },
    ],
  }),
];

if (!fs.existsSync(path.join(__dirname, 'dist/plugins.js'))) {
  plugins.push(
    new webpack.DefinePlugin({
      PLUGINS: JSON.stringify([]),
    })
  );
}

module.exports = {
  mode: 'production',
  devtool: 'cheap-module-source-map',
  entry: ['@babel/polyfill', './src/index.jsx', './favicon.png'],
  output: {
    path: path.join(__dirname, 'dist'),
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
        exclude: /\w*(logo)\w*\.(jpg|png)$/,
        use: {
          loader: 'file-loader',
          options: {
            name: '[path][name].[hash].[ext]',
          },
        },
      },
      {
        test: /\w*(logo)\w*\.(jpg|png)$/,
        use: {
          loader: 'file-loader',
          options: {
            name: '[path][name].[ext]',
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
                  path: ['node_modules'],
                }),
                require('postcss-color-function'),
                require('postcss-url')(),
                require('autoprefixer')({
                  overrideBrowserslist: ['last 2 versions'],
                }),
                require('precss')(),
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
        type: 'javascript/auto',
        test: /\.(json)/,
        exclude: /(node_modules)/,
        use: [
          {
            loader: 'file-loader',
            options: { name: '[name].[ext]' },
          },
        ],
      },
    ],
  },
  resolve: {
    extensions: ['.js', '.json'],
    alias: {
      '@plugins$': path.resolve('./plugins'),
    },
  },
};
