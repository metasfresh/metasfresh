import { detect } from 'detect-browser';

const BROWSER = detect();
const SUPPORTED_BROWSERS = ['chrome'];

export const isBrowserSupported = () => {
  const userBrowser = BROWSER !== null ? BROWSER.name : 'chrome';
  let isSupported = false;

  SUPPORTED_BROWSERS.map((browser) => {
    if (userBrowser === browser) {
      isSupported = true;
    }
  });
  return isSupported;
};
