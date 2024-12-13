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
