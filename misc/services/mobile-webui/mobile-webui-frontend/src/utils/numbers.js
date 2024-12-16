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
      const number = Number(string.replaceAll(',', '.'));
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
