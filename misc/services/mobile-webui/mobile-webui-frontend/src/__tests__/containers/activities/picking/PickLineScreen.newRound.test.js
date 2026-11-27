import React from 'react';
import { fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

// the app reads `config` (a runtime global of the hosting page) while its modules load
global.config = { SERVER_URL: '' };
const PickLineScreen = require('../../../../containers/activities/picking/PickLineScreen').default;
const { FORGET_CU_ENTRY } = require('../../../../apps/picking/redux/PickingActionTypes');

const mockHistory = { push: jest.fn(), goBack: jest.fn() };

jest.mock('../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../../hooks/useScreenDefinition', () => ({
  useScreenDefinition: () => ({
    history: mockHistory,
    url: '/line/1',
    applicationId: 'picking',
    wfProcessId: 'wf1',
    activityId: 'a1',
    lineId: 'l1',
  }),
}));
jest.mock('../../../../containers/activities/picking/SelectCurrentLUTUButtons', () => () => null);
jest.mock('../../../../containers/activities/picking/PickStepButton', () => () => null);
jest.mock('../../../../components/buttons/ButtonWithIndicator', () => ({ captionKey, onClick }) => (
  <button type="button" data-testid={captionKey} onClick={onClick} />
));
jest.mock('../../../../reducers/wfProcesses', () => ({
  getActivityById: () => ({}),
  getLineByIdFromActivity: () => ({ steps: {} }),
  isAnonymousPickHUsOnTheFly: () => true,
}));
jest.mock('../../../../utils/picking', () => ({
  getCurrentPickFromHUQRCode: () => mockPickFromHUQRCode,
  getQtyPickedOrRejectedTotalForLine: () => 0,
  getQtyToPickForLine: () => 10,
  getQtyToPickRemainingForLine: () => 10,
  isAllowPickingAnyHUForLine: () => true,
}));

let mockPickFromHUQRCode = null;

const renderScreen = () => {
  const actions = [];
  const store = createStore((state = {}, action) => {
    actions.push(action);
    return state;
  });
  render(
    <Provider store={store}>
      <PickLineScreen />
    </Provider>
  );
  return () => actions.filter((a) => a.type === FORGET_CU_ENTRY);
};

describe('PickLineScreen: a new round on the line remembers nothing, whichever button opens the scan screen', () => {
  beforeEach(() => mockHistory.push.mockClear());

  it.each([
    ['scan a QR code (no source HU)', 'activities.picking.scanQRCode', null],
    ['pick the source HU', 'activities.picking.PickHU', 'HU-QR'],
    ['pick on the fly', 'activities.picking.pickQtyButton', null],
  ])('%s forgets the remembered count of the line', (_name, testId, qrCode) => {
    mockPickFromHUQRCode = qrCode;
    const forgets = renderScreen();
    fireEvent.click(screen.getByTestId(testId));
    expect(forgets()).toEqual([
      expect.objectContaining({ payload: expect.objectContaining({ wfProcessId: 'wf1', activityId: 'a1' }) }),
    ]);
    expect(mockHistory.push).toHaveBeenCalledTimes(1);
  });
});
