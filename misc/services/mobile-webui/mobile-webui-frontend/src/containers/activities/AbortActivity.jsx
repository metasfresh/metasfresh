import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import counterpart from 'counterpart';
import { goBack } from 'connected-react-router';

import { abortWorkflowRequest } from '../../api/launchers';
import ConfirmButton from './confirmButton/ConfirmButton';

class AbortActivity extends PureComponent {
  onUserConfirmed = () => {
    const { wfProcessId, goBack } = this.props;

    abortWorkflowRequest(wfProcessId).then(goBack);
  };

  render() {
    const caption = counterpart.translate('activities.confirmButton.abort.caption');

    return (
      <div className="mt-5">
        <ConfirmButton
          isCancelMode={true}
          isUserEditable={true}
          caption={caption}
          onUserConfirmed={this.onUserConfirmed}
        />
      </div>
    );
  }
}

AbortActivity.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  goBack: PropTypes.func.isRequired,
};

export default connect(null, { goBack })(AbortActivity);
