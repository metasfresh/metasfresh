import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { go } from 'connected-react-router';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';

import { selectWFProcessFromState } from '../../../../reducers/wfProcesses_status';
import { updateManufacturingReceiptTarget, updateManufacturingReceipt } from '../../../../actions/ManufacturingActions';
import { toastError } from '../../../../utils/toast';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';

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
      <div className="section pt-2">
        <div className="steps-container">
          <div className="buttons">
            {availableReceivingTargets.values.map((target) => (
              <ButtonWithIndicator
                key={target.luPIItemId}
                caption={target.caption}
                onClick={() => this.handleClick(target)}
              >
                <div className="row is-full is-size-7">{target.tuCaption}</div>
              </ButtonWithIndicator>
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
