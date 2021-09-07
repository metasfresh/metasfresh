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
        test: /\.js|\.jsx?$/,
        loader: 'babel-loader',
      },
      {
        test: /\.html$/,
        loader: 'html-loader',
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
                require('autoprefixer')({
                  overrideBrowserslist: ['last 2 versions'],
                }),
              ],
            },
          },
        ],
      },
    ],
  },
};
