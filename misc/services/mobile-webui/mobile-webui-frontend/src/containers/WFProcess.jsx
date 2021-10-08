import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';

import { continueWorkflow } from '../actions/WorkflowActions';
import { getWorkflowProcess } from '../reducers/wfProcesses';
import { getWorkflowProcessStatus } from '../reducers/wfProcesses_status';

import ConfirmActivity from '../components/containers/ConfirmActivity';
import PickProductsActivity from '../components/containers/PickProductsActivity';
import ScanActivity from '../components/ScanActivity';

class WorkflowProcess extends PureComponent {
  componentDidMount() {
    const { wfProcessId, activities, continueWorkflow } = this.props;

    if (!activities.length) {
      continueWorkflow(wfProcessId);
    }
  }

  render() {
    const { wfProcessId, activities, workflowProcessStatus } = this.props;
    const { activities: activitiesState } = workflowProcessStatus;

    return (
      <div className="pt-2 section wf-process-container">
        <div className="container pick-products-container">
          <div className="activities">
            {activities.length > 0 &&
              activities.map((activityItem) => {
                let uniqueId = uuidv4();

                switch (activityItem.componentType) {
                  case 'common/scanBarcode':
                    return (
                      <ScanActivity
                        key={uniqueId}
                        wfProcessId={wfProcessId}
                        activityItem={activityItem}
                        activityState={activitiesState[activityItem.activityId]}
                      />
                    );
                  case 'picking/pickProducts':
                    return (
                      <PickProductsActivity
                        key={uniqueId}
                        id={uniqueId}
                        wfProcessId={wfProcessId}
                        activityId={activityItem.activityId}
                        activityState={activitiesState[activityItem.activityId]}
                        {...activityItem}
                      />
                    );
                  case 'common/confirmButton':
                    return <ConfirmActivity key={uniqueId} id={uniqueId} wfProcessId={wfProcessId} {...activityItem} />;
                }
              })}
          </div>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state, { match }) {
  const { workflowId } = match.params;
  const workflow = getWorkflowProcess(state, workflowId);
  const workflowProcessStatus = getWorkflowProcessStatus(state, workflowId);

  return {
    wfProcessId: workflowId,
    activities: workflow.activities,
    workflowProcessStatus,
    token: state.appHandler.token,
  };
}

WorkflowProcess.propTypes = {
  activities: PropTypes.array,
  workflowProcessStatus: PropTypes.object,
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  continueWorkflow: PropTypes.func.isRequired,
  token: PropTypes.string,
};

export default withRouter(connect(mapStateToProps, { continueWorkflow })(WorkflowProcess));
