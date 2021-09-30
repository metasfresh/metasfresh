import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';

import { continueWorkflow } from '../actions/WorkflowActions';
import ViewHeader from '../containers/ViewHeader';
import BarcodeScanner from '../components/containers/BarcodeScanner';
import ConfirmActivity from '../components/containers/ConfirmActivity';
import PickProductsActivity from '../components/containers/PickProductsActivity';

class WorkflowProcess extends PureComponent {
  componentDidMount() {
    const { wfProcessId, activities, continueWorkflow } = this.props;

    if (!activities.length) {
      continueWorkflow(wfProcessId);
    }
  }

  render() {
    const { wfProcessId, activities, status } = this.props;
    const { activities: activitiesState } = status;

    return (
      <div className="container pick-products-container">
        <ViewHeader />
        <div className="pick-products-active-line header-caption">custom headers</div>
        {activities.length > 0 &&
          activities.map((activityItem) => {
            let uniqueId = uuidv4();

            switch (activityItem.componentType) {
              case 'common/scanBarcode':
                return <BarcodeScanner key={uniqueId} id={uniqueId} {...activityItem} />;
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
    );
  }
}

function mapStateToProps(state, { match }) {
  const { workflowId } = match.params;
  // TODO: We need a selector for this
  const workflow = state.wfProcesses[workflowId];
  const activities = workflow ? workflow.activities : [];
  const status = state.wfProcesses_status[workflowId] || { activities: [] };

  return {
    wfProcessId: workflowId,
    activities,
    status,
  };
}

WorkflowProcess.propTypes = {
  activities: PropTypes.array,
  status: PropTypes.object,
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  continueWorkflow: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { continueWorkflow })(WorkflowProcess));
