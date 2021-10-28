import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import { updateWFProcess } from '../actions/WorkflowActions';
import { activitiesNotStarted, selectWFProcessFromState } from '../reducers/wfProcesses_status';

import ScanActivity from './activities/scan/ScanActivity';
import PickProductsActivity from './activities/picking/PickProductsActivity';
import ConfirmActivity from './activities/confirmButton/ConfirmActivity';
import ManufacturingActivity from './activities/manufacturing/ManufacturingActivity';
import AbortActivity from './activities/AbortActivity';

class WFProcessScreen extends PureComponent {
  render() {
    const { wfProcessId, activities, isWorkflowNotStarted } = this.props;

    return (
      <div className="pt-2 section wf-process-container">
        <div className="container pick-products-container">
          <div className="activities">
            {activities.length > 0 &&
              activities.map((activityItem, index) => {
                const isLastActivity = index === activities.length - 1;

                switch (activityItem.componentType) {
                  case 'common/scanBarcode':
                    return (
                      <ScanActivity
                        key={activityItem.activityId}
                        wfProcessId={wfProcessId}
                        activityState={activityItem}
                      />
                    );
                  case 'picking/pickProducts':
                    return (
                      <PickProductsActivity
                        key={activityItem.activityId}
                        id={activityItem.activityId}
                        wfProcessId={wfProcessId}
                        activityId={activityItem.activityId}
                        activityState={activityItem}
                        {...activityItem}
                      />
                    );
                  case 'common/confirmButton':
                    return (
                      <ConfirmActivity
                        key={activityItem.activityId}
                        id={activityItem.activityId}
                        wfProcessId={wfProcessId}
                        activityId={activityItem.activityId}
                        componentProps={activityItem.componentProps}
                        isUserEditable={activityItem.dataStored.isUserEditable}
                        isLastActivity={isLastActivity}
                      />
                    );
                  case 'manufacturing/activity':
                    return (
                      <ManufacturingActivity
                        key={activityItem.activityId}
                        id={activityItem.activityId}
                        wfProcessId={wfProcessId}
                        activityState={activityItem}
                      />
                    );
                }
              })}
            {isWorkflowNotStarted ? <AbortActivity wfProcessId={wfProcessId} /> : null}
          </div>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state, { match }) {
  const { workflowId: wfProcessId } = match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activities = wfProcess.activities ? Object.values(wfProcess.activities) : [];
  const isWorkflowNotStarted = activitiesNotStarted(state, wfProcessId);

  return {
    wfProcessId,
    activities,
    isWorkflowNotStarted,
  };
}

WFProcessScreen.propTypes = {
  //
  // Props
  isWorkflowNotStarted: PropTypes.bool,
  activities: PropTypes.array.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  //
  // Actions
  updateWFProcess: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { updateWFProcess })(WFProcessScreen));
