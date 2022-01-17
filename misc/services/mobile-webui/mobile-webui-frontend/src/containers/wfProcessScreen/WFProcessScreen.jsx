import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import { updateWFProcess } from '../../actions/WorkflowActions';
import { pushHeaderEntry } from '../../actions/HeaderActions';
import { activitiesNotStarted, selectWFProcessFromState } from '../../reducers/wfProcesses_status';

import ScanActivity from '../activities/scan/ScanActivity';
import PickProductsActivity from '../activities/picking/PickProductsActivity';
import ConfirmActivity from '../activities/confirmButton/ConfirmActivity';
import RawMaterialIssueActivity from '../activities/manufacturing/issue/RawMaterialIssueActivity';
import MaterialReceiptActivity from '../activities/manufacturing/receipt/MaterialReceiptActivity';
import DistributionMoveActivity from '../activities/distribution/DistributionMoveActivity';
import AbortButton from './AbortButton';
import { getWFProcessScreenLocation } from '../../routes/workflow_locations';

const EMPTY_ARRAY = [];

class WFProcessScreen extends PureComponent {
  componentDidMount() {
    const { applicationId, wfProcessId, headerProperties } = this.props;
    const { pushHeaderEntry } = this.props;

    pushHeaderEntry({
      location: getWFProcessScreenLocation({ applicationId, wfProcessId }),
      values: headerProperties,
    });
  }

  render() {
    const { applicationId, wfProcessId, activities, isWorkflowNotStarted } = this.props;

    return (
      <div className="pt-2 section wf-process-container">
        <div className="container pick-products-container">
          <div className="activities">
            {activities.length > 0 &&
              activities.map((activityItem, index) => {
                return this.renderComponent({
                  activityItem,
                  isLastActivity: index === activities.length - 1,
                });
              })}
            {isWorkflowNotStarted ? <AbortButton applicationId={applicationId} wfProcessId={wfProcessId} /> : null}
          </div>
        </div>
      </div>
    );
  }

  renderComponent = ({ activityItem, isLastActivity }) => {
    const { applicationId, wfProcessId } = this.props;

    switch (activityItem.componentType) {
      case 'common/scanBarcode':
        return (
          <ScanActivity
            key={activityItem.activityId}
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityState={activityItem}
          />
        );
      case 'picking/pickProducts':
        return (
          <PickProductsActivity
            key={activityItem.activityId}
            id={activityItem.activityId}
            applicationId={applicationId}
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
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityId={activityItem.activityId}
            caption={activityItem.caption}
            componentProps={activityItem.componentProps}
            isUserEditable={activityItem.dataStored.isUserEditable}
            isLastActivity={isLastActivity}
          />
        );
      case 'manufacturing/rawMaterialsIssue':
        return (
          <RawMaterialIssueActivity
            key={activityItem.activityId}
            id={activityItem.activityId}
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityState={activityItem}
          />
        );
      case 'manufacturing/materialReceipt':
        return (
          <MaterialReceiptActivity
            key={activityItem.activityId}
            id={activityItem.activityId}
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityState={activityItem}
          />
        );
      case 'distribution/move':
        return (
          <DistributionMoveActivity
            key={activityItem.activityId}
            id={activityItem.activityId}
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityState={activityItem}
            {...activityItem}
          />
        );
    }
  };
}

function mapStateToProps(state, { match }) {
  const { applicationId, workflowId: wfProcessId } = match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activities = wfProcess.activities ? Object.values(wfProcess.activities) : EMPTY_ARRAY;
  const isWorkflowNotStarted = activitiesNotStarted(state, wfProcessId);

  return {
    applicationId,
    wfProcessId,
    activities,
    isWorkflowNotStarted,
    headerProperties: wfProcess.headerProperties.entries,
  };
}

WFProcessScreen.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activities: PropTypes.array.isRequired,
  isWorkflowNotStarted: PropTypes.bool,
  headerProperties: PropTypes.array,
  //
  // Actions
  updateWFProcess: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { updateWFProcess, pushHeaderEntry })(WFProcessScreen));
