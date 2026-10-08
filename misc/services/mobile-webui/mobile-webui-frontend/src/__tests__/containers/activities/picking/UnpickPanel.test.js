import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';

import UnpickPanel from '../../../../containers/activities/picking/unpick/UnpickPanel';

// Controllable dispatch: each test sets its return value to a resolving / rejecting promise.
const mockDispatch = jest.fn();
jest.mock('react-redux', () => ({
  ...jest.requireActual('react-redux'),
  useDispatch: () => mockDispatch,
}));

// The thunk action-creator is irrelevant here — dispatch is mocked to return our promise.
jest.mock('../../../../apps/picking/redux/postStepPartiallyUnPickedThunk', () => ({
  postStepPartiallyUnPickedThunk: jest.fn(() => ({ type: 'MOCK_THUNK' })),
}));

const mockToastError = jest.fn();
jest.mock('../../../../utils/toast', () => ({
  toastError: (...args) => mockToastError(...args),
}));

// Stage 1: product scan dialog → one button that fires onResolved with a canned resolved product.
jest.mock('../../../../containers/activities/picking/unpick/UnpickProductScanDialog', () => {
  // eslint-disable-next-line react/prop-types
  const Mock = ({ onResolved }) => (
    <button
      data-testid="mock-product-resolved"
      onClick={() =>
        onResolved({ productId: 1, scannedCode: 'x', packedQty: 5, packedQtyUom: 'PCE', unpickable: true })
      }
    >
      resolve-product
    </button>
  );
  return Mock;
});

// Stage 2: qty dialog → one button that fires onQtyChange.
jest.mock('../../../../components/dialogs/GetQuantityDialog', () => {
  // eslint-disable-next-line react/prop-types
  const Mock = ({ onQtyChange }) => (
    <button data-testid="mock-qty-change" onClick={() => onQtyChange({ qtyEnteredAndValidated: 2 })}>
      change-qty
    </button>
  );
  return Mock;
});

// Stage 3: target scan dialog → one button that fires onSubmit; rendered iff we are on SCAN_TARGET.
jest.mock('../../../../containers/activities/picking/unpick/UnpickTargetScanDialog', () => {
  // eslint-disable-next-line react/prop-types
  const Mock = ({ onSubmit }) => (
    <button data-testid="mock-target-submit" onClick={() => onSubmit({ unpickToTargetQRCode: { code: 'HU#1' } })}>
      submit-target
    </button>
  );
  return Mock;
});

const baseProps = {
  wfProcessId: 'wf-1',
  activityId: 'act-1',
  lineId: 'line-1',
};

const driveToTargetSubmit = () => {
  fireEvent.click(screen.getByTestId('mock-product-resolved'));
  fireEvent.click(screen.getByTestId('mock-qty-change'));
  fireEvent.click(screen.getByTestId('mock-target-submit'));
};

describe('UnpickPanel — onTargetSubmitted', () => {
  beforeEach(() => {
    mockDispatch.mockReset();
    mockToastError.mockClear();
  });

  it('NETWORK failure (no response): stays on SCAN_TARGET and does NOT close (allow retry)', async () => {
    mockDispatch.mockReturnValue(Promise.reject(new Error('network blip')));
    const onClose = jest.fn();
    render(<UnpickPanel {...baseProps} onClose={onClose} />);

    driveToTargetSubmit();

    await waitFor(() => expect(mockToastError).toHaveBeenCalledTimes(1));
    expect(onClose).not.toHaveBeenCalled();
    // Still on the target stage so the operator can retry.
    expect(screen.getByTestId('mock-target-submit')).toBeInTheDocument();
  });

  it('SERVER rejection (response present): toasts but does NOT close — panel stays on SCAN_TARGET for a corrected scan', async () => {
    mockDispatch.mockReturnValue(Promise.reject({ response: { status: 422 } }));
    const onClose = jest.fn();
    render(<UnpickPanel {...baseProps} onClose={onClose} />);

    driveToTargetSubmit();

    await waitFor(() => expect(mockToastError).toHaveBeenCalledTimes(1));
    // A server rejection (e.g. a mis-scanned/incompatible target HU) is correctable in place, so the
    // panel must stay open — same as the network-failure case — and let the operator re-scan or Cancel.
    expect(onClose).not.toHaveBeenCalled();
    expect(screen.getByTestId('mock-target-submit')).toBeInTheDocument();
  });

  it('closes exactly once on a successful submit', async () => {
    mockDispatch.mockReturnValue(Promise.resolve());
    const onClose = jest.fn();
    render(<UnpickPanel {...baseProps} onClose={onClose} />);

    driveToTargetSubmit();

    await waitFor(() => expect(onClose).toHaveBeenCalledTimes(1));
    expect(mockToastError).not.toHaveBeenCalled();
  });
});
