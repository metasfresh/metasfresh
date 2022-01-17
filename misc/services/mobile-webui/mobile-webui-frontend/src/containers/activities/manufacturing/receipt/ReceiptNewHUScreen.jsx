import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { go } from 'connected-react-router';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';

import { selectWFProcessFromState } from '../../../../reducers/wfProcesses_status';
import { updateManufacturingReceiptTarget, updateManufacturingReceipt } from '../../../../actions/ManufacturingActions';
import { toastError } from '../../../../utils/toast';

class ReceiptNewHUScreen extends PureComponent {
  handleClick = (target) => {
    const {
      updateManufacturingReceiptTarget,
      updateManufacturingReceipt,
      wfProcessId,
      activityId,
      lineId,
      go,
      lineProps,
    } = this.props;

    updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target });

    if (lineProps.userQtyReceived) {
      updateManufacturingReceipt({
        wfProcessId,
        activityId,
        lineId,
      }).catch((axiosError) => toastError({ axiosError }));
    }

    go(-2);
  };

  render() {
    const { availableReceivingTargets } = this.props.lineProps;

    return (
      <div className="pt-2 section lines-screen-container">
        <div className="steps-container">
          <div className="buttons">
            {availableReceivingTargets.values.map((target) => (
              <button
                key={target.luPIItemId}
                className="button is-outlined complete-btn"
                onClick={() => this.handleClick(target)}
              >
                <div className="full-size-btn">
                  <div className="left-btn-side" />
                  <div className="caption-btn">
                    <div className="rows">
                      <div className="row is-full pl-5">{target.caption}</div>
                      <div className="row is-full is-size-7">
                        <div className="picking-row-info">
                          <div className="picking-to-pick">{target.tuCaption}</div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </button>
            ))}
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
  const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;

  return {
    wfProcessId,
    activityId,
    lineId,
    lineProps,
  };
};

ReceiptNewHUScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
  lineId: PropTypes.string.isRequired,

  // Actions
  updateManufacturingReceiptTarget: PropTypes.func.isRequired,
  updateManufacturingReceipt: PropTypes.func.isRequired,
  go: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, { go, updateManufacturingReceiptTarget, updateManufacturingReceipt })(ReceiptNewHUScreen)
);
