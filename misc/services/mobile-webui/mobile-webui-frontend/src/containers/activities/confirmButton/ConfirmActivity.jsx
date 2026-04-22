import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { postUserConfirmation } from '../../../api/confirmation';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { extractUserFriendlyErrorMessageFromAxiosError } from '../../../utils/toast';
import { useDispatch } from 'react-redux';
import { appLaunchersLocation } from '../../../routes/launchers';
import { setActivityProcessing, updateWFProcess } from '../../../actions/WorkflowActions';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { usePositiveNumberSetting } from '../../../reducers/settings';
import { trl } from '../../../utils/translations';
import * as uiTrace from '../../../utils/ui_trace';

const DEFAULT_TIMEOUT_MILLIS = 20000;

const ConfirmActivity = ({
  applicationId,
  wfProcessId,
  activityId,
  caption,
  promptQuestion,
  userInstructions,
  isUserEditable,
  isProcessing,
  completeStatus,
  isLastActivity,
}) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();
  const [errorMessage, setErrorMessage] = useState(null);

  // Overridable via AD_SysConfig mobileui.frontend.api.completeConfirmation.timeoutMillis
  const timeoutMillis = usePositiveNumberSetting('api.completeConfirmation.timeoutMillis', DEFAULT_TIMEOUT_MILLIS);

  const sendConfirmation = () => {
    setErrorMessage(null);
    dispatch(setActivityProcessing({ wfProcessId, activityId, processing: true }));
    postUserConfirmation({ wfProcessId, activityId, timeoutMillis })
      .then((wfProcess) => {
        uiTrace.trace({ eventName: 'confirmationPosted', wfProcessId, activityId });
        dispatch(updateWFProcess({ wfProcess }));
        if (isLastActivity) {
          history.push(appLaunchersLocation({ applicationId }));
        }
      })
      .catch((axiosError) => {
        const message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError });
        uiTrace.trace({
          eventName: 'confirmationFailed',
          wfProcessId,
          activityId,
          httpStatus: axiosError?.response?.status ?? null,
          axiosCode: axiosError?.code ?? null,
          message,
        });
        setErrorMessage(message);
      })
      .finally(() => dispatch(setActivityProcessing({ wfProcessId, activityId, processing: false })));
  };

  const onCancelError = () => {
    setErrorMessage(null);
  };

  return (
    <div className="mt-5">
      <ConfirmButton
        id={isLastActivity ? 'last-confirm-button' : null}
        caption={caption}
        promptQuestion={promptQuestion}
        userInstructions={userInstructions}
        isUserEditable={isUserEditable && !errorMessage}
        isProcessing={isProcessing}
        completeStatus={completeStatus}
        onUserConfirmed={sendConfirmation}
      />
      {errorMessage && (
        <div className="notification is-danger mt-3" data-testid="confirm-activity-error-panel">
          <p className="mb-3">
            <strong>{trl('activities.confirmButton.error.title')}</strong>
          </p>
          <p className="mb-3">{errorMessage}</p>
          <div className="buttons">
            <ButtonWithIndicator
              testId="confirm-activity-error-retry"
              captionKey="activities.confirmButton.error.retry"
              disabled={isProcessing}
              onClick={sendConfirmation}
            />
            <ButtonWithIndicator
              testId="confirm-activity-error-cancel"
              captionKey="activities.confirmButton.error.cancel"
              disabled={isProcessing}
              onClick={onCancelError}
            />
          </div>
        </div>
      )}
    </div>
  );
};

ConfirmActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  userInstructions: PropTypes.string,
  promptQuestion: PropTypes.string,
  isUserEditable: PropTypes.bool.isRequired,
  isProcessing: PropTypes.bool,
  completeStatus: PropTypes.string.isRequired,
  isLastActivity: PropTypes.bool.isRequired,
};

export default ConfirmActivity;
