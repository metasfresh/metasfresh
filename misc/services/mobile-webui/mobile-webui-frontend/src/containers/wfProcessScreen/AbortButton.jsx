import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import counterpart from 'counterpart';

import { abortWorkflowRequest } from '../../api/launchers';
import ConfirmButton from '../activities/confirmButton/ConfirmButton';
import { gotoAppLaunchers } from '../../routes/launchers';

class AbortButton extends PureComponent {
  onUserConfirmed = () => {
    const { wfProcessId, appId, gotoAppLaunchers } = this.props;

    abortWorkflowRequest(wfProcessId).then(gotoAppLaunchers(appId));
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
  const { activeApplication } = state.applications;

  return {
    appId: activeApplication ? activeApplication.id : null,
  };
};

AbortButton.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  appId: PropTypes.string.isRequired,
  gotoAppLaunchers: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { gotoAppLaunchers })(AbortButton);
