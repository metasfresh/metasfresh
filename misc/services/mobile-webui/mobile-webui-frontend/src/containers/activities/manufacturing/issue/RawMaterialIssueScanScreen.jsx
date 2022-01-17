import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';

import { selectWFProcessFromState } from '../../../../reducers/wfProcesses_status';
import { updateManufacturingIssueQty, updateManufacturingIssue } from '../../../../actions/ManufacturingActions';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { toastError } from '../../../../utils/toast';
import { manufacturingScanScreenLocation } from '../../../../routes/manufacturing_issue';

import StepScanScreenComponent from '../../common/StepScanScreenComponent';

class RawMaterialIssueScanScreen extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      scannedBarcode: null,
    };
  }

  componentDidMount() {
    const {
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps: { huBarcode, qtyToIssue },
    } = this.props;
    const location = manufacturingScanScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId });

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('general.Barcode'),
          value: huBarcode,
        },
        {
          caption: counterpart.translate('activities.mfg.issues.qtyToIssue'),
          value: qtyToIssue,
        },
      ],
    });
  }

  setScannedBarcode = (scannedBarcode) => {
    this.setState({ scannedBarcode });
  };

  pushUpdatedQuantity = ({ qty = 0, reason = null }) => {
    const { wfProcessId, activityId, lineId, stepId, updateManufacturingIssueQty, updateManufacturingIssue, go } =
      this.props;

    updateManufacturingIssueQty({
      wfProcessId,
      activityId,
      lineId,
      stepId,
      qtyPicked: qty,
      qtyRejectedReasonCode: reason,
    });
    updateManufacturingIssue({ wfProcessId, activityId, lineId, stepId })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => go(-1));
  };

  render() {
    const { stepProps } = this.props;
    return (
      <StepScanScreenComponent
        eligibleBarcode={stepProps.huBarcode}
        qtyTarget={stepProps.qtyToIssue}
        qtyCaption={counterpart.translate('general.QtyToPick')}
        stepProps={stepProps}
        // Callbacks:
        pushUpdatedQuantity={this.pushUpdatedQuantity}
        setScannedBarcode={this.setScannedBarcode}
      />
    );
  }
}

const mapStateToProps = (state, { match }) => {
  const { applicationId, workflowId: wfProcessId, activityId, lineId, stepId } = match.params;

  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const stepProps = wfProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  return {
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    stepProps,
  };
};

RawMaterialIssueScanScreen.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  // Actions:
  go: PropTypes.func.isRequired,
  updateManufacturingIssueQty: PropTypes.func.isRequired,
  updateManufacturingIssue: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, {
    go,
    updateManufacturingIssueQty,
    updateManufacturingIssue,
    pushHeaderEntry,
  })(RawMaterialIssueScanScreen)
);
