import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { toastError } from '../../../utils/toast';
import { pickingStepScanScreenLocation } from '../../../routes/picking';
import { getPickFrom, getQtyToPick } from '../../../utils/picking';
import { postStepPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import StepScanScreenComponent from '../common/StepScanScreenComponent';

class PickStepScanScreen extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      scannedBarcode: null,
    };
  }

  onComponentDidMount() {
    const { applicationId, wfProcessId, activityId, lineId, stepId, altStepId, stepProps } = this.props;
    const { pushHeaderEntry } = this.props;
    const location = pickingStepScanScreenLocation({
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
    });
    const headerHuCode = getPickFrom({ stepProps, altStepId }).huBarcode;
    const headerQtyToPick = getQtyToPick({ stepProps, altStepId }).qtyPicked;

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('general.Barcode'),
          value: headerHuCode,
        },
        {
          caption: counterpart.translate('general.QtyToPick'),
          value: headerQtyToPick,
        },
      ],
    });
  }

  setScannedBarcode = (scannedBarcode) => {
    this.setState({ scannedBarcode });
  };

  pushUpdatedQuantity = ({ qty = 0, reason = null }) => {
    const { updatePickingStepQty, wfProcessId, activityId, lineId, stepId, go, altStepId, qtyTarget } = this.props;
    const { scannedBarcode } = this.state;
    const qtyRejected = qtyTarget - qty;

    postStepPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: scannedBarcode,
      qtyPicked: qty,
      qtyRejectedReasonCode: reason,
      qtyRejected,
    })
      .then(() => {
        updatePickingStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          altStepId,
          qtyPicked: qty,
          qtyRejected,
          qtyRejectedReasonCode: reason,
        });
        go(-2); // go to picking line screen
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  render() {
    const { eligibleBarcode, qtyTarget, stepProps } = this.props;
    return (
      <StepScanScreenComponent
        eligibleBarcode={eligibleBarcode}
        qtyTarget={qtyTarget}
        qtyCaption={counterpart.translate('general.QtyToPick')}
        stepProps={stepProps}
        //
        pushUpdatedQuantity={this.pushUpdatedQuantity}
        setScannedBarcode={this.setScannedBarcode}
      />
    );
  }
}

const mapStateToProps = (state, { match }) => {
  const { applicationId, workflowId: wfProcessId, activityId, lineId, stepId, altStepId } = match.params;

  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const stepProps = wfProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  return {
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
    stepProps,
    qtyTarget: altStepId ? stepProps.pickFromAlternatives[altStepId].qtyToPick : stepProps.qtyToPick,
    eligibleBarcode: altStepId ? stepProps.pickFromAlternatives[altStepId].huBarcode : stepProps.mainPickFrom.huBarcode,
  };
};

PickStepScanScreen.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  altStepId: PropTypes.string,
  eligibleBarcode: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  qtyTarget: PropTypes.number,
  // Actions:
  go: PropTypes.func.isRequired,
  updatePickingStepQty: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, {
    updatePickingStepQty,
    go,
    pushHeaderEntry,
  })(PickStepScanScreen)
);
