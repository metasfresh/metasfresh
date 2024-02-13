import React from 'react';
import PropTypes from 'prop-types';
import { postUserConfirmation } from '../../../api/confirmation';
import { setActivityUserConfirmed } from '../../../actions/UserConfirmationActions';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import { toastError } from '../../../utils/toast';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { appLaunchersLocation } from '../../../routes/launchers';
import { setActivityProcessing } from '../../../actions/WorkflowActions';

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
  const history = useHistory();
  const onUserConfirmed = () => {
    dispatch(setActivityProcessing({ wfProcessId, activityId, processing: true }));
    postUserConfirmation({ wfProcessId, activityId })
      .then(() => dispatch(setActivityUserConfirmed({ wfProcessId, activityId })))
      .then(() => {
        if (isLastActivity) {
          history.push(appLaunchersLocation({ applicationId }));
        }
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => dispatch(setActivityProcessing({ wfProcessId, activityId, processing: false })));
  };

  return (
    <div className="mt-5">
      <ConfirmButton
        caption={caption}
        promptQuestion={promptQuestion}
        userInstructions={userInstructions}
        isUserEditable={isUserEditable}
        isProcessing={isProcessing}
        completeStatus={completeStatus}
        onUserConfirmed={onUserConfirmed}
      />
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
