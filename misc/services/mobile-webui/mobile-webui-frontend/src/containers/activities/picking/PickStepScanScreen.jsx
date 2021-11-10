import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { toastError } from '../../../utils/toast';
import { postStepPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';

import StepScanScreenComponent from '../StepScanScreenComponent';

function PickStepScanScreen(WrappedComponent) {
  const mapStateToProps = (state, { match }) => {
    const { workflowId: wfProcessId, activityId, lineId, stepId, appId } = match.params;

    const wfProcess = selectWFProcessFromState(state, wfProcessId);
    const stepProps = wfProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

    return {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps,
      appId,
      qtyTarget: stepProps.qtyToPick,
      eligibleBarcode: stepProps.huBarcode,
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
      const { updatePickingStepQty, wfProcessId, activityId, lineId, stepId, go } = this.props;
      const { scannedBarcode } = this.state;

      // TODO: This should be added to the same, not next level
      // pushHeaderEntry({
      //   location,
      //   values: [
      //     {
      //       caption: counterpart.translate('general.QtyPicked'),
      //       value: qtyPicked,
      //     },
      //   ],
      // });

      // TODO: We should only set the scanned barcode if the quantity is correct and user submitted any
      // potential reason to wrong quantity.

      postStepPicked({
        wfProcessId,
        activityId,
        stepId,
        huBarcode: scannedBarcode,
        qtyPicked: qty,
        qtyRejectedReasonCode: reason,
      })
        .then(() => {
          updatePickingStepQty({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            scannedHUBarcode: scannedBarcode,
            qtyPicked: qty,
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
    eligibleBarcode: PropTypes.string.isRequired,
    stepProps: PropTypes.object.isRequired,
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
