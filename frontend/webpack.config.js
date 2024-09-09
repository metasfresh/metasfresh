const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { GitRevisionPlugin } = require('git-revision-webpack-plugin');
const fs = require('fs');
const MomentTimezoneDataPlugin = require('moment-timezone-data-webpack-plugin');
const MomentLocalesPlugin = require('moment-locales-webpack-plugin');

// check if we have already a config.js file. If we do not we need to create it otherwise webpack will complain that is missing
if (!fs.existsSync('config.js')) {
  fs.copyFileSync('config.js.dist', 'config.js');
}

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
    process: {
      env: {
        NODE_ENV: JSON.stringify('development'),
      },
    },
    COMMIT_HASH: JSON.stringify(commitHash),
  }),
  new webpack.NoEmitOnErrorsPlugin(),
  new HtmlWebpackPlugin({
    template: 'index.html',
  }),
  new MomentTimezoneDataPlugin({
    startYear: 2005,
    endYear: 2030,
  }),
  new MomentLocalesPlugin({
    localesToKeep: ['en', 'de', 'nl'],
  }),
];

// WebpackGitHash attempts to run the git binary as well
if (commitHash !== 'GIT_REV_NOT_AVAILABLE') {
  plugins.push(new GitRevisionPlugin());
}

const entries = {
  index: [
    'webpack-dev-server/client?http://localhost:3000',
    'webpack/hot/only-dev-server',
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
  bail: true,
  devtool: 'source-map',
  entry: entries,
  output: {
    path: '/',
    filename: '[name].bundle-[git-revision-hash]-git-[chunkhash].js',
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
        type: 'asset/resource',
        generator: {
          filename: '[path][name].[ext]',
        },
      },
      {
        test: /\.(css|scss)$/,
        use: [
          'style-loader',
          { loader: 'css-loader', options: { importLoaders: 1 } },
          {
            loader: 'postcss-loader',
            options: {
              postcssOptions: {
                plugins: {
                  'postcss-import': {
                    addDependencyTo: webpack,
                    path: ['node_modules', 'src/assets'],
                  },
                  'postcss-color-function': {},
                  'postcss-url': {},
                  precss: {},
                  autoprefixer: {
                    overrideBrowserslist: ['last 2 versions'],
                  },
                },
                ident: 'postcss',
              },
            },
          },
        ],
      },
      {
        type: 'javascript/auto',
        test: /\.(json)/,
        exclude: /(node_modules)/,
        generator: {
          filename: '[name].[ext]',
        },
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
