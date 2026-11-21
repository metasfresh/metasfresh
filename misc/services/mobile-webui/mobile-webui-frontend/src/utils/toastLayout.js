// Layout of the error toast, kept in a plain JS module. Pinned by __tests__/utils/toast.test.js; the E2E layout test
// (e2e/mobile-webui/tests/spec/errorToastLayout.spec.js) repeats the same values, because its CI image holds no frontend sources.
// react-toastify caps a toast at 800px, taller than a handheld screen: a long message would push the close button off-screen.
// The toast is capped to the screen instead (minus the container's bottom margin). The toast body does not stretch to the toast's height
// (react-toastify: margin auto 0), so the body carries its own cap (toast cap minus the toast's padding) and scrolls the message.
export const ERROR_TOAST_STYLE = { maxHeight: 'calc(100vh - 6rem)' };
export const ERROR_TOAST_BODY_STYLE = { overflow: 'auto', maxHeight: 'calc(100vh - 8rem)' };
