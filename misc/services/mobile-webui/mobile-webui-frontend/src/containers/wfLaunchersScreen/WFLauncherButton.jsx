import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { gotoWFProcessScreen } from '../../routes/workflow';

import { getWorkflowRequest, startWorkflowRequest } from '../../api/launchers';
import { updateWFProcess } from '../../actions/WorkflowActions';

import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { toastError } from '../../utils/toast';

class WFLauncherButton extends PureComponent {
  handleClick = () => {
    const { applicationId, startedWFProcessId, wfParameters } = this.props;
    const { updateWFProcess, gotoWFProcessScreen } = this.props;

    const wfProcessPromise = startedWFProcessId
      ? getWorkflowRequest(startedWFProcessId)
      : startWorkflowRequest({ wfParameters });

    wfProcessPromise
      .then((wfProcess) => {
        updateWFProcess({ wfProcess });
        gotoWFProcessScreen({ applicationId, wfProcessId: wfProcess.id });
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  render() {
    const { caption, startedWFProcessId, showWarningSign } = this.props;
    const wfCompleteStatus = startedWFProcessId ? CompleteStatus.IN_PROGRESS : CompleteStatus.NOT_STARTED;

    return (
      <ButtonWithIndicator
        caption={caption}
        showWarningSign={showWarningSign}
        completeStatus={wfCompleteStatus}
        disabled={false}
        onClick={this.handleClick}
      />
    );
  }
}

WFLauncherButton.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  startedWFProcessId: PropTypes.string,
  wfParameters: PropTypes.object.isRequired,
  showWarningSign: PropTypes.bool,
  //
  // Actions
  updateWFProcess: PropTypes.func.isRequired,
  gotoWFProcessScreen: PropTypes.func.isRequired,
};

export default connect(null, {
  updateWFProcess,
  gotoWFProcessScreen,
})(WFLauncherButton);
