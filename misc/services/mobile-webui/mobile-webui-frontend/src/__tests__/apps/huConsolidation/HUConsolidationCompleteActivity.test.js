import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import HUConsolidationCompleteActivity from '../../../apps/huConsolidation/activities/HUConsolidationCompleteActivity';
import { postUserConfirmation } from '../../../api/confirmation';
import { getWFProcess } from '../../../api/launchers';
import { toastError } from '../../../utils/toast';
import * as uiTrace from '../../../utils/ui_trace';

const WF_PROCESS_ID = 'huConsolidation-1';
const ACTIVITY_ID = 'complete';

const mockPush = jest.fn();
jest.mock('../../../hooks/useMobileNavigation', () => ({
  useMobileNavigation: () => ({ push: mockPush }),
}));
jest.mock('../../../api/confirmation', () => ({
  DEFAULT_TIMEOUT_MILLIS: 20000,
  postUserConfirmation: jest.fn(),
}));
jest.mock('../../../api/launchers', () => ({ getWFProcess: jest.fn() }));
jest.mock('../../../utils/toast', () => ({
  toastError: jest.fn(),
  extractUserFriendlyErrorMessageFromAxiosError: ({ axiosError }) =>
    axiosError?.response ? 'Done and shipped. Label not printed: Print failed.' : 'No response',
}));
jest.mock('../../../utils/ui_trace', () => ({ trace: jest.fn(), traceFunction: (func) => func }));
jest.mock('../../../utils/translations', () => ({ trl: (key) => key }));
jest.mock('../../../reducers/settings', () => ({ usePositiveNumberSetting: (name, defaultValue) => defaultValue }));
jest.mock('../../../routes/launchers', () => ({ appLaunchersLocation: () => '/launchers' }));

const serverError = { response: { status: 500 } };
const networkError = { code: 'ECONNABORTED' };
const reloadedJob = ({ completed }) => ({
  id: WF_PROCESS_ID,
  activities: [{ activityId: ACTIVITY_ID, componentProps: { confirmed: completed } }],
});

const renderActivity = () => {
  const store = createStore(combineReducers({ dummy: (state = {}) => state }));
  render(
    <Provider store={store}>
      <HUConsolidationCompleteActivity
        applicationId="huConsolidation"
        wfProcessId={WF_PROCESS_ID}
        activityId={ACTIVITY_ID}
        isLastActivity={true}
        activityState={{
          caption: 'Fertigstellen',
          componentProps: { question: 'Sure?' },
          dataStored: { isUserEditable: true, processing: false, completeStatus: 'NOT_STARTED' },
        }}
      />
    </Provider>
  );
};

const completeAndConfirm = async () => {
  fireEvent.click(document.getElementById('last-confirm-button'));
  await act(async () => {
    fireEvent.click(screen.getByText('activities.confirmButton.default.yes'));
  });
};

const failureTrace = () =>
  uiTrace.trace.mock.calls.map(([event]) => event).find((e) => e.eventName === 'confirmationFailed');

describe('HUConsolidationCompleteActivity', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('server error, job completed anyway: dialog with the server message, OK shows the job list', async () => {
    postUserConfirmation.mockRejectedValue(serverError);
    getWFProcess.mockResolvedValue(reloadedJob({ completed: true }));
    renderActivity();

    await completeAndConfirm();

    expect(screen.getByTestId('huConsolidation-completed-with-error-dialog')).toHaveTextContent(
      'Done and shipped. Label not printed: Print failed.'
    );
    expect(toastError).not.toHaveBeenCalled();
    expect(failureTrace()).toMatchObject({ httpStatus: 500, isNetworkFailure: false, isCompleted: true });

    fireEvent.click(screen.getByTestId('huConsolidation-completed-with-error-ok'));
    expect(mockPush).toHaveBeenCalledWith('/launchers');
  });

  it('no response, job completed anyway: dialog saying the job is completed, not the network error', async () => {
    postUserConfirmation.mockRejectedValue(networkError);
    getWFProcess.mockResolvedValue(reloadedJob({ completed: true }));
    renderActivity();

    await completeAndConfirm();

    const dialog = screen.getByTestId('huConsolidation-completed-with-error-dialog');
    expect(dialog).toHaveTextContent('huConsolidation.CompleteActivity.completed');
    expect(dialog).not.toHaveTextContent('No response');
    expect(screen.queryByTestId('confirm-activity-error-panel')).not.toBeInTheDocument();
    expect(failureTrace()).toMatchObject({ isNetworkFailure: true, isCompleted: true });
  });

  it('server error, job not completed: error toast, no dialog', async () => {
    postUserConfirmation.mockRejectedValue(serverError);
    getWFProcess.mockResolvedValue(reloadedJob({ completed: false }));
    renderActivity();

    await completeAndConfirm();

    expect(toastError).toHaveBeenCalledWith({ axiosError: serverError });
    expect(screen.queryByTestId('huConsolidation-completed-with-error-dialog')).not.toBeInTheDocument();
    expect(failureTrace()).toMatchObject({ isCompleted: false });
  });

  it('no response, job not completed: the retry panel with the network error', async () => {
    postUserConfirmation.mockRejectedValue(networkError);
    getWFProcess.mockResolvedValue(reloadedJob({ completed: false }));
    renderActivity();

    await completeAndConfirm();

    expect(screen.getByTestId('confirm-activity-error-panel')).toHaveTextContent('No response');
  });

  it('success: traced, the job list is shown', async () => {
    postUserConfirmation.mockResolvedValue(reloadedJob({ completed: true }));
    renderActivity();

    await completeAndConfirm();

    expect(uiTrace.trace).toHaveBeenCalledWith(expect.objectContaining({ eventName: 'confirmationPosted' }));
    expect(mockPush).toHaveBeenCalledWith('/launchers');
  });
});
