import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { pushHeaderEntry } from '../../actions/HeaderActions';

import { continueWorkflow, startWorkflow } from '../../actions/WorkflowActions';
import ButtonWithIndicator from '../../components/ButtonWithIndicator';
import * as CompleteStatus from '../../constants/CompleteStatus';

class WFLauncherButton extends PureComponent {
  handleClick = () => {
    const { startWorkflow, continueWorkflow, wfParameters, startedWFProcessId, push, pushHeaderEntry } = this.props;
    const action = startedWFProcessId ? continueWorkflow(startedWFProcessId) : startWorkflow({ wfParameters });

    action.then(({ endpointResponse: wfProcess }) => {
      const location = `/workflow/${wfProcess.id}`;
      push(location);
      pushHeaderEntry({
        location,
        values: wfProcess.headerProperties.entries,
      });
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
  startWorkflow: PropTypes.func.isRequired,
  continueWorkflow: PropTypes.func.isRequired,
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default connect(null, { startWorkflow, continueWorkflow, push, pushHeaderEntry })(WFLauncherButton);
