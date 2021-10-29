import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import counterpart from 'counterpart';
import { push } from 'connected-react-router';

import { abortWorkflowRequest } from '../../api/launchers';
import ConfirmButton from './confirmButton/ConfirmButton';

class AbortActivity extends PureComponent {
  onUserConfirmed = () => {
    const { wfProcessId, appId, push } = this.props;

    abortWorkflowRequest(wfProcessId).then(push(`/launchers/${appId}`));
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

const mapStateToProps = (state) => {
  return {
    appId: state.appHandler.activeApplication.id,
  };
};

AbortActivity.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  appId: PropTypes.string.isRequired,
  push: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { push })(AbortActivity);
