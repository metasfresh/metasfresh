import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import { updateWFProcess } from '../actions/WorkflowActions';
import { selectWFProcessFromState } from '../reducers/wfProcesses_status/index';

import ScanActivity from './activities/scan/ScanActivity';
import PickProductsActivity from './activities/picking/PickProductsActivity';
import ConfirmActivity from './activities/confirmButton/ConfirmActivity';

class WFProcessScreen extends PureComponent {
  render() {
    const { wfProcess } = this.props;
    const { id: wfProcessId, activities } = wfProcess;
    const activitiesArray = activities ? Object.values(activities) : [];

    return (
      <div className="pt-2 section wf-process-container">
        <div className="container pick-products-container">
          <div className="activities">
            {activitiesArray.length > 0 &&
              activitiesArray.map((activityItem, index) => {
                const isLastActivity = index === activitiesArray.length - 1;

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
                }
              })}
          </div>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state, { match }) {
  const { workflowId: wfProcessId } = match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);

  return { wfProcess };
}

WFProcessScreen.propTypes = {
  //
  // Props
  wfProcess: PropTypes.object,
  //
  // Actions
  updateWFProcess: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { updateWFProcess })(WFProcessScreen));
