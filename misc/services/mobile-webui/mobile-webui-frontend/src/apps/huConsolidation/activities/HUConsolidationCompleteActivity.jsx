import React, { useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';
import { DEFAULT_TIMEOUT_MILLIS, postUserConfirmation } from '../../../api/confirmation';
import { getWFProcess } from '../../../api/launchers';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import Dialog from '../../../components/dialogs/Dialog';
import DialogButton from '../../../components/dialogs/DialogButton';
import { extractUserFriendlyErrorMessageFromAxiosError, toastError } from '../../../utils/toast';
import { appLaunchersLocation } from '../../../routes/launchers';
import { setActivityProcessing, updateWFProcess } from '../../../actions/WorkflowActions';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { usePositiveNumberSetting } from '../../../reducers/settings';
import { trl } from '../../../utils/translations';
import * as uiTrace from '../../../utils/ui_trace';

/**
 * "Complete" of an HU consolidation job: on success, shows the job list.
 * If the request fails but the reloaded job is completed anyway, shows a dialog whose OK shows the job list:
 * with the server's error, or (no response) a text saying the job is completed.
 * <p>
 * Mirrors the shared containers/activities/confirmButton/ConfirmActivity.jsx (send guard, UI trace events, network-error panel)
 * on purpose, to leave that component unchanged for the other apps: mirror fixes made there.
 */
const HUConsolidationCompleteActivity = ({ applicationId, wfProcessId, activityId, activityState, isLastActivity }) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();
  /** @type {[{kind: 'completed'|'networkError', message: string}|null, function]} */
  const [failure, setFailure] = useState(null);
  // Synchronous guard: isProcessing only re-flows via Redux on the next render, so a fast double-tap would send two requests.
  const sendingRef = useRef(false);

  const timeoutMillis = usePositiveNumberSetting('api.completeConfirmation.timeoutMillis', DEFAULT_TIMEOUT_MILLIS);

  const goToJobList = () => history.push(appLaunchersLocation({ applicationId }));

  /** @returns {Promise<boolean>} true if the reloaded job is completed */
  const reloadJobAndCheckCompleted = () =>
    getWFProcess({ wfProcessId }).then((wfProcess) => {
      dispatch(updateWFProcess({ wfProcess }));
      const activity = wfProcess?.activities?.find((activity) => activity.activityId === activityId);
      return !!activity?.componentProps?.confirmed;
    });

  const sendConfirmation = () => {
    if (sendingRef.current) return;
    sendingRef.current = true;
    setFailure(null);
    dispatch(setActivityProcessing({ wfProcessId, activityId, processing: true }));
    postUserConfirmation({ wfProcessId, activityId, timeoutMillis })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        uiTrace.trace({ eventName: 'confirmationPosted', wfProcessId, activityId });
        goToJobList();
      })
      .catch((axiosError) => {
        const isNetworkFailure = !axiosError?.response;
        const message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError });
        return reloadJobAndCheckCompleted()
          .catch(() => false)
          .then((isCompleted) => {
            uiTrace.trace({
              eventName: 'confirmationFailed',
              wfProcessId,
              activityId,
              httpStatus: axiosError?.response?.status ?? null,
              axiosCode: axiosError?.code ?? null,
              isNetworkFailure,
              message,
              isCompleted,
            });
            if (isCompleted) {
              setFailure({
                kind: 'completed',
                message: isNetworkFailure ? trl('huConsolidation.CompleteActivity.completed') : message,
              });
            } else if (isNetworkFailure) {
              setFailure({ kind: 'networkError', message });
            } else {
              toastError({ axiosError });
            }
          });
      })
      .finally(() => {
        sendingRef.current = false;
        dispatch(setActivityProcessing({ wfProcessId, activityId, processing: false }));
      });
  };

  return (
    <div className="mt-5">
      {failure?.kind === 'completed' && (
        <Dialog
          className="huConsolidation-completed-with-error-dialog"
          testId="huConsolidation-completed-with-error-dialog"
        >
          <p className="mb-3">{failure.message}</p>
          <div className="buttons is-centered">
            <DialogButton
              testId="huConsolidation-completed-with-error-ok"
              captionKey="huConsolidation.CompleteActivity.ok"
              className="is-success"
              onClick={goToJobList}
            />
          </div>
        </Dialog>
      )}
      <ConfirmButton
        id={isLastActivity ? 'last-confirm-button' : null}
        caption={activityState.caption}
        promptQuestion={activityState.componentProps?.question}
        userInstructions={activityState.userInstructions}
        isUserEditable={activityState.dataStored.isUserEditable && !failure}
        isProcessing={activityState.dataStored.processing}
        completeStatus={activityState.dataStored.completeStatus}
        onUserConfirmed={sendConfirmation}
      />
      {failure?.kind === 'networkError' && (
        <div className="notification is-danger mt-3" data-testid="confirm-activity-error-panel">
          <p className="mb-3">
            <strong>{trl('activities.confirmButton.error.title')}</strong>
          </p>
          <p className="mb-3">{failure.message}</p>
          <div className="buttons">
            <ButtonWithIndicator
              testId="confirm-activity-error-retry"
              captionKey="activities.confirmButton.error.retry"
              disabled={activityState.dataStored.processing}
              onClick={sendConfirmation}
            />
            <ButtonWithIndicator
              testId="confirm-activity-error-cancel"
              captionKey="activities.confirmButton.error.cancel"
              disabled={activityState.dataStored.processing}
              onClick={() => setFailure(null)}
            />
          </div>
        </div>
      )}
    </div>
  );
};

HUConsolidationCompleteActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activityState: PropTypes.object.isRequired,
  isLastActivity: PropTypes.bool.isRequired,
};

export default HUConsolidationCompleteActivity;
