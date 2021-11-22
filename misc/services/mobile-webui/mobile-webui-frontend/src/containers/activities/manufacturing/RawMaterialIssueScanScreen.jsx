import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { updateManufacturingIssueQty, updateManufacturingIssue } from '../../../actions/ManufacturingActions';
import { toastError } from '../../../utils/toast';

import StepScanScreenComponent from '../common/StepScanScreenComponent';

function RawMaterialIssueScanScreen(WrappedComponent) {
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
      qtyTarget: stepProps.qtyToIssue,
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
      const { scannedBarcode } = this.state;
      const { wfProcessId, activityId, lineId, stepId, updateManufacturingIssueQty, updateManufacturingIssue, go } =
        this.props;

      updateManufacturingIssueQty({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        qtyPicked: qty,
        qtyRejectedReasonCode: reason,
        scannedHUBarcode: scannedBarcode,
      });
      updateManufacturingIssue({ wfProcessId, activityId, lineId, stepId })
        .catch((axiosError) => toastError({ axiosError }))
        .finally(() => go(-1));
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
    eligibleBarcode: PropTypes.string.isRequired,
    stepProps: PropTypes.object.isRequired,
    // Actions:
    go: PropTypes.func.isRequired,
    updateManufacturingIssueQty: PropTypes.func.isRequired,
    updateManufacturingIssue: PropTypes.func.isRequired,
  };

  return withRouter(
    connect(mapStateToProps, {
      go,
      updateManufacturingIssueQty,
      updateManufacturingIssue,
    })(Wrapped)
  );
}

export default RawMaterialIssueScanScreen(StepScanScreenComponent);
