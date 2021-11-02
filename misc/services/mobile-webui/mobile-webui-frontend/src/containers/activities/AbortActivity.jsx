import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import { abortWorkflowRequest } from '../../api/launchers';
import ConfirmButton from './confirmButton/ConfirmButton';
import { history } from '../../store/store';

class AbortActivity extends PureComponent {
  onUserConfirmed = () => {
    const { wfProcessId } = this.props;

    abortWorkflowRequest(wfProcessId).then(() => history.push('/'));
  };

  render() {
    const caption = counterpart.translate('activities.confirmButton.abort.caption');

    return (
      <div className="mt-5">
        <ConfirmButton isCancel={true} isUserEditable={true} caption={caption} onUserConfirmed={this.onUserConfirmed} />
      </div>
    );
  }
}

AbortActivity.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
};

export default AbortActivity;
