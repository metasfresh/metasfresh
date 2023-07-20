import React from 'react';
import PropTypes from 'prop-types';
import { postUserConfirmation } from '../../../api/confirmation';
import { setActivityUserConfirmed } from '../../../actions/UserConfirmationActions';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import { toastError } from '../../../utils/toast';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';

const ConfirmActivity = ({
  wfProcessId,
  activityId,
  caption,
  promptQuestion,
  userInstructions,
  isUserEditable,
  completeStatus,
  isLastActivity,
}) => {
  const dispatch = useDispatch();
  const history = useHistory();
  const onUserConfirmed = () => {
    postUserConfirmation({ wfProcessId, activityId })
      .then(() => dispatch(setActivityUserConfirmed({ wfProcessId, activityId })))
      .then(() => {
        if (isLastActivity) {
          history.push('/');
        }
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <div className="mt-5">
      <ConfirmButton
        caption={caption}
        promptQuestion={promptQuestion}
        userInstructions={userInstructions}
        isUserEditable={isUserEditable}
        completeStatus={completeStatus}
        onUserConfirmed={onUserConfirmed}
      />
    </div>
  );
};

ConfirmActivity.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  userInstructions: PropTypes.string,
  promptQuestion: PropTypes.string,
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  isLastActivity: PropTypes.bool.isRequired,
};

export default ConfirmActivity;
