import { toast } from 'react-toastify';
import { toastError } from '../../utils/toast';
import { ERROR_TOAST_BODY_STYLE, ERROR_TOAST_STYLE } from '../../utils/toastLayout';

jest.mock('../../utils/ui_trace', () => ({ trace: jest.fn() }));

describe('toastError', () => {
  afterEach(() => jest.restoreAllMocks());

  // The layout itself (a long message scrolls, the close button stays on screen) is tested in a real browser: e2e/mobile-webui/tests/spec/errorToastLayout.spec.js
  it('passes the shared layout styles of the error toast to react-toastify', () => {
    const toastErrorSpy = jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastError({ plainMessage: 'a long message' });

    expect(toastErrorSpy).toHaveBeenCalledTimes(1);
    const options = toastErrorSpy.mock.calls[0][1];
    expect(options.style).toBe(ERROR_TOAST_STYLE);
    expect(options.bodyStyle).toBe(ERROR_TOAST_BODY_STYLE);
  });
});
