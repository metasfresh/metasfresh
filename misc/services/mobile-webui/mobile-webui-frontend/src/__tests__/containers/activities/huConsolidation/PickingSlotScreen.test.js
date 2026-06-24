import React from 'react';
import { render, screen, fireEvent, act } from '@testing-library/react';

// Mock the hooks used by PickingSlotScreen
jest.mock('../../../../apps/huConsolidation/actions/usePickingSlot', () => ({
  usePickingSlot: jest.fn(),
}));
jest.mock('../../../../hooks/useMobileLocation', () => ({
  useMobileLocation: jest.fn(),
}));
jest.mock('../../../../hooks/useScreenDefinition', () => ({
  useScreenDefinition: jest.fn(),
}));

// Mock BarcodeScannerComponent to a simple div with a testId that simulates scanning
jest.mock('../../../../components/BarcodeScannerComponent', () => {
  const React = require('react');
  return function MockBarcodeScannerComponent({ onResolvedResult, testId, invisible }) {
    if (invisible) return null;
    return (
      <div data-testid={testId || 'barcode-scanner'}>
        <button data-testid="mock-scan-trigger" onClick={() => onResolvedResult({ scannedBarcode: 'test-grai-123' })}>
          Scan GRAI
        </button>
      </div>
    );
  };
});

// Mock ButtonWithIndicator to avoid uiTrace / IndexedDB in jsdom
jest.mock('../../../../components/buttons/ButtonWithIndicator', () => {
  const React = require('react');
  return function MockButtonWithIndicator({ testId, captionKey, caption, onClick, children, disabled }) {
    const label = caption || captionKey || 'button';
    return (
      <button data-testid={testId} onClick={onClick} disabled={disabled}>
        {label}
        {children}
      </button>
    );
  };
});

// Silence console.log inside component
jest.spyOn(console, 'log').mockImplementation(() => {});

import { PickingSlotScreen } from '../../../../apps/huConsolidation/activities/PickingSlotScreen';
import { usePickingSlot } from '../../../../apps/huConsolidation/actions/usePickingSlot';
import { useMobileLocation } from '../../../../hooks/useMobileLocation';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';

describe('PickingSlotScreen — GRAI scan', () => {
  const consolidateMock = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();

    useMobileLocation.mockReturnValue({
      wfProcessId: 'wf-1',
      activityId: 'act-1',
      id: 'slot-1',
    });

    useScreenDefinition.mockReturnValue({
      history: { goBack: jest.fn() },
    });

    consolidateMock.mockResolvedValue({});

    usePickingSlot.mockReturnValue({
      isLoading: false,
      pickingSlotContent: {
        items: [
          {
            huId: 42,
            displayName: 'TU-001',
            packingInfo: 'EACH',
            storages: [],
          },
        ],
      },
      pickingSlotQRCode: null,
      isProcessing: false,
      consolidate: consolidateMock,
    });
  });

  it('(1) renders the GRAI scan affordance with the expected data-testid', () => {
    render(<PickingSlotScreen />);
    // getByTestId throws if not found — presence assertion is sufficient
    expect(screen.getByTestId('grai-scanner')).toBeTruthy();
  });

  it('(2) a GRAI scan triggers consolidate({ grai }) without huId', async () => {
    render(<PickingSlotScreen />);

    await act(async () => {
      fireEvent.click(screen.getByTestId('mock-scan-trigger'));
    });

    expect(consolidateMock).toHaveBeenCalledTimes(1);
    expect(consolidateMock).toHaveBeenCalledWith({ grai: 'test-grai-123' });
    // huId must NOT be in the call (or must be undefined/null)
    const callArg = consolidateMock.mock.calls[0][0];
    expect(callArg).not.toHaveProperty('huId');
  });

  it('(3) tapping an HU item still calls consolidate({ huId }) without grai', async () => {
    render(<PickingSlotScreen />);

    await act(async () => {
      fireEvent.click(screen.getByTestId('consolidate-42-button'));
    });

    expect(consolidateMock).toHaveBeenCalledTimes(1);
    const callArg = consolidateMock.mock.calls[0][0];
    expect(callArg).toMatchObject({ huId: 42 });
    expect(callArg).not.toHaveProperty('grai');
  });
});
