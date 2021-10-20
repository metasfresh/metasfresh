import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { pushHeaderEntry } from '../../actions/HeaderActions';

import { getWorkflowRequest, startWorkflowRequest } from '../../api/launchers';
import { updateWFProcess } from '../../actions/WorkflowActions';

import ButtonWithIndicator from '../../components/ButtonWithIndicator';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { toastError } from '../../utils/toast';

class WFLauncherButton extends PureComponent {
  handleClick = () => {
    const { startedWFProcessId, wfParameters } = this.props;
    const { updateWFProcess } = this.props;

    const wfProcessPromise = startedWFProcessId
      ? getWorkflowRequest(startedWFProcessId)
      : startWorkflowRequest({ wfParameters });

    wfProcessPromise
      .then((wfProcess) => {
        updateWFProcess({ wfProcess });
        this.gotoWFProcessScreen({ wfProcess });
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  gotoWFProcessScreen = ({ wfProcess }) => {
    const { push, pushHeaderEntry } = this.props;
    const location = `/workflow/${wfProcess.id}`;
    push(location);
    pushHeaderEntry({
      location,
      values: wfProcess.headerProperties.entries,
    });
  };

  render() {
    const { id, caption, startedWFProcessId } = this.props;
    const wfCompleteStatus = startedWFProcessId ? CompleteStatus.IN_PROGRESS : CompleteStatus.NOT_STARTED;

    return (
      <div className="buttons">
        <button key={id} className="button is-outlined complete-btn" disabled={false} onClick={this.handleClick}>
          <ButtonWithIndicator caption={caption} completeStatus={wfCompleteStatus} />
        </button>
      </div>
    );
  }
}

WFLauncherButton.propTypes = {
  //
  // Props
  id: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  startedWFProcessId: PropTypes.string,
  wfParameters: PropTypes.object.isRequired,
  //
  // Actions
  updateWFProcess: PropTypes.func.isRequired,
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default connect(null, {
  updateWFProcess,
  push,
  pushHeaderEntry,
})(WFLauncherButton);
