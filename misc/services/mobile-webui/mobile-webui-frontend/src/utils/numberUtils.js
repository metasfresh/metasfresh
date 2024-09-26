export const toNumberOrZero = (arg) => {
  try {
    return Number(arg);
  } catch (e) {
    return 0;
  }
};
