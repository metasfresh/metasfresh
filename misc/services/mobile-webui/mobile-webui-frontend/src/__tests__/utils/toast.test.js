import { toast } from 'react-toastify';
import { toastError, toastErrorFromObj } from '../../utils/toast';
import { UserMessageError } from '../../utils/UserMessageError';
import * as uiTrace from '../../utils/ui_trace';
import { ERROR_TOAST_BODY_STYLE, ERROR_TOAST_STYLE } from '../../utils/toastLayout';

jest.mock('../../utils/ui_trace', () => ({ trace: jest.fn() }));

describe('toastError', () => {
  afterEach(() => jest.restoreAllMocks());

  it('passes the shared layout styles of the error toast to react-toastify', () => {
    const toastErrorSpy = jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastError({ plainMessage: 'a long message' });

    expect(toastErrorSpy).toHaveBeenCalledTimes(1);
    const options = toastErrorSpy.mock.calls[0][1];
    expect(options.style).toBe(ERROR_TOAST_STYLE);
    expect(options.bodyStyle).toBe(ERROR_TOAST_BODY_STYLE);
    expect(ERROR_TOAST_STYLE).toEqual({ maxHeight: 'calc(100vh - 6rem)' });
    expect(ERROR_TOAST_BODY_STYLE).toEqual({ overflow: 'auto', maxHeight: 'calc(100vh - 8rem)' });
  });

  it('shows the details (the reason) after the localized message of the key', () => {
    const toastErrorSpy = jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastError({ messageKey: 'picking.cuLabel.formatMismatch', details: "Expected constant '01' but got ']d'" });

    const message = toastErrorSpy.mock.calls[0][0].props.message;
    expect(message).toMatch(/^\S.* Expected constant '01' but got '\]d'$/);
  });

  it('also traces the details of the refusal for support', () => {
    jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastError({
      messageKey: 'picking.cuLabel.formatMismatch',
      details: 'part 3 does not fit',
      context: { scannedLabel: 'L1' },
    });

    expect(uiTrace.trace).toHaveBeenCalledWith(
      expect.objectContaining({ context: { scannedLabel: 'L1', details: 'part 3 does not fit' } })
    );
  });

  it('shows a UserMessageError as its localized message with the details, not as a server error', () => {
    const toastErrorSpy = jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastErrorFromObj(new UserMessageError('picking.cuLabel.formatMismatch', 'part 3 does not fit'));

    const message = toastErrorSpy.mock.calls[0][0].props.message;
    expect(message).toMatch(/^\S.* part 3 does not fit$/);
    expect(toastErrorSpy.mock.calls[0][0].props.code).toBe('picking.cuLabel.formatMismatch');
  });

  it('traces the context of a UserMessageError (the scanned label of a refused scan)', () => {
    jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastErrorFromObj(new UserMessageError('picking.cuLabel.formatMismatch', undefined, { scannedLabel: 'L123' }));

    expect(uiTrace.trace).toHaveBeenCalledWith(expect.objectContaining({ context: { scannedLabel: 'L123' } }));
  });
});
