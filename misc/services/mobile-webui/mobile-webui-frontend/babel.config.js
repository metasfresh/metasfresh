// babel.config.js
module.exports = {
    presets: [
      [
        '@babel/preset-env',
        { useBuiltIns: 'usage', corejs: 3.16, modules: 'commonjs' },
      ],
      ['@babel/preset-react'],
    ],
    plugins: [
      '@babel/plugin-syntax-dynamic-import',
      'dynamic-import-node',
      'react-hot-loader/babel',
    ],
};