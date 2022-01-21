import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { push } from 'connected-react-router';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';

import {
  manufacturingStepScreenLocation,
  manufacturingScanScreenLocation,
} from '../../../../routes/manufacturing_issue';
import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { selectWFProcessFromState } from '../../../../reducers/wfProcesses_status';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';

import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';

class RawMaterialIssueStepScreen extends PureComponent {
  componentDidMount() {
    const {
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps: { locatorName },
      pushHeaderEntry,
    } = this.props;
    const location = manufacturingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId });

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('general.Locator'),
          value: locatorName,
        },
      ],
    });
  }

  onScanButtonClick = () => {
    const { push, applicationId, wfProcessId, activityId, lineId, stepId } = this.props;
    const location = manufacturingScanScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId });

    push(location);
  };

  render() {
    const {
      stepProps: { huBarcode, uom, qtyToIssue, qtyIssued, qtyRejected },
    } = this.props;

    const isIssued = qtyIssued > 0 || qtyRejected > 0;
    const scanButtonCaption = isIssued ? `${huBarcode}` : counterpart.translate('activities.picking.scanHUBarcode');

    const scanButtonStatus = isIssued ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

    return (
      <div className="pt-3 section">
        <div className="picking-step-details centered-text is-size-5">
          <div>
            <div className="columns is-mobile">
              <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                {counterpart.translate('general.Barcode')}
              </div>
              <div className="column is-half has-text-left pb-0">{huBarcode}</div>
            </div>
            <div className="columns is-mobile">
              <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                {counterpart.translate('activities.mfg.issues.qtyToIssue')}:
              </div>
              <div className="column is-half has-text-left pb-0">
                {qtyToIssue} {uom}
              </div>
            </div>
            <div className="columns is-mobile">
              <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                {counterpart.translate('activities.mfg.issues.qtyIssued')}:
              </div>
              <div className="column is-half has-text-left pb-0">
                {qtyIssued || 0} {uom}
              </div>
            </div>
            {!!qtyRejected && (
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
                  {counterpart.translate('activities.mfg.issues.qtyRejected')}:
                </div>
                <div className="column is-half has-text-left pb-0">
                  {qtyRejected} {uom}
                </div>
              </div>
            )}
            <div className="mt-0">
              <ButtonWithIndicator
                caption={scanButtonCaption}
                completeStatus={scanButtonStatus}
                disabled={isIssued}
                onClick={this.onScanButtonClick}
              />
              {/* Unpick button */}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { applicationId, workflowId: wfProcessId, activityId, lineId, stepId, altStepId } = ownProps.match.params;

  const activity = selectWFProcessFromState(state, wfProcessId).activities[activityId];
  const stepProps = activity.dataStored.lines[lineId].steps[stepId];

  return {
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
    stepProps,
  };
};

RawMaterialIssueStepScreen.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  //
  // Actions
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { pushHeaderEntry, push })(RawMaterialIssueStepScreen));
