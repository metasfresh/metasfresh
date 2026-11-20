// Layout of the error toast, kept in a plain JS module so the E2E layout test can import the very same values.
// react-toastify caps a toast at 800px, taller than a handheld screen: a long message would push the close button off-screen.
// The toast is capped to the screen instead (minus the container's bottom margin). The toast body does not stretch to the toast's height
// (react-toastify: margin auto 0), so the body carries its own cap (toast cap minus the toast's padding) and scrolls the message.
export const ERROR_TOAST_STYLE = { maxHeight: 'calc(100vh - 6rem)' };
export const ERROR_TOAST_BODY_STYLE = { overflow: 'auto', maxHeight: 'calc(100vh - 8rem)' };
