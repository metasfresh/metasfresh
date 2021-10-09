import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { pushHeaderEntry } from '../../actions/HeaderActions';

import { continueWorkflow, startWorkflow } from '../../actions/WorkflowActions';

class WFLauncherItem extends PureComponent {
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
    const { caption, startedWFProcessId } = this.props;

    return (
      <div className="ml-3 mr-3 is-light launcher" onClick={this.handleClick}>
        <div className="box">
          <div className="columns is-mobile">
            <div className="column is-12">
              <div className="columns">
                <div className="column is-size-4-mobile no-p">{caption}</div>
                <div className="column is-size-7 no-p">{startedWFProcessId}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

WFLauncherItem.propTypes = {
  //
  // Props
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

export default connect(null, { startWorkflow, continueWorkflow, push, pushHeaderEntry })(WFLauncherItem);
