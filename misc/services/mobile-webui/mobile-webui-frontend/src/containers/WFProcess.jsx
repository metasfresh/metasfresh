import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import PropTypes from 'prop-types';

import BarcodeScanner from '../components/containers/BarcodeScanner';
import ConfirmActivity from '../components/containers/ConfirmActivity';
import PickProductsActivity from '../components/containers/PickProductsActivity';

class WorkflowProcess extends PureComponent {
  render() {
    const { wfProcessId, activities, status } = this.props;
    const { activities: activitiesState } = status;

    return (
      <div className="pick-products-container">
        <div className="title is-4 header-caption">WFProcess Caption HEADER</div>
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
                    activityId={activityItem.id}
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
  const { activities } = state.wfProcesses[workflowId];
  const status = state.wfProcesses_status[workflowId];

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
};

export default withRouter(connect(mapStateToProps)(WorkflowProcess));
