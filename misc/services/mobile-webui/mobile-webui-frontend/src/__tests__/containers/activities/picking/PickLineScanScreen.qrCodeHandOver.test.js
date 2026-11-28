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
// when set, replaces the screen's `nextTU.bookAfterTUOpened`: the test decides how the booking ends
let mockBookAfterTUOpened;

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
jest.mock('../../../../apps/picking/cuQtyEntry/useOpenNextTU', () => {
  const actual = jest.requireActual('../../../../apps/picking/cuQtyEntry/useOpenNextTU');
  return {
    ...actual,
    useOpenNextTU: (...args) => {
      const nextTU = actual.useOpenNextTU(...args);
      return mockBookAfterTUOpened ? { ...nextTU, bookAfterTUOpened: mockBookAfterTUOpened } : nextTU;
    },
  };
});
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
    mockBookAfterTUOpened = undefined;
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

  const deferred = () => {
    let resolve, reject;
    const promise = new Promise((res, rej) => {
      resolve = res;
      reject = rej;
    });
    return { promise, resolve, reject };
  };

  const renderAfterReturnWithKeptCount = () => {
    mockCUEntry = { pendingScan: { scannedBarcode: 'LABEL#1', qtyEntered: '6' } };
    render(
      <Provider store={createStore((state = {}) => state)}>
        <PickLineScanScreen />
      </Provider>
    );
    expect(mockScanProps.suggestedQty).toBe(6);
  };

  const bookKeptCount = () =>
    mockScanProps.onResult({ qtyEnteredAndValidated: 6, scannedBarcode: 'LABEL#1', isDone: false });

  it('the count kept for the trip is offered once: after a booking the normal capped suggestion is offered again (open 10, kept 6, booked 6, open 4 -> 4)', async () => {
    mockSuggestedQty = 4; // what the normal rule gives once 6 are booked (last typed count capped by the open quantity)
    mockBookAfterTUOpened = () => Promise.resolve(false);
    renderAfterReturnWithKeptCount();

    await act(async () => {
      await bookKeptCount();
    });

    expect(mockScanProps.suggestedQty).toBe(4);
  });

  it('first round of the line (no normal suggestion yet): the kept count stays offered while the booking is in flight, so the quantity dialog is not re-created mid-booking', async () => {
    mockSuggestedQty = null; // nothing booked on this line yet: the normal rule offers nothing
    const booking = deferred();
    mockBookAfterTUOpened = () => booking.promise;
    renderAfterReturnWithKeptCount();

    let result;
    await act(async () => {
      result = bookKeptCount();
    });
    expect(mockScanProps.suggestedQty).toBe(6);

    await act(async () => {
      booking.resolve(false);
      await result;
    });
    expect(mockScanProps.suggestedQty).toBeUndefined();
  });

  it('a refused booking keeps the kept count offered (no retyping)', async () => {
    mockSuggestedQty = null;
    mockBookAfterTUOpened = () => Promise.reject(new Error('refused by the server'));
    renderAfterReturnWithKeptCount();

    await act(async () => {
      await expect(bookKeptCount()).rejects.toThrow('refused by the server');
    });

    expect(mockScanProps.suggestedQty).toBe(6);
  });

  it('a booking that was not sent (the TU opening it waited for failed) keeps the kept count offered', async () => {
    const { NOT_BOOKED } = jest.requireActual('../../../../apps/picking/cuQtyEntry/useOpenNextTU');
    mockSuggestedQty = 4;
    mockBookAfterTUOpened = () => Promise.resolve(NOT_BOOKED);
    renderAfterReturnWithKeptCount();

    await act(async () => {
      expect(await bookKeptCount()).toBe(NOT_BOOKED);
    });

    expect(mockScanProps.suggestedQty).toBe(6);
  });
});
