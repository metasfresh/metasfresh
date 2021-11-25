import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { push } from 'connected-react-router';

import { postStepUnPicked, postStepPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import { toastError } from '../../../utils/toast';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import NotFoundButton from '../NotFoundButton';

class PickStepScreen extends Component {
  getPickFrom = () => {
    const { stepProps, altStepId } = this.props;
    return altStepId ? stepProps.pickFromAlternatives[altStepId] : stepProps.mainPickFrom;
  };

  getQtyToPick = () => {
    const { stepProps, altStepId } = this.props;
    return altStepId ? this.getPickFrom().qtyToPick : stepProps.qtyToPick;
  };

  onScanHUButtonClick = () => {
    const { dispatch, onScanButtonClick } = this.props;

    onScanButtonClick();

    const headerHuCode = this.getPickFrom().huBarcode;
    const headerQtyToPick = this.getQtyToPick();

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
    const { wfProcessId, activityId, lineId, stepId, dispatch } = this.props;

    postStepUnPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: this.getPickFrom().huBarcode,
    })
      .then(() => {
        dispatch(
          updatePickingStepQty({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            qtyPicked: 0,
            qtyRejected: 0,
            qtyRejectedReasonCode: null,
          })
        );
        dispatch(push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`));
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  handleNotFound = () => {
    const { wfProcessId, stepId, lineId, activityId, dispatch } = this.props;
    const huBarcode = this.getPickFrom().huBarcode;
    const qtyRejected = this.getQtyToPick();

    postStepPicked({
      wfProcessId,
      activityId,
      stepId,
      qtyPicked: 0,
      qtyRejected,
      qtyRejectedReasonCode: 'N',
      huBarcode,
    }).then(() => {
      dispatch(
        updatePickingStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyPicked: 0,
          qtyRejected,
          qtyRejectedReasonCode: 'N',
        })
      );
    });
  };

  componentWillUnmount() {
    const { wfProcessId, activityId, lineId, stepId, dispatch } = this.props;

    const qtyPicked = this.getPickFrom().qtyPicked;
    qtyPicked === '' &&
      dispatch(
        updatePickingStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          qtyPicked: 0,
        })
      );
  }

  render() {
    const { wfProcessId, activityId, lineId, stepId } = this.props;
    const pickFrom = this.getPickFrom();
    const isPickedFromHU = pickFrom.qtyPicked > 0;

    const scanButtonCaption = isPickedFromHU
      ? `${pickFrom.huBarcode}`
      : counterpart.translate('activities.picking.scanHUBarcode');

    const scanButtonStatus = isPickedFromHU ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
    const nothingPicked = !isPickedFromHU && !pickFrom.qtyRejectedReasonCode;

    return (
      <>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.Barcode')}
          </div>
          <div className="column is-half has-text-left pb-0">{pickFrom.huBarcode}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.QtyToPick')}:
          </div>
          <div className="column is-half has-text-left pb-0">{this.getQtyToPick()}</div>
        </div>
        <div className="mt-0">
          <button
            className="button is-outlined complete-btn"
            disabled={isPickedFromHU}
            onClick={this.onScanHUButtonClick}
          >
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
        <NotFoundButton
          onNotFound={this.handleNotFound}
          disabled={!nothingPicked}
          {...{ wfProcessId, activityId, stepId, lineId }}
        />
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
  //
  stepProps: PropTypes.object.isRequired,
  //
  // Actions
  onScanButtonClick: PropTypes.func.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default PickStepScreen;
