import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

// import { postStepDistributionMove } from '../../../api/distribution';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { updateDistributionStepQty } from '../../../actions/DistributionActions';

import StepScanScreenComponent from '../common/StepScanScreenComponent';
// import { toastError } from '../../../utils/toast';

function ManufacturingHUScanScreen(WrappedComponent) {
  const mapStateToProps = (state, { match }) => {
    const { workflowId: wfProcessId, activityId, lineId, stepId, appId, locatorId } = match.params;
    const wfProcess = selectWFProcessFromState(state, wfProcessId);
    const stepProps = wfProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

    return {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps,
      appId,
      // qtyTarget: stepProps.qtyToMove,
      qtyTarget: null,
      // eligibleBarcode: locatorId ? stepProps.dropToLocator.barcode : stepProps.pickFromHU.barcode,
      eligibleBarcode: null,
      locatorId,
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

      // const { updateDistributionStepQty, wfProcessId, activityId, lineId, stepId, go, locatorId } = this.props;
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
          qtyCaption={counterpart.translate('mfg.receipts.pickPromptTitle')}
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
    locatorId: PropTypes.string,

    // Actions:
    go: PropTypes.func.isRequired,
    updateDistributionStepQty: PropTypes.func.isRequired,
  };

  return withRouter(
    connect(mapStateToProps, {
      updateDistributionStepQty,
      go,
    })(Wrapped)
  );
}

export default ManufacturingHUScanScreen(StepScanScreenComponent);
