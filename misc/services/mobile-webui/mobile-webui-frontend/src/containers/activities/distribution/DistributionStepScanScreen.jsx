import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { updateDistributionStepQty } from '../../../actions/DistributionActions';

import StepScanScreenComponent from '../StepScanScreenComponent';

function DistributionStepScanScreen(WrappedComponent) {
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
      qtyTarget: stepProps.qtyToMove,
      eligibleBarcode: locatorId ? stepProps.dropToLocator.barcode : stepProps.pickFromHU.barcode,
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
    };

    pushUpdatedQuantity = ({ qty = 0, reason = null }) => {
      const { updateDistributionStepQty, wfProcessId, activityId, lineId, stepId, go, locatorId } = this.props;
      const { scannedBarcode } = this.state;

      // TODO: Update on the backend
      if (locatorId) {
        updateDistributionStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          locatorBarcode: scannedBarcode,
          qtyPicked: qty,
          qtyRejectedReasonCode: reason,
        });
      } else {
        updateDistributionStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          actualHUPicked: scannedBarcode,
          qtyPicked: qty,
          qtyRejectedReasonCode: reason,
        });
      }

      go(-2);
    };

    render() {
      return (
        <WrappedComponent
          pushUpdatedQuantity={this.pushUpdatedQuantity}
          setScannedBarcode={this.setScannedBarcode}
          qtyCaption={counterpart.translate('general.QtyToMove')}
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

export default DistributionStepScanScreen(StepScanScreenComponent);
