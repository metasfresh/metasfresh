import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { push } from 'connected-react-router';

import { postStepUnPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import { toastError } from '../../../utils/toast';
import { getLocation, getPickFrom, getQtyToPick } from '../../../utils';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class PickStepScreen extends Component {
  onUnpickButtonClick = () => {
    const { wfProcessId, activityId, lineId, stepId, dispatch } = this.props;

    postStepUnPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: getPickFrom(this.props).huBarcode,
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

  componentDidMount() {
    const {
      dispatch,
      stepProps: { mainPickFrom },
    } = this.props;
    const location = getLocation(this.props);

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.Locator'),
            value: mainPickFrom.locatorName,
          },
        ],
      })
    );
  }

  componentWillUnmount() {
    const { wfProcessId, activityId, lineId, stepId, dispatch } = this.props;

    const qtyPicked = getPickFrom(this.props).qtyPicked;
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
    const { onScanButtonClick } = this.props;
    const qtyToPick = getQtyToPick(this.props);
    const pickFrom = getPickFrom(this.props);
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
          <div className="column is-half has-text-left pb-0">{qtyToPick}</div>
        </div>
        <div className="mt-0">
          <button className="button is-outlined complete-btn" disabled={isPickedFromHU} onClick={onScanButtonClick}>
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
  //
  stepProps: PropTypes.object.isRequired,
  //
  // Actions
  onScanButtonClick: PropTypes.func.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default PickStepScreen;
