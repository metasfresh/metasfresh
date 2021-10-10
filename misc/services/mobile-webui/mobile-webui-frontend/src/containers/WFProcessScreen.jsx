import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';

import { continueWorkflow } from '../actions/WorkflowActions';
import { selectWFProcess } from '../reducers/wfProcesses';
import { selectWFProcessState } from '../reducers/wfProcesses_status/index';

import ScanActivity from './activities/scan/ScanActivity';
import PickProductsActivity from './activities/picking/PickProductsActivity';
import ConfirmActivity from './activities/confirmButton/ConfirmActivity';

class WFProcessScreen extends PureComponent {
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
  const workflow = selectWFProcess(state, workflowId);
  const workflowProcessStatus = selectWFProcessState(state, workflowId);

  return {
    wfProcessId: workflowId,
    activities: workflow.activities,
    workflowProcessStatus,
  };
}

WFProcessScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  workflowProcessStatus: PropTypes.object,
  activities: PropTypes.array,
  //
  // Actions
  continueWorkflow: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { continueWorkflow })(WFProcessScreen));
