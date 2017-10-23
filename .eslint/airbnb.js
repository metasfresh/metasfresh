/* Taken from https://github.com/airbnb/javascript/blob/master/packages/eslint-config-airbnb/index.js
 * excluding "react-a11y" rules
 */
module.exports = {
  extends: [
    'eslint-config-airbnb-base',
    'eslint-config-airbnb-base/rules/strict',
    'eslint-config-airbnb/rules/react',
  ].map(require.resolve),
};
