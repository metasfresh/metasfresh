import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { push } from 'connected-react-router';

import { postStepUnPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import { toastError } from '../../../utils/toast';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class PickStepScreen extends Component {
  onScanHUButtonClick = () => {
    const { stepProps, dispatch, onScanButtonClick, altStepId } = this.props;
    const { huBarcode, qtyToPick } = stepProps;

    onScanButtonClick();

    const headerHuCode = altStepId ? stepProps.altSteps.genSteps[altStepId].huBarcode : huBarcode;
    const headerQtyToPick = altStepId ? stepProps.altSteps.genSteps[altStepId].qtyAvailable : qtyToPick;

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.Barcode'),
            value: headerHuCode,
          },
          {
            caption: counterpart.translate('general.QtyToPick'),
            value: headerQtyToPick,
          },
        ],
      })
    );
  };

  onUnpickButtonClick = () => {
    const {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps: { scannedHUBarcode },
      dispatch,
    } = this.props;

    postStepUnPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: scannedHUBarcode,
    })
      .then(() => {
        dispatch(
          updatePickingStepQty({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            scannedHUBarcode: null,
            qtyPicked: 0,
            qtyRejectedReasonCode: null,
          })
        );
        dispatch(push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`));
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
      dispatch,
    } = this.props;
    qtyPicked === '' && dispatch(updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qtyPicked: 0 }));
  }

  render() {
    console.log('P:', this.props);

    const { stepProps, altStepId } = this.props;
    const { huBarcode, qtyToPick, scannedHUBarcode, qtyPicked } = stepProps;

    const isValidCode = !!scannedHUBarcode;
    const scanButtonCaption = isValidCode
      ? `${scannedHUBarcode}`
      : counterpart.translate('activities.picking.scanHUBarcode');

    const scanButtonStatus = isValidCode ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
    const nothingPicked = !isValidCode || !qtyPicked;

    const customHuCode = altStepId ? stepProps.altSteps.genSteps[altStepId].huBarcode : huBarcode;
    const customQtyToPick = altStepId ? stepProps.altSteps.genSteps[altStepId].qtyAvailable : qtyToPick;

    return (
      <>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.Barcode')}
          </div>
          <div className="column is-half has-text-left pb-0">{customHuCode}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.QtyToPick')}:
          </div>
          <div className="column is-half has-text-left pb-0">{customQtyToPick}</div>
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
            <ButtonWithIndicator caption={counterpart.translate('activities.picking.unPickBtn')} />
          </button>
        </div>
      </>
    );
  }
}

PickStepScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  altStepId: PropTypes.string,
  stepProps: PropTypes.object.isRequired,
  onScanButtonClick: PropTypes.func.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default PickStepScreen;
