import { toast } from 'react-toastify';
import { toastError, toastErrorFromObj } from '../../utils/toast';
import { UserMessageError } from '../../utils/UserMessageError';
import * as uiTrace from '../../utils/ui_trace';
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
    // the same literals are repeated in e2e/mobile-webui/tests/spec/errorToastLayout.spec.js (its CI image holds no frontend sources)
    expect(ERROR_TOAST_STYLE).toEqual({ maxHeight: 'calc(100vh - 6rem)' });
    expect(ERROR_TOAST_BODY_STYLE).toEqual({ overflow: 'auto', maxHeight: 'calc(100vh - 8rem)' });
  });

  it('shows the details (the reason) after the message of the key', () => {
    const toastErrorSpy = jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastError({ messageKey: 'picking.cuLabel.formatMismatch', details: "Expected constant '01' but got ']d'" });

    const message = toastErrorSpy.mock.calls[0][0].props.message;
    expect(message).toMatch(/ Expected constant '01' but got '\]d'$/);
  });

  it('shows a UserMessageError as its message with the details, not as a server error', () => {
    const toastErrorSpy = jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastErrorFromObj(new UserMessageError('picking.cuLabel.formatMismatch', 'part 3 does not fit'));

    const message = toastErrorSpy.mock.calls[0][0].props.message;
    expect(message).toMatch(/ part 3 does not fit$/);
    expect(toastErrorSpy.mock.calls[0][0].props.code).toBe('picking.cuLabel.formatMismatch');
  });

  it('traces the context of a UserMessageError (the scanned label of a refused scan)', () => {
    jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastErrorFromObj(new UserMessageError('picking.cuLabel.formatMismatch', undefined, { scannedLabel: 'L123' }));

    expect(uiTrace.trace).toHaveBeenCalledWith(expect.objectContaining({ context: { scannedLabel: 'L123' } }));
  });
});
