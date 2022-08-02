module.exports = {
  env: {
    browser: true,
    node: true,
    es6: true,
    'jest/globals': true,
  },
  parser: '@babel/eslint-parser',
  parserOptions: {
    requireConfigFile: 'false',
    babelOptions: { configFile: './babel.config.js' },
  },
  extends: ['eslint:recommended', 'plugin:react/recommended', 'prettier'],
  rules: {
    'prettier/prettier': [
      'error',
      {
        singleQuote: true,
        trailingComma: 'es5',
        endOfLine: 'auto',
        tabWidth: 2,
        arrowParens: 'always',
      },
    ],
    'react/prop-types': 'warn',
    'react/jsx-filename-extension': 'off',
    'react/prefer-stateless-function': 'off',
    'no-dupe-else-if': 'off', // this gives false positives on imports (also the next ones below - no-import-assign, no-setter-return)
    'no-import-assign': 'off',
    'no-setter-return': 'off',
    'react/display-name': 'warn',
  },
  plugins: ['prettier', 'react', 'jest'],
  settings: {
    react: {
      version: 'detect',
    },
  },
  globals: {
    localStorage: true,
    Promise: true,
    config: true,
    PLUGINS: true,
    context: true,
    before: true,
    document: true,
  },
};
