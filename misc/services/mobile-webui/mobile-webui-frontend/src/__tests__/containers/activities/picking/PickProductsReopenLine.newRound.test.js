import React from 'react';
import { render, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';

// the app reads `config` (a runtime global of the hosting page) while its modules load
global.config = { SERVER_URL: '' };
const { usePickProductsScan } = require('../../../../containers/activities/picking/PickProductsScanScreen');
const { useLineButtonClickHandler } = require('../../../../containers/activities/picking/PickProductsActivity');
const { FORGET_CU_ENTRY } = require('../../../../apps/picking/redux/PickingActionTypes');
const { postStepPickedThunk } = require('../../../../apps/picking/redux/postStepPickedThunk');

const mockHistory = { push: jest.fn(), goBack: jest.fn(), replace: jest.fn() };
let mockLine = {};
let mockActivity = {};

jest.mock('../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../../hooks/useMobileNavigation', () => ({ useMobileNavigation: () => mockHistory }));
jest.mock('../../../../hooks/useScreenDefinition', () => ({ useScreenDefinition: () => ({}) }));
jest.mock('../../../../api/picking', () => ({ getNextEligibleLineToPack: () => Promise.resolve({ lineId: 'l1' }) }));
jest.mock('../../../../apps/picking/redux/postStepPickedThunk', () => ({
  postStepPickedThunk: jest.fn(() => () => Promise.resolve({})),
}));
jest.mock('../../../../containers/activities/picking/PickLineScanScreen', () => ({
  NEXT_PickingJob: 'next',
  convertScannedBarcodeToResolvedResult: () => Promise.resolve({ qrCode: 'QR' }),
}));
jest.mock('../../../../reducers/wfProcesses', () => ({
  getCustomQRCodeFormats: () => [],
  getLineByIdFromActivity: () => mockLine,
  useWFActivity: () => mockActivity,
  getLinesArrayFromActivity: () => [],
  isAnonymousPickHUsOnTheFly: () => true,
}));
jest.mock('../../../../utils/picking', () => ({
  getQtyToPickRemainingForLine: () => 1,
  isCatchWeight: () => false,
  getCurrentPickFromHUQRCode: () => null,
}));
jest.mock('../../../../reducers/wfProcesses/picking/getReadAttributesFromActivity', () => ({
  isNoReadAttributes: () => true,
}));

const withStore = (callback) => {
  const actions = [];
  const thunk = () => (next) => (action) => typeof action === 'function' ? action() : next(action);
  const store = createStore((state = {}, action) => {
    actions.push(action);
    return state;
  }, applyMiddleware(thunk));
  const Probe = () => {
    callback();
    return null;
  };
  render(
    <Provider store={store}>
      <Probe />
    </Provider>
  );
  return () => actions.filter((a) => a.type === FORGET_CU_ENTRY);
};

describe('reopening a line from the job screen starts a new round: nothing is remembered', () => {
  beforeEach(() => {
    mockHistory.push.mockClear();
    postStepPickedThunk.mockImplementation(() => () => Promise.resolve({}));
    mockLine = { cuQtyEntryPolicy: { isLabelRequired: true } };
    mockActivity = {};
  });

  it('job screen "Scannen" forgets the remembered count and opens the line scan screen', async () => {
    let onBarcodeScanned;
    const forgets = withStore(() => {
      ({ onBarcodeScanned } = usePickProductsScan({ applicationId: 'picking', wfProcessId: 'wf1', activityId: 'a1' }));
    });
    await onBarcodeScanned({ scannedBarcode: 'LABEL' });
    expect(forgets()).toHaveLength(1);
    expect(mockHistory.push).toHaveBeenCalledTimes(1);
  });

  it('job screen "Scannen" on a line that needs its label never books directly (1 open): the scan screen reads the label', async () => {
    let onBarcodeScanned;
    withStore(() => {
      ({ onBarcodeScanned } = usePickProductsScan({ applicationId: 'picking', wfProcessId: 'wf1', activityId: 'a1' }));
    });
    await onBarcodeScanned({ scannedBarcode: 'LABEL' });
    expect(postStepPickedThunk).not.toHaveBeenCalled();
    expect(mockHistory.push).toHaveBeenCalledTimes(1);
  });

  it('job screen "Scannen" on a Manual line with 1 open still books directly', async () => {
    mockLine = {};
    let onBarcodeScanned;
    const forgets = withStore(() => {
      ({ onBarcodeScanned } = usePickProductsScan({ applicationId: 'picking', wfProcessId: 'wf1', activityId: 'a1' }));
    });
    await onBarcodeScanned({ scannedBarcode: 'LABEL' });
    await waitFor(() => expect(postStepPickedThunk).toHaveBeenCalledTimes(1));
    expect(forgets()).toHaveLength(0);
  });

  it('a click on a line with nothing picked (picks undone) forgets the remembered count before opening the scan screen', () => {
    let onLineButtonClick;
    const forgets = withStore(() => {
      const { useDispatch } = require('react-redux');
      onLineButtonClick = useLineButtonClickHandler({
        applicationId: 'picking',
        wfProcessId: 'wf1',
        activity: { activityId: 'a1' },
        history: mockHistory,
        dispatch: useDispatch(),
      });
    });
    onLineButtonClick({ line: { pickingLineId: 'l1', qtyPicked: 0 } });
    expect(forgets()).toHaveLength(1);
    expect(mockHistory.push).toHaveBeenCalledTimes(1);
  });

  it('a click on a line that has picks opens the line screen and keeps the memory', () => {
    let onLineButtonClick;
    const forgets = withStore(() => {
      const { useDispatch } = require('react-redux');
      onLineButtonClick = useLineButtonClickHandler({
        applicationId: 'picking',
        wfProcessId: 'wf1',
        activity: { activityId: 'a1' },
        history: mockHistory,
        dispatch: useDispatch(),
      });
    });
    onLineButtonClick({ line: { pickingLineId: 'l1', qtyPicked: 3 } });
    expect(forgets()).toHaveLength(0);
    expect(mockHistory.push).toHaveBeenCalledTimes(1);
  });
});
