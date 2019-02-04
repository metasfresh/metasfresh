var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var WebpackGitHash = require('webpack-git-hash');
var fs = require('fs');

// allow webpack.config.js to be evaluated if there is no git binary or if we are outside of the git repo;
// useful for cypress scenarios
const commitHash = computeCommitHash();
function computeCommitHash() {
  try {
    return require('child_process')
      .execSync('git rev-parse --short HEAD')
      .toString();
  } catch (ex) {
    return 'GIT_REV_NOT_AVAILABLE';
  }
}

const plugins = [
  new webpack.DefinePlugin({
    COMMIT_HASH: JSON.stringify(commitHash),
  }),
  new webpack.HotModuleReplacementPlugin(),
  new webpack.NoEmitOnErrorsPlugin(),
  new HtmlWebpackPlugin({
    template: 'index.html',
  }),
];

// WebpackGitHash attempts to run the git binary as well
if (commitHash !== 'GIT_REV_NOT_AVAILABLE') {
  plugins.push(new WebpackGitHash());
}

const entries = {
  index: [
    'webpack-dev-server/client?http://localhost:3000',
    'webpack/hot/only-dev-server',
    '@babel/polyfill',
    './src/index.jsx',
  ],
};

if (!fs.existsSync(path.join(__dirname, 'plugins.js'))) {
  plugins.push(
    new webpack.DefinePlugin({
      PLUGINS: JSON.stringify([]),
    })
  );
} else {
  entries.plugins = './plugins.js';
}

module.exports = {
  mode: 'development',
  devtool: 'eval',
  entry: entries,
  output: {
    path: '/',
    filename: '[name].bundle-[hash]-git-[githash].js',
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
    alias: {
      '@plugins': path.resolve('./plugins'),
    },
  },
};
