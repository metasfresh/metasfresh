var webpack = require('webpack');

const plugins = [
  new webpack.NoEmitOnErrorsPlugin(),
  new webpack.DefinePlugin({
    PLUGINS: JSON.stringify([]),
  }),
];

module.exports = {
  mode: 'development',
  devtool: 'eval',
  plugins,
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
      },
    ],
  },
};
