import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

import { getQtyRejectedReasonsFromActivity, selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { toastError } from '../../../utils/toast';
import { pickingStepScanScreenLocation } from '../../../routes/picking';
import { getPickFrom, getQtyToPick } from '../../../utils/picking';
import { postStepPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import ScanHUAndGetQtyComponent from '../ScanHUAndGetQtyComponent';

class PickStepScanScreen extends PureComponent {
  componentDidMount() {
    const { applicationId, wfProcessId, activityId, lineId, stepId, altStepId, eligibleBarcode, qtyToPick, uom } =
      this.props;
    const { pushHeaderEntry } = this.props;
    const location = pickingStepScanScreenLocation({
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
    });

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('general.Barcode'),
          value: eligibleBarcode,
        },
        {
          caption: counterpart.translate('general.QtyToPick'),
          value: qtyToPick + ' ' + uom,
        },
      ],
    });
  }

  onResult = ({ qty = 0, reason = null, scannedBarcode = null }) => {
    const { updatePickingStepQty, wfProcessId, activityId, lineId, stepId, go, altStepId, qtyToPick } = this.props;
    const qtyRejected = qtyToPick - qty;

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
    const { eligibleBarcode, qtyToPick, uom, qtyRejectedReasons } = this.props;
    return (
      <ScanHUAndGetQtyComponent
        eligibleBarcode={eligibleBarcode}
        qtyCaption={counterpart.translate('general.QtyToPick')}
        qtyTarget={qtyToPick}
        qtyInitial={qtyToPick}
        uom={uom}
        qtyRejectedReasons={qtyRejectedReasons}
        //
        onResult={this.onResult}
      />
    );
  }
}

const mapStateToProps = (state, { match }) => {
  const { applicationId, workflowId: wfProcessId, activityId, lineId, stepId, altStepId } = match.params;

  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess.activities[activityId];

  const stepProps = activity.dataStored.lines[lineId].steps[stepId];
  const eligibleBarcode = getPickFrom({ stepProps, altStepId }).huBarcode;
  const qtyToPick = getQtyToPick({ stepProps, altStepId });

  const qtyRejectedReasons = getQtyRejectedReasonsFromActivity(activity);

  return {
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
    eligibleBarcode: eligibleBarcode,
    qtyToPick: qtyToPick,
    uom: stepProps.uom,
    qtyRejectedReasons,
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
  qtyToPick: PropTypes.number,
  uom: PropTypes.string.isRequired,
  qtyRejectedReasons: PropTypes.array.isRequired,
  // Actions:
  go: PropTypes.func.isRequired,
  updatePickingStepQty: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { updatePickingStepQty, go, pushHeaderEntry })(PickStepScanScreen));
