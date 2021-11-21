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
  /**
   * @method handleScanButtonClick
   * @summary Redirects the user to the scanner screen and sets the header parameters
   * @param {bool} locator - depending on this param we either use the flow for scanning HU or drop to locator.
   *                         This way we can reuse all of the components.
   */
  handleScanButtonClick = (e, locator) => {
    const {
      stepProps: { dropToLocator, qtyToMove },
      dispatch,
      onScanButtonClick,
    } = this.props;
    const locatorId = locator ? dropToLocator.caption : null;

    onScanButtonClick(locatorId);

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.DropToLocator'),
            value: dropToLocator.barcode,
          },
          {
            caption: counterpart.translate('general.QtyMoved'),
            value: qtyToMove,
          },
        ],
      })
    );
  };

  onUnpickLocatorButtonClick = () => {
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

  onUnpickHUButtonClick = () => {
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
            actualHUPicked: null,
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
      stepProps: { dropToLocator, qtyToMove, actualHUPicked, qtyPicked, scannedLocator },
    } = this.props;

    const isValidHUCode = !!actualHUPicked;
    const isValidLocatorCode = !!scannedLocator;

    const scanHUCaption = isValidHUCode
      ? `${actualHUPicked.caption}`
      : counterpart.translate('activities.distribution.scanHU');
    const scanLocatorCaption = isValidLocatorCode
      ? `${scannedLocator}`
      : counterpart.translate('activities.distribution.scanLocator');

    const scanHUButtonStatus = isValidHUCode ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
    const scanLocatorButtonStatus = isValidLocatorCode ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
    const noHUPicked = !isValidHUCode || !qtyPicked;
    const noLocatorPicked = !isValidLocatorCode;

    return (
      <>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.DropToLocator')}
          </div>
          <div className="column is-half has-text-left pb-0">{dropToLocator.barcode}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.QtyMoved')}:
          </div>
          <div className="column is-half has-text-left pb-0">{qtyToMove}</div>
        </div>

        <div className="mt-0">
          <button className="button is-outlined complete-btn" onClick={this.handleScanButtonClick}>
            <ButtonWithIndicator caption={scanHUCaption} completeStatus={scanHUButtonStatus} />
          </button>
        </div>

        <div className="mt-5">
          <button
            className="button is-outlined complete-btn"
            disabled={noHUPicked}
            onClick={this.onUnpickHUButtonClick}
          >
            <ButtonWithIndicator caption={counterpart.translate('activities.picking.unPickBtn')} />
          </button>
        </div>

        <div className="mt-5">
          <button className="button is-outlined complete-btn" onClick={(e) => this.handleScanButtonClick(e, true)}>
            <ButtonWithIndicator caption={scanLocatorCaption} completeStatus={scanLocatorButtonStatus} />
          </button>
        </div>
        {/* Unpick button */}
        <div className="mt-5">
          <button
            className="button is-outlined complete-btn"
            disabled={noLocatorPicked}
            onClick={this.onUnpickLocatorButtonClick}
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
