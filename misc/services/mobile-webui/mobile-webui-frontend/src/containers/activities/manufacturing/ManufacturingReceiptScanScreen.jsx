import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

// import { postStepDistributionMove } from '../../../api/distribution';
// import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { updateManufacturingReceiptTarget } from '../../../actions/ManufacturingActions';

import StepScanScreenComponent from '../common/StepScanScreenComponent';
// import { toastError } from '../../../utils/toast';

const EMPTY_OBJECT = {};

function ManufacturingReceiptScanScreen(WrappedComponent) {
  const mapStateToProps = (state, { match }) => {
    const { workflowId: wfProcessId, activityId, lineId, appId } = match.params;

    return {
      wfProcessId,
      activityId,
      lineId,
      appId,
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

      // const { updateManufacturingReceiptTarget, wfProcessId, activityId, lineId, stepId, go } = this.props;
      // const { scannedBarcode } = this.state;

      console.log('pushUpdatedQuantity: ', scannedBarcode);

      // if (locatorId) {
      //   postStepDistributionMove({
      //     wfProcessId,
      //     activityId,
      //     stepId,
      //     dropTo: {
      //       qtyPicked: qty,
      //       qtyRejectedReasonCode: reason,
      //     },
      //   })
      //     .then(() => {
      //       updateDistributionStepQty({
      //         wfProcessId,
      //         activityId,
      //         lineId,
      //         stepId,
      //         locatorBarcode: scannedBarcode,
      //         qtyPicked: qty,
      //         qtyRejectedReasonCode: reason,
      //       });
      //       go(-1);
      //     })
      //     .catch((axiosError) => toastError({ axiosError }));
      // } else {
      //   postStepDistributionMove({
      //     wfProcessId,
      //     activityId,
      //     stepId,
      //     pickFrom: {
      //       qtyPicked: qty,
      //       qtyRejectedReasonCode: reason,
      //     },
      //   })
      //     .then(() => {
      //       updateDistributionStepQty({
      //         wfProcessId,
      //         activityId,
      //         lineId,
      //         stepId,
      //         actualHUPicked: {
      //           barcode: scannedBarcode,
      //           caption: scannedBarcode,
      //         },
      //         qtyPicked: qty,
      //         qtyRejectedReasonCode: reason,
      //       });
      go(-1);
      //     })
      //     .catch((axiosError) => toastError({ axiosError }));
      // }
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
    componentProps: PropTypes.object,
    wfProcessId: PropTypes.string.isRequired,
    activityId: PropTypes.string.isRequired,
    lineId: PropTypes.string.isRequired,
    stepId: PropTypes.string,
    eligibleBarcode: PropTypes.string,
    stepProps: PropTypes.object.isRequired,
    locatorId: PropTypes.string,

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
