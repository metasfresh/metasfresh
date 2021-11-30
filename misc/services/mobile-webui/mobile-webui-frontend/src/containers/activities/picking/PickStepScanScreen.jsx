import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { toastError } from '../../../utils/toast';
import { postStepPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';

import StepScanScreenComponent from '../common/StepScanScreenComponent';

function PickStepScanScreen(WrappedComponent) {
  const mapStateToProps = (state, { match }) => {
    const { workflowId: wfProcessId, activityId, lineId, stepId, appId, altStepId } = match.params;

    const wfProcess = selectWFProcessFromState(state, wfProcessId);
    const stepProps = wfProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

    return {
      appId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
      stepProps,
      qtyTarget: altStepId ? stepProps.pickFromAlternatives[altStepId].qtyToPick : stepProps.qtyToPick,
      eligibleBarcode: altStepId
        ? stepProps.pickFromAlternatives[altStepId].huBarcode
        : stepProps.mainPickFrom.huBarcode,
    };
  };

  class Wrapped extends PureComponent {
    constructor(props) {
      super(props);

      this.state = {
        scannedBarcode: null,
      };
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
          go(-2);
        })
        .catch((axiosError) => toastError({ axiosError }));
    };

    render() {
      return (
        <WrappedComponent
          pushUpdatedQuantity={this.pushUpdatedQuantity}
          setScannedBarcode={this.setScannedBarcode}
          qtyCaption={counterpart.translate('general.QtyToPick')}
          {...this.props}
        />
      );
    }
  }

  Wrapped.propTypes = {
    componentProps: PropTypes.object,
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
  };

  return withRouter(
    connect(mapStateToProps, {
      updatePickingStepQty,
      go,
    })(Wrapped)
  );
}

export default PickStepScanScreen(StepScanScreenComponent);
