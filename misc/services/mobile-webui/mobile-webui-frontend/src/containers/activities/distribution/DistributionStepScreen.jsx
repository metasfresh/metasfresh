import React, { Component } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import { push } from 'connected-react-router';

import * as CompleteStatus from '../../../constants/CompleteStatus';
import { postStepDistributionMove } from '../../../api/distribution';
import { updateDistributionStepQty } from '../../../actions/DistributionActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { toastError } from '../../../utils/toast';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

class DistributionStepScreen extends Component {
  onScanPickFromHU = () => {
    const {
      stepProps: { qtyToMove, pickFromHU },
      dispatch,
      onScanButtonClick,
    } = this.props;

    onScanButtonClick();

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('activities.distribution.scanHU'),
            value: pickFromHU.barcode,
          },
          {
            caption: counterpart.translate('general.QtyToMove'),
            value: qtyToMove,
          },
        ],
      })
    );
  };

  onUndoPickFromHU = () => {
    const { wfProcessId, activityId, lineId, stepId, dispatch } = this.props;

    postStepDistributionMove({
      wfProcessId,
      activityId,
      stepId,
    })
      .then(() => {
        dispatch(
          updateDistributionStepQty({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            qtyPicked: 0,
            qtyRejectedReasonCode: null,
          })
        );
        dispatch(push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`));
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  onScanDropToLocator = () => {
    const {
      stepProps: { qtyToMove, dropToLocator },
      dispatch,
      onScanButtonClick,
    } = this.props;

    onScanButtonClick({ locatorId: dropToLocator.barcode });

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.DropToLocator'),
            value: dropToLocator.caption + ' (' + dropToLocator.barcode + ')',
          },
          {
            caption: counterpart.translate('general.QtyToMove'),
            value: qtyToMove,
          },
        ],
      })
    );
  };

  onUndoDropToLocator = () => {
    const { wfProcessId, activityId, lineId, stepId, dispatch } = this.props;

    postStepDistributionMove({
      wfProcessId,
      activityId,
      stepId,
    })
      .then(() => {
        dispatch(
          updateDistributionStepQty({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            scannedLocator: null,
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

    qtyPicked === '' && dispatch(updateDistributionStepQty({ wfProcessId, activityId, lineId, stepId, qtyPicked: 0 }));
  }

  render() {
    const {
      stepProps: {
        qtyToMove,
        //
        // Pick From:
        isPickedFrom,
        pickFromHU,
        qtyPicked,
        //
        // Drop To
        droppedToLocator: isDroppedToLocator,
        dropToLocator,
      },
    } = this.props;

    console.log(this.props);

    const pickFromHUCaption = isPickedFrom
      ? pickFromHU.caption
      : counterpart.translate('activities.distribution.scanHU');
    const pickFromHUStatus = isPickedFrom ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

    const dropToLocatorCaption = isDroppedToLocator
      ? dropToLocator.caption
      : counterpart.translate('activities.distribution.scanLocator');
    const dropToLocatorStatus = isDroppedToLocator ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

    return (
      <>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('activities.distribution.scanHU')}
          </div>
          <div className="column is-half has-text-left pb-0">{pickFromHU.barcode}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.QtyToMove')}:
          </div>
          <div className="column is-half has-text-left pb-0">{qtyToMove}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.QtyPicked')}:
          </div>
          <div className="column is-half has-text-left pb-0">{qtyPicked}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.DropToLocator')}
          </div>
          <div className="column is-half has-text-left pb-0">{dropToLocator.caption}</div>
        </div>

        <div className="mt-0">
          <button className="button is-outlined complete-btn" disabled={isPickedFrom} onClick={this.onScanPickFromHU}>
            <ButtonWithIndicator caption={pickFromHUCaption} completeStatus={pickFromHUStatus} />
          </button>
        </div>

        <div className="mt-5">
          <button
            className="button is-outlined complete-btn"
            disabled={!(isPickedFrom && !isDroppedToLocator)}
            onClick={this.onUndoPickFromHU}
          >
            <ButtonWithIndicator caption={counterpart.translate('activities.picking.unPickBtn')} />
          </button>
        </div>

        <div className="mt-5">
          <button
            className="button is-outlined complete-btn"
            disabled={!(isPickedFrom && !isDroppedToLocator)}
            onClick={this.onScanDropToLocator}
          >
            <ButtonWithIndicator caption={dropToLocatorCaption} completeStatus={dropToLocatorStatus} />
          </button>
        </div>
        {/* Undo Drop To Locator button */}
        <div className="mt-5">
          <button
            className="button is-outlined complete-btn"
            disabled={!isDroppedToLocator}
            onClick={this.onUndoDropToLocator}
          >
            <ButtonWithIndicator caption={counterpart.translate('activities.picking.unPickBtn')} />
          </button>
        </div>
      </>
    );
  }
}

DistributionStepScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  onScanButtonClick: PropTypes.func.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default DistributionStepScreen;
