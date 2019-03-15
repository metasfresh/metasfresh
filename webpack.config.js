var path = require('path');
var webpack = require('webpack');
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
  plugins,
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
        include: path.join(__dirname, 'src'),
      },
    ],
  },
};
