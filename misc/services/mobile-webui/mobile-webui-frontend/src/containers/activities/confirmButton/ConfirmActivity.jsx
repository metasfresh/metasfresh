import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { userConfirmation } from '../../../api/confirmation';
import ConfirmButton from './ConfirmButton';
import { history } from '../../../store/store';

class ConfirmActivity extends PureComponent {
  onUserConfirmed = () => {
    const { wfProcessId, activityId, isLastActivity } = this.props;
    userConfirmation({ wfProcessId, activityId }).then(() => {
      if (isLastActivity) {
        history.push('/');
      }
    });
  };

  render() {
    console.log('CONFIRM PROPS: ', this.props);

    const {
      caption,
      componentProps: { promptQuestion },
      isUserEditable,
    } = this.props;

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
  componentProps: PropTypes.object.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
  isLastActivity: PropTypes.bool.isRequired,
};

export default ConfirmActivity;
