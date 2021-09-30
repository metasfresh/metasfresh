import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';

import { startWorkflow, continueWorkflow } from '../actions/WorkflowActions';

class Launcher extends PureComponent {
  handleClick = () => {
    const { startWorkflow, continueWorkflow, wfProviderId, wfParameters, startedWFProcessId, push } = this.props;
    const action = startedWFProcessId
      ? continueWorkflow(startedWFProcessId)
      : startWorkflow({ wfProviderId, wfParameters });

    action.then(({ endpointResponse }) => {
      push(`/workflow/${endpointResponse.id}`);
    });
  };

  render() {
    const { caption, wfProviderId, startedWFProcessId } = this.props;

    return (
      <div className="ml-3 mr-3 is-light launcher" onClick={this.handleClick}>
        <div className="box">
          <div className="columns is-mobile">
            <div className="column is-12">
              <div className="columns">
                <div className="column is-size-4-mobile no-p">{caption}</div>
                <div className="column is-size-7 no-p">
                  {wfProviderId} - {startedWFProcessId}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Launcher.propTypes = {
  caption: PropTypes.string.isRequired,
  wfProviderId: PropTypes.string.isRequired,
  startedWFProcessId: PropTypes.string,
  startWorkflow: PropTypes.func.isRequired,
  continueWorkflow: PropTypes.func.isRequired,
  push: PropTypes.func.isRequired,
  wfParameters: PropTypes.object.isRequired,
};

export default connect(null, { startWorkflow, continueWorkflow, push })(Launcher);
