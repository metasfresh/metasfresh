import { toast } from 'react-toastify';
import { toastError } from '../../utils/toast';

jest.mock('../../utils/ui_trace', () => ({ trace: jest.fn() }));

describe('toastError', () => {
  afterEach(() => jest.restoreAllMocks());

  // react-toastify caps a toast at 800px, taller than a handheld screen: a long message pushed the close button off-screen
  it('caps the error toast at the screen height and scrolls the message inside it, so the close button stays on screen', () => {
    const toastErrorSpy = jest.spyOn(toast, 'error').mockImplementation(() => undefined);

    toastError({ plainMessage: 'a long message' });

    expect(toastErrorSpy).toHaveBeenCalledTimes(1);
    const options = toastErrorSpy.mock.calls[0][1];
    expect(options.style).toEqual({ maxHeight: 'calc(100vh - 6rem)' });
    // the toast body (not the toast) is the scroll container: react-toastify's body does not stretch to the toast's height, so it needs its own cap
    expect(options.bodyStyle).toEqual({ overflow: 'auto', maxHeight: 'calc(100vh - 8rem)' });
  });
});
