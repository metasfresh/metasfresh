import React from 'react';
import { act, render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

// the app reads `config` (a runtime global of the hosting page) while its modules load
global.config = { SERVER_URL: '' };
const PickLineScanScreen = require('../../../../containers/activities/picking/PickLineScanScreen').default;

const mockHistory = { push: jest.fn(), replace: jest.fn(), goBack: jest.fn() };
let mockScanProps;
let mockCUEntry;
let mockSuggestedQty = null;

jest.mock('../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  toastNotification: jest.fn(),
}));
jest.mock('../../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => mockHistory }));
jest.mock('../../../../hooks/useScreenDefinition', () => ({
  useScreenDefinition: () => ({
    url: '/u',
    applicationId: 'picking',
    wfProcessId: 'wf',
    activityId: 'a',
    lineId: 'l',
  }),
}));
jest.mock('../../../../api/picking', () => ({
  getScannedHUQRCodeInfo: jest.fn(),
  closePickingTarget: jest.fn(),
  setTUPickingTargetFromGrai: jest.fn(),
  PICKING_TARGET_TIMEOUT_MILLIS: 20000,
  useAvailablePickingTargets: () => ({
    graiScanEnabled: false,
    existingLuGrais: [],
    isTargetsLoading: false,
    setPickingTarget: jest.fn(),
  }),
}));
jest.mock('../../../../api/launchers', () => ({ getWFProcess: jest.fn() }));
jest.mock('../../../../reducers/settings', () => ({
  ...jest.requireActual('../../../../reducers/settings'),
  useBooleanSetting: () => false,
}));
jest.mock('../../../../reducers/wfProcesses', () => ({
  getActivityById: () => ({ activityId: 'a', dataStored: {} }),
  getCustomQRCodeFormats: () => [],
  getLineById: () => ({
    productId: '1',
    productNo: 'P1',
    packingTuPIId: null, // a line without a TU type: "Weiteres TU" goes to the TU list
    cuQtyEntryPolicy: { isLabelRequired: true, isPackIdRequired: false, isScanOnce: true, isOneCUPerLabel: false },
  }),
  getQtyRejectedReasonsFromActivity: () => [],
}));
jest.mock('../../../../reducers/wfProcesses/picking', () => ({ getCUEntry: () => mockCUEntry }));
jest.mock('../../../../reducers/wfProcesses/picking/useCurrentPickTarget', () => ({
  useCurrentPickingTargetInfo: () => ({ luPickingTarget: undefined, tuPickingTarget: undefined }),
}));
jest.mock('../../../../reducers/wfProcesses/picking/getReadAttributesFromActivity', () => ({
  getReadAttributesFromActivity: () => [],
}));
jest.mock('../../../../utils/picking', () => ({
  computeCUEntryToRemember: jest.fn(),
  computeSuggestedQty: () => mockSuggestedQty,
  getNextEligibleLineToPick: jest.fn(),
  getQtyPickedOrRejectedTotalForLine: () => 0,
  getQtyToPickForLine: () => 5,
  getQtyToPickRemainingForLine: () => 5,
}));
jest.mock('../../../../containers/activities/picking/PickLineScreen', () => ({ useHeaderUpdate: jest.fn() }));
jest.mock('../../../../components/ScanHUAndGetQtyComponent', () => (props) => {
  mockScanProps = props;
  return null;
});

describe('PickLineScanScreen: the source HU of the hop from the line screen (URL param qrCode) survives the trip to the TU list', () => {
  beforeEach(() => {
    mockHistory.push.mockClear();
    mockCUEntry = undefined;
    mockSuggestedQty = null;
    window.history.pushState({}, '', '/scanner?next=pickingJob&qrCode=HU%231');
  });
  afterAll(() => window.history.pushState({}, '', '/'));

  it('"Weiteres TU" on a line without a TU type: the TU list returns to the scan screen with the same qrCode', async () => {
    render(
      <Provider store={createStore((state = {}) => state)}>
        <PickLineScanScreen />
      </Provider>
    );
    const element = mockScanProps.nextTUActions({ scanner: undefined, pendingScan: { scannedBarcode: 'LABEL#1' } });
    await act(async () => {
      element.props.onNextTU();
    });

    expect(mockHistory.push).toHaveBeenCalledTimes(1);
    const url = mockHistory.push.mock.calls[0][0];
    const returnTo = new URLSearchParams(url.split('?')[1]).get('returnTo');
    expect(returnTo).toBe('/picking/wf/wf/pick/A/a/L/l/scanner?next=pickingJob&qrCode=HU%231');
  });

  it('the count kept for the trip is offered once: after a booking the normal capped suggestion is offered again (open 10, kept 6, booked 6, open 4 -> 4)', async () => {
    mockCUEntry = { pendingScan: { scannedBarcode: 'LABEL#1', qtyEntered: '6' } };
    mockSuggestedQty = 4; // what the normal rule gives once 6 are booked (last typed count capped by the open quantity)
    render(
      <Provider store={createStore((state = {}) => state)}>
        <PickLineScanScreen />
      </Provider>
    );
    expect(mockScanProps.suggestedQty).toBe(6);

    await act(async () => {
      try {
        await mockScanProps.onResult({ qtyEnteredAndValidated: 6, scannedBarcode: 'LABEL#1' });
      } catch (e) {
        // the booking itself (API) is not under test here, only that the kept count is used up
      }
    });

    expect(mockScanProps.suggestedQty).toBe(4);
  });
});
