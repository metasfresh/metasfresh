import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push } from 'connected-react-router';
import counterpart from 'counterpart';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';

import { selectWFProcessFromState } from '../../../../reducers/wfProcesses_status';
import {
  manufacturingReceiptScanScreenLocation,
  manufacturingReceiptNewHUScreen,
} from '../../../../routes/manufacturing_receipt';

class ReceiptReceiveTargetScreen extends PureComponent {
  handleNewHUClick = () => {
    const { push, applicationId, wfProcessId, activityId, lineId } = this.props;
    const location = manufacturingReceiptNewHUScreen({ applicationId, wfProcessId, activityId, lineId });

    push(location);
  };

  handleScanClick = () => {
    const { push, applicationId, wfProcessId, activityId, lineId } = this.props;
    const location = manufacturingReceiptScanScreenLocation({ applicationId, wfProcessId, activityId, lineId });

    push(location);
  };

  render() {
    return (
      <div className="pt-2 section lines-screen-container">
        <div className="steps-container">
          <div className="buttons">
            <button className="button is-outlined complete-btn" disabled={false} onClick={this.handleNewHUClick}>
              <div className="full-size-btn">
                <div className="left-btn-side" />
                <div className="caption-btn">
                  <div className="rows">
                    <div className="row is-full pl-5">{counterpart.translate('activities.mfg.receipts.newHU')}</div>
                  </div>
                </div>
              </div>
            </button>
            <button className="button is-outlined complete-btn" disabled={false} onClick={this.handleScanClick}>
              <div className="full-size-btn">
                <div className="left-btn-side" />
                <div className="caption-btn">
                  <div className="rows">
                    <div className="row is-full pl-5">
                      {counterpart.translate('activities.mfg.receipts.existingLU')}
                    </div>
                  </div>
                </div>
              </div>
            </button>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { applicationId, workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
  const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;

  return {
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    lineProps,
  };
};

ReceiptReceiveTargetScreen.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,

  // actions
  push: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { push })(ReceiptReceiveTargetScreen));
