import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { go } from 'connected-react-router';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import { postDistributionDropTo } from '../../../api/distribution';
import { updateDistributionDropTo } from '../../../actions/DistributionActions';
import { toastError } from '../../../utils/toast';
import ScanHUAndGetQtyComponent from '../common/ScanHUAndGetQtyComponent';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { distributionStepDropToScreenLocation } from '../../../routes/distribution';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class DistributionStepDropToScreen extends PureComponent {
  componentDidMount() {
    const { wfProcessId, activityId, lineId, stepId, qtyToMove, caption, locatorBarcode, pushHeaderEntry } = this.props;
    const location = distributionStepDropToScreenLocation({
      wfProcessId,
      activityId,
      lineId,
      stepId,
    });

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('general.DropToLocator'),
          value: `${caption}(${locatorBarcode})`,
        },
        {
          caption: counterpart.translate('general.QtyToMove'),
          value: qtyToMove,
        },
      ],
    });
  }

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
  caption: PropTypes.string.isRequired,
  qtyToMove: PropTypes.number.isRequired,
  //
  // Actions
  go: PropTypes.func.isRequired,
  updateDistributionDropTo: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
  const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;
  const stepProps = lineProps != null && lineProps.steps ? lineProps.steps[stepId] : {};

  return {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    caption: stepProps.dropToLocator.caption,
    qtyToMove: stepProps.qtyToMove,
    locatorBarcode: stepProps.pickFromHU.barcode,
  };
};

export default withRouter(
  connect(mapStateToProps, { go, updateDistributionDropTo, pushHeaderEntry })(DistributionStepDropToScreen)
);
