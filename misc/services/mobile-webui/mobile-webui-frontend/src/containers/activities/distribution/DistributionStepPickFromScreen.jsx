import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { go } from 'connected-react-router';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import PropTypes from 'prop-types';
import ScanHUAndGetQtyComponent from '../common/ScanHUAndGetQtyComponent';
import { postDistributionPickFrom } from '../../../api/distribution';
import { toastError } from '../../../utils/toast';
import counterpart from 'counterpart';
import { updateDistributionPickFrom } from '../../../actions/DistributionActions';

class DistributionStepPickFromScreen extends PureComponent {
  onResult = ({ qty = 0, reason = null }) => {
    console.log(`!!!!! qty=${qty}`);
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

        const result = go(-2);
        console.log('!!!!!!!!!!!!!!!!! goto result', result);
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

  console.log('stepProps', stepProps);

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

export default withRouter(connect(mapStateToProps, { go, updateDistributionPickFrom })(DistributionStepPickFromScreen));
