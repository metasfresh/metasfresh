import React from 'react';
import { render, screen, act } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

// ---------------------------------------------------------------------------
// Stub connected-react-router before react-redux is imported by anything.
// It uses connect() which requires a real store; we don't have one.
// ---------------------------------------------------------------------------
jest.mock('connected-react-router', () => ({
  push: jest.fn(),
  ConnectedRouter: ({ children }) => children,
  connectRouter: jest.fn((s) => s),
  LOCATION_CHANGE: '@@router/LOCATION_CHANGE',
}));

// ---------------------------------------------------------------------------
// Stub the hooks that HUConsolidationActivity uses directly.
// We mock at the hook level so we never need a real Redux store.
// ---------------------------------------------------------------------------
jest.mock('../../../reducers/wfProcesses', () => ({
  useWFActivity: jest.fn(),
}));

jest.mock('../../../apps/huConsolidation/actions/useCurrentTarget', () => ({
  useCurrentTarget: jest.fn(),
}));

jest.mock('../../../apps/huConsolidation/actions/usePickingSlots', () => ({
  usePickingSlots: jest.fn(),
}));

jest.mock('../../../hooks/useMobileNavigation', () => ({
  useMobileNavigation: jest.fn(),
}));

jest.mock('../../../utils/translations', () => ({
  trl: (key) => key,
}));

jest.mock('../../../utils/qrCode/hu', () => ({
  toQRCodeDisplayableNoFail: jest.fn((v) => String(v)),
}));

// ButtonWithIndicator renders a real button; no need to stub it.

// ---------------------------------------------------------------------------
// Now it is safe to import.
// ---------------------------------------------------------------------------
import { useWFActivity } from '../../../reducers/wfProcesses';
import { useCurrentTarget } from '../../../apps/huConsolidation/actions/useCurrentTarget';
import { usePickingSlots } from '../../../apps/huConsolidation/actions/usePickingSlots';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';

import HUConsolidationActivity from '../../../apps/huConsolidation/activities/HUConsolidationActivity';

// ---------------------------------------------------------------------------
// Fixtures
// ---------------------------------------------------------------------------
const WF_PROCESS_ID = 'wfp-test-001';
const ACTIVITY_ID = 'act-001';
const APPLICATION_ID = 'huConsolidation';

const mockPush = jest.fn();

function setupMocks({ graiScanEnabled = false, currentTarget = null, isUserEditable = true } = {}) {
  useWFActivity.mockReturnValue({
    dataStored: {
      isUserEditable,
      job: {
        graiScanEnabled,
        currentTarget,
        pickingSlots: [],
      },
    },
  });
  useCurrentTarget.mockReturnValue({
    currentTarget,
    isProcessing: false,
    closeTarget: jest.fn(),
    printLabel: null,
  });
  usePickingSlots.mockReturnValue({ pickingSlots: [] });
  useMobileNavigation.mockReturnValue({ push: mockPush, goTo: mockPush, goBack: jest.fn() });
}

function renderActivity(opts) {
  setupMocks(opts);
  return render(
    <HUConsolidationActivity applicationId={APPLICATION_ID} wfProcessId={WF_PROCESS_ID} activityId={ACTIVITY_ID} />
  );
}

// ---------------------------------------------------------------------------
// Tests: GRAI scan action button visibility
// ---------------------------------------------------------------------------
describe('HUConsolidationActivity — GRAI scan action button', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('shows "GRAI scannen" button when graiScanEnabled=true and a target is open', () => {
    renderActivity({
      graiScanEnabled: true,
      currentTarget: { caption: 'LU-0001', printable: false },
    });

    expect(screen.getByTestId('grai-scan-action-button')).toBeInTheDocument();
  });

  it('does NOT show the GRAI button when graiScanEnabled=false', () => {
    renderActivity({
      graiScanEnabled: false,
      currentTarget: { caption: 'LU-0001', printable: false },
    });

    expect(screen.queryByTestId('grai-scan-action-button')).toBeNull();
  });

  it('does NOT show the GRAI button when graiScanEnabled=true but no target is open', () => {
    renderActivity({ graiScanEnabled: true, currentTarget: null });

    expect(screen.queryByTestId('grai-scan-action-button')).toBeNull();
  });
});

// ---------------------------------------------------------------------------
// Tests: useTargetGrais — sendToBackend calls setTargetGrais correctly
// ---------------------------------------------------------------------------

// Mock react-redux useDispatch for the hook tests.
jest.mock('react-redux', () => {
  const actual = jest.requireActual('react-redux');
  return {
    ...actual,
    useDispatch: jest.fn(),
    useSelector: jest.fn(),
  };
});

jest.mock('../../../apps/huConsolidation/api', () => ({
  getTargetGrais: jest.fn(),
  setTargetGrais: jest.fn(),
}));

jest.mock('../../../actions/WorkflowActions', () => ({
  updateWFProcess: jest.fn((args) => ({ type: 'UPDATE_WF_PROCESS', ...args })),
}));

jest.mock('../../../utils/toast', () => ({
  toastError: jest.fn(),
}));

import { useDispatch } from 'react-redux';
import * as api from '../../../apps/huConsolidation/api';
import { useTargetGrais } from '../../../apps/huConsolidation/actions/useTargetGrais';

const mockDispatch = jest.fn();

function renderHook(hookFn) {
  const result = { current: null };
  function Probe() {
    result.current = hookFn();
    return null;
  }
  render(<Probe />);
  return result;
}

describe('useTargetGrais — sendToBackend payload', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    useDispatch.mockReturnValue(mockDispatch);
  });

  it('calls setTargetGrais({ wfProcessId, graiCodes }) with the added GRAIs', async () => {
    api.setTargetGrais.mockResolvedValue(null);

    const result = renderHook(() => useTargetGrais({ wfProcessId: WF_PROCESS_ID }));

    act(() => {
      result.current.addGrais(['GRAI-001', 'GRAI-002']);
    });

    await act(async () => {
      await result.current.sendToBackend();
    });

    expect(api.setTargetGrais).toHaveBeenCalledTimes(1);
    expect(api.setTargetGrais).toHaveBeenCalledWith({
      wfProcessId: WF_PROCESS_ID,
      graiCodes: ['GRAI-001', 'GRAI-002'],
    });
  });

  it('calls setTargetGrais with an empty graiCodes array when no GRAIs were added', async () => {
    api.setTargetGrais.mockResolvedValue(null);

    const result = renderHook(() => useTargetGrais({ wfProcessId: WF_PROCESS_ID }));

    await act(async () => {
      await result.current.sendToBackend();
    });

    expect(api.setTargetGrais).toHaveBeenCalledWith({
      wfProcessId: WF_PROCESS_ID,
      graiCodes: [],
    });
  });
});
