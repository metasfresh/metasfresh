var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    devtool: 'cheap-module-source-map',
    entry: [
        './src/index.jsx',
        './favicon.png'
    ],
    output: {
        path: './dist',
        filename: 'bundle[hash].js',
        publicPath: '/'
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env': {
                'NODE_ENV': JSON.stringify('production')
            }
        }),
        new HtmlWebpackPlugin({
            template: './index.html'
        })
    ],
    module: {
        loaders: [{
            test: /\.jsx?$/,
            loaders: ['react-hot', 'babel'],
            include: path.join(__dirname, 'src')
        }, {
            test: /\.(jpg|png|svg|eot|woff|woff2|ttf|gif)$/,
            loader: 'file?name=[path][name].[ext]'
        }, {
            test: /\.css$/,
            loaders: ["style-loader","css-loader","postcss-loader"]
        }, {
            test: /\.html$/,
            loader: 'html'
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
