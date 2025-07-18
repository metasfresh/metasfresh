const isTouchCapable = () => {
  return (
    ('maxTouchPoints' in navigator && navigator.maxTouchPoints > 0) ||
    ('msMaxTouchPoints' in navigator && navigator.msMaxTouchPoints > 0)
  );
};

const isSmallScreen = () => {
  return window.matchMedia('(max-width: 1024px)').matches;
};

export const isMobileOrTablet = () => {
  return isSmallScreen() && isTouchCapable();
};
