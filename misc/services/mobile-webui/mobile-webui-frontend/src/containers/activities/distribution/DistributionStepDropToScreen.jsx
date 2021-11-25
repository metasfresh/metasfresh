import React, { PureComponent } from 'react';
import { postDistributionDropTo } from '../../../api/distribution';
import { updateDistributionDropTo } from '../../../actions/DistributionActions';
import { toastError } from '../../../utils/toast';
import ScanHUAndGetQtyComponent from '../common/ScanHUAndGetQtyComponent';
import PropTypes from 'prop-types';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { go } from 'connected-react-router';

class DistributionStepDropToScreen extends PureComponent {
  onResult = ({ qty = 0, reason = null }) => {
    const { wfProcessId, activityId, lineId, stepId } = this.props;
    const { updateDistributionDropTo, go } = this.props;

    postDistributionDropTo({
      wfProcessId,
      activityId,
      stepId,
    })
      .then(() => {
        updateDistributionDropTo({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyPicked: qty,
          qtyRejectedReasonCode: reason,
          droppedToLocator: true,
        });

        go(-1);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  render() {
    const { locatorBarcode } = this.props;
    return (
      <ScanHUAndGetQtyComponent
        eligibleBarcode={locatorBarcode}
        invalidBarcodeMessageKey={'activities.distribution.invalidLocatorBarcode'}
        onResult={this.onResult}
      />
    );
  }
}

DistributionStepDropToScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  locatorBarcode: PropTypes.string.isRequired,
  //
  // Actions
  go: PropTypes.func.isRequired,
  updateDistributionDropTo: PropTypes.func.isRequired,
};

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
  const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;
  const stepProps = lineProps != null && lineProps.steps ? lineProps.steps[stepId] : {};

  console.log('stepProps', stepProps);

  return {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    locatorBarcode: stepProps.dropToLocator.barcode,
  };
};

export default withRouter(connect(mapStateToProps, { go, updateDistributionDropTo })(DistributionStepDropToScreen));
