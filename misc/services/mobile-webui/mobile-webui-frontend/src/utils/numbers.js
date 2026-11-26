export const round = (number, precision) => {
  return parseFloat(Number(number).toFixed(precision));
};

export const toNumberOrZero = (arg) => {
  try {
    return Number(arg);
  } catch (e) {
    return 0;
  }
};

export const toNumberOrNaN = (arg) => {
  if (arg == null) {
    return Number.NaN;
  } else if (typeof arg === 'number') {
    return arg;
  } else if (typeof arg === 'string') {
    const string = arg.trim();
    if (!string) {
      return 0;
    }

    try {
      const number = Number(string);
      if (Number.isFinite(number)) {
        return number;
      }
    } catch (ex) {
      // ignore it
    }

    try {
      // Use a global regex replace instead of String.prototype.replaceAll: the mobile build/test
      // image is node:14 (Dockerfile.mobile), where replaceAll (ES2021 / node 15+) is absent — under
      // node 14 it throws, gets swallowed by the catch, and this comma-decimal path silently returns
      // NaN. replace(/,/g, '.') is behaviour-identical and works on node 14 and every browser.
      const number = Number(string.replace(/,/g, '.'));
      if (Number.isFinite(number)) {
        return number;
      }
    } catch (ex) {
      // ignore it
    }

    return Number.NaN;
  } else {
    return Number(arg);
  }
};
