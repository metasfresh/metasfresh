import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';
import counterpart from 'counterpart';

import { postStepUnPicked } from '../../../api/picking';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import ScreenToaster from '../../../components/ScreenToaster';
import { toastError } from '../../../utils/toast';

class PickAltStepScreen extends Component {
  onScanHUButtonClick = () => {
    const { wfProcessId, activityId, lineId, stepId, altStepId, stepProps, push, pushHeaderEntry } = this.props;

    const { huBarcode, qtyToPick } = stepProps;
    console.log('P:', this.props);

    const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/altStepId/${altStepId}/scanner`;

    push(location);
    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('general.Barcode'),
          value: altStepId ? stepProps.altSteps.genSteps[altStepId].huBarcode : huBarcode,
        },
        {
          caption: counterpart.translate('general.QtyToPick'),
          value: altStepId ? stepProps.altSteps.genSteps[altStepId].qtyAvailable : qtyToPick,
        },
      ],
    });
  };

  onUnpickButtonClick = () => {
    const {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps: { scannedHUBarcode },
    } = this.props;
    const { updatePickingStepQty, push } = this.props;

    postStepUnPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: scannedHUBarcode,
    })
      .then(() => {
        updatePickingStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          scannedHUBarcode: null,
          qtyPicked: 0,
          qtyRejectedReasonCode: null,
        });
        push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  componentWillUnmount() {
    const {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps: { qtyPicked },
      updatePickingStepQty,
    } = this.props;
    qtyPicked === '' && updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qtyPicked: 0 });
  }

  render() {
    const {
      stepProps: { altSteps },
      altStepId,
    } = this.props;

    const { genSteps } = altSteps;
    const { huBarcode, qtyAvailable: qtyToPick, qtyPicked, scannedHUBarcode } = genSteps[altStepId];

    const isValidCode = !!scannedHUBarcode;
    const scanButtonCaption = isValidCode
      ? `${scannedHUBarcode}`
      : counterpart.translate('activities.picking.scanHUBarcode');

    const scanButtonStatus = isValidCode ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
    const nothingPicked = !isValidCode || !qtyPicked;

    return (
      <div className="pt-3 section picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('general.Barcode')}
            </div>
            <div className="column is-half has-text-left pb-0">{huBarcode}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
              {counterpart.translate('general.QtyToPick')}:
            </div>
            <div className="column is-half has-text-left pb-0">{qtyToPick}</div>
          </div>
          <div className="mt-0">
            <button className="button is-outlined complete-btn" onClick={this.onScanHUButtonClick}>
              <ButtonWithIndicator caption={scanButtonCaption} completeStatus={scanButtonStatus} />
            </button>
          </div>
          {/* Unpick button */}
          <div className="mt-5">
            <button
              className="button is-outlined complete-btn"
              disabled={nothingPicked}
              onClick={this.onUnpickButtonClick}
            >
              <ButtonWithIndicator caption="Unpick" />
            </button>
          </div>
          <ScreenToaster />
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId, altStepId } = ownProps.match.params;

  const stepProps = state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId];

  return {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    stepProps,
    altStepId,
  };
};

PickAltStepScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  altStepId: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  //
  // Actions
  updatePickingStepQty: PropTypes.func.isRequired,
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { updatePickingStepQty, push, pushHeaderEntry })(PickAltStepScreen));
