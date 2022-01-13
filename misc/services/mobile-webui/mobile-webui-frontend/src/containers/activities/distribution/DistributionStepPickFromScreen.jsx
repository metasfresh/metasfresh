import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { go } from 'connected-react-router';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';

import { postDistributionPickFrom } from '../../../api/distribution';
import { distributionStepPickFromScreenLocation } from '../../../routes/distribution';
import { toastError } from '../../../utils/toast';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { updateDistributionPickFrom } from '../../../actions/DistributionActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ScanHUAndGetQtyComponent from '../common/ScanHUAndGetQtyComponent';

class DistributionStepPickFromScreen extends PureComponent {
  componentDidMount() {
    const { wfProcessId, activityId, lineId, stepId, qtyToMove, huBarcode } = this.props;
    const location = distributionStepPickFromScreenLocation({
      wfProcessId,
      activityId,
      lineId,
      stepId,
    });

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('activities.distribution.scanHU'),
          value: huBarcode,
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
    const { updateDistributionPickFrom, go } = this.props;

    postDistributionPickFrom({
      wfProcessId,
      activityId,
      stepId,
      pickFrom: {
        qtyPicked: qty,
        qtyRejectedReasonCode: reason,
      },
    })
      .then(() => {
        updateDistributionPickFrom({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyPicked: qty,
          qtyRejectedReasonCode: reason,
        });

        go(-2);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  render() {
    const { huBarcode, qtyToMove, uom } = this.props;
    return (
      <ScanHUAndGetQtyComponent
        eligibleBarcode={huBarcode}
        qtyCaption={counterpart.translate('general.QtyToMove')}
        qtyInitial={qtyToMove}
        qtyTarget={qtyToMove}
        uom={uom}
        invalidQtyMessageKey={'activities.distribution.invalidQtyToMove'}
        onResult={this.onResult}
      />
    );
  }
}

DistributionStepPickFromScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyToMove: PropTypes.number.isRequired,
  huBarcode: PropTypes.string.isRequired,
  //
  // Actions
  go: PropTypes.func.isRequired,
  updateDistributionPickFrom: PropTypes.func.isRequired,
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
    qtyToMove: stepProps.qtyToMove,
    uom: stepProps.uom,
    huBarcode: stepProps.pickFromHU.barcode,
  };
};

export default withRouter(
  connect(mapStateToProps, { go, updateDistributionPickFrom, pushHeaderEntry })(DistributionStepPickFromScreen)
);
