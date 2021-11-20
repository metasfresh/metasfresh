import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

import { updateManufacturingReceiptTarget } from '../../../actions/ManufacturingActions';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';

import StepScanScreenComponent from '../common/StepScanScreenComponent';
// import { toastError } from '../../../utils/toast';

const EMPTY_OBJECT = {};

function ManufacturingReceiptScanScreen(WrappedComponent) {
  const mapStateToProps = (state, { match }) => {
    const { workflowId: wfProcessId, activityId, lineId, appId } = match.params;

    const wfProcess = selectWFProcessFromState(state, wfProcessId);
    const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
    const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;

    return {
      wfProcessId,
      activityId,
      lineId,
      appId,
      lineProps,
      stepProps: EMPTY_OBJECT,
      qtyTarget: null,
      eligibleBarcode: null,
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

      const { updateManufacturingReceiptTarget, wfProcessId, activityId, lineId, go, lineProps } = this.props;

      updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target: { scannedBarcode } });

      // TODO: If quantity is already picked, update on the backend
      if (lineProps.qtyReceived) {
        // postStepDistributionMove({
        //   wfProcessId,
        //   activityId,
        //   stepId,
        //   dropTo: {
        //     qtyPicked: qty,
        //     qtyRejectedReasonCode: reason,
        //   },
        // })
        //   .then(() => {
        //     updateDistributionStepQty({
        //       wfProcessId,
        //       activityId,
        //       lineId,
        //       stepId,
        //       locatorBarcode: scannedBarcode,
        //       qtyPicked: qty,
        //       qtyRejectedReasonCode: reason,
        //     });
        //     go(-1);
        //   })
        //   .catch((axiosError) => toastError({ axiosError }));
      }
      go(-1);
    };

    render() {
      return (
        <WrappedComponent
          pushUpdatedQuantity={this.pushUpdatedQuantity}
          setScannedBarcode={this.setScannedBarcode}
          qtyCaption={counterpart.translate('activities.mfg.receipts.pickPromptTitle')}
          {...this.props}
        />
      );
    }
  }

  Wrapped.propTypes = {
    wfProcessId: PropTypes.string.isRequired,
    activityId: PropTypes.string.isRequired,
    lineId: PropTypes.string.isRequired,
    lineProps: PropTypes.object.isRequired,

    // Actions:
    go: PropTypes.func.isRequired,
    updateManufacturingReceiptTarget: PropTypes.func.isRequired,
  };

  return withRouter(
    connect(mapStateToProps, {
      updateManufacturingReceiptTarget,
      go,
    })(Wrapped)
  );
}

export default ManufacturingReceiptScanScreen(StepScanScreenComponent);
