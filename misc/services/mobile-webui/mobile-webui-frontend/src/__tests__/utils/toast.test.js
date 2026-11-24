import { toast } from 'react-toastify';
import { showToastErrorsOnNextScreen, toastErrorOnNextScreen } from '../../utils/toast';

jest.mock('react-toastify', () => ({
  toast: { error: jest.fn(), dismiss: jest.fn() },
  Bounce: {},
}));
jest.mock('../../utils/ui_trace', () => ({ trace: jest.fn() }));

describe('toastErrorOnNextScreen', () => {
  beforeEach(() => {
    toast.error.mockClear();
    showToastErrorsOnNextScreen(); // drain anything left over
    toast.error.mockClear();
  });

  it('shows nothing until the next screen is shown', () => {
    toastErrorOnNextScreen({ plainMessage: 'Label not printed' });

    expect(toast.error).not.toHaveBeenCalled();
  });

  it('shows the queued error once, on the next screen only', () => {
    toastErrorOnNextScreen({ plainMessage: 'Label not printed' });

    showToastErrorsOnNextScreen();
    expect(toast.error).toHaveBeenCalledTimes(1);

    showToastErrorsOnNextScreen();
    expect(toast.error).toHaveBeenCalledTimes(1);
  });
});
