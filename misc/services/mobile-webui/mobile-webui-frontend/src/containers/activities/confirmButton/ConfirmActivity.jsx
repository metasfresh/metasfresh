import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { postUserConfirmation } from '../../../api/confirmation';
import { setActivityUserConfirmed } from '../../../actions/UserConfirmationActions';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import { history } from '../../../store/store';
import { toastError } from '../../../utils/toast';
import { connect } from 'react-redux';

class ConfirmActivity extends PureComponent {
  onUserConfirmed = () => {
    const { wfProcessId, activityId, isLastActivity } = this.props;
    const { setActivityUserConfirmed } = this.props;

    postUserConfirmation({ wfProcessId, activityId })
      .then(() => setActivityUserConfirmed({ wfProcessId, activityId }))
      .then(() => {
        if (isLastActivity) {
          history.push('/');
        }
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  render() {
    const { caption, promptQuestion, isUserEditable } = this.props;

    return (
      <div className="mt-5">
        <ConfirmButton
          caption={caption}
          promptQuestion={promptQuestion}
          isUserEditable={isUserEditable}
          onUserConfirmed={this.onUserConfirmed}
        />
      </div>
    );
  }
}

ConfirmActivity.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  caption: PropTypes.string,
  promptQuestion: PropTypes.string,
  isUserEditable: PropTypes.bool.isRequired,
  isLastActivity: PropTypes.bool.isRequired,
  //
  // Actions:
  setActivityUserConfirmed: PropTypes.func.isRequired,
};

export default connect(null, { setActivityUserConfirmed })(ConfirmActivity);
