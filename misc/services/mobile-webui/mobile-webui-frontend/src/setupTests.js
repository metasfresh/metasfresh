global.config = {
  SERVER_URL: 'http://api.test.url',
};

// Jest runs on Node 14 (docker-builds/Dockerfile.mobile: node:14-bullseye), which lacks String.prototype.replaceAll
// (Node 15+). The app targets browsers, which all have it (e.g. src/utils/numbers.js), so provide it for the test env.
if (!String.prototype.replaceAll) {
  // eslint-disable-next-line no-extend-native
  String.prototype.replaceAll = function (search, replacement) {
    if (search instanceof RegExp) {
      if (!search.global) {
        throw new TypeError('replaceAll must be called with a global RegExp');
      }
      return this.replace(search, replacement);
    }
    return this.split(String(search)).join(replacement);
  };
}
