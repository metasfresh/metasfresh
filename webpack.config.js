var path = require('path');
var webpack = require('webpack');

module.exports = {
    devtool: 'eval',
    entry: [
        'webpack-dev-server/client?http://localhost:3000',
        'webpack/hot/only-dev-server',
        './src/index.jsx'
    ],
    output: {
        path: '/',
        filename: 'bundle.js',
        publicPath: '/'
    },
    plugins: [
        new webpack.HotModuleReplacementPlugin(),
        new webpack.NoErrorsPlugin()
    ],
    module: {
        loaders: [{
            test: /\.jsx?$/,
            loaders: ['react-hot', 'babel'],
            include: path.join(__dirname, 'src')
        }, {
            test: /\.(jpg|png|svg|eot|woff|woff2|ttf|gif)$/,
            loader: 'file?name=[path][name].[hash].[ext]'
        }, {
            test: /\.css$/,
            loaders: ["style-loader","css-loader","postcss-loader"]
        }, {
            test: /\.html/,
            loader: 'file?name=[name].[ext]'
        }
    ]},
    postcss: () => [
        require('postcss-import')({ addDependencyTo: webpack, path: ['node_modules'] }),
        require('postcss-color-function'),
        require('postcss-url')(),
        require('autoprefixer')({ browsers: [ 'last 2 versions' ] }),
        require('precss')(),
    ],
    resolve: {
        extensions: ['', '.js', '.json']
    }
};
