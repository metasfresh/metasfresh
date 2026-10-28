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
    // A numeric/decimal input must never be <input type="number">: an HTML number input ties its
    // accepted decimal separator to the device browser language, so on a non-English device a typed
    // decimal point/comma is rejected and the entry is silently wiped or inflated. Use
    // <input type="text" inputMode="decimal"> via the shared QtyInputField / src/utils/decimalInput.js.
    // (The POS cash-journal inputs still carry type="number"; they are exempted in overrides below
    // until they migrate, so this rule only guards new/changed inputs.)
    'no-restricted-syntax': [
      'error',
      {
        selector: "JSXOpeningElement[name.name='input'] JSXAttribute[name.name='type'] Literal[value='number']",
        message:
          'Do not use <input type="number"> for numeric/decimal entry: its accepted decimal separator follows the device browser language, so a typed decimal is rejected on non-English devices and the entry is wiped/inflated. Use <input type="text" inputMode="decimal"> via QtyInputField / src/utils/decimalInput.js.',
      },
      {
        selector:
          "JSXOpeningElement[name.name='input'] JSXAttribute[name.name='type'] JSXExpressionContainer Literal[value='number']",
        message:
          'Do not use <input type={...}> resolving to "number" for numeric/decimal entry: its accepted decimal separator follows the device browser language. Use <input type="text" inputMode="decimal"> via QtyInputField / src/utils/decimalInput.js.',
      },
    ],
  },
  plugins: ['prettier', 'react', 'jest'],
  overrides: [
    {
      // The POS cash-journal amount inputs and the NUMBER attribute input of the Material Receipt
      // attribute editor (EditableAttributesSection, not present on the branches that introduced the
      // ban) still use type="number"; they are the deferred follow-up surface for the decimal-input
      // migration (the shop this fix targets runs no POS terminals).
      // Exempt them until they move to type="text" inputMode="decimal", so the ban above only guards
      // new/changed inputs rather than failing the build on this pre-existing debt.
      files: [
        'src/apps/pos/containers/cash_journal/POSCashJournalClosingModal.jsx',
        'src/apps/pos/containers/cash_journal/POSCashJournalOpenModal.jsx',
        'src/components/attributes/EditableAttributesSection.jsx',
      ],
      rules: { 'no-restricted-syntax': 'off' },
    },
  ],
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
