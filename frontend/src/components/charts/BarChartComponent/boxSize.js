const fallbackSize = {
  height: 400,
  padding: 20,
  ratio: 5 / 4,
};

export default {
  ...fallbackSize,
  minWidth: fallbackSize.height * fallbackSize.ratio,
};
