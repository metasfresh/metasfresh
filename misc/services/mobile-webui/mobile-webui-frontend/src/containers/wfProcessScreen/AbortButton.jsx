import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import counterpart from 'counterpart';

import { abortWorkflowRequest } from '../../api/launchers';
import ConfirmButton from '../../components/buttons/ConfirmButton';
import { gotoAppLaunchers } from '../../routes/launchers';

class AbortButton extends PureComponent {
  onUserConfirmed = () => {
    const { applicationId, wfProcessId, gotoAppLaunchers } = this.props;

    abortWorkflowRequest(wfProcessId).then(gotoAppLaunchers(applicationId));
  };

  render() {
    return (
      <div className="mt-5">
        <ConfirmButton
          caption={counterpart.translate('activities.confirmButton.abort')}
          isDangerousAction={true}
          isUserEditable={true}
          onUserConfirmed={this.onUserConfirmed}
        />
      </div>
    );
  }
}

AbortButton.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  gotoAppLaunchers: PropTypes.func.isRequired,
};

export default connect(null, { gotoAppLaunchers })(AbortButton);
