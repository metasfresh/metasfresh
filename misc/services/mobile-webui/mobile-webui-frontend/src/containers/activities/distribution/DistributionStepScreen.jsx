import React, { Component } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';

import { updateDistributionStepQty } from '../../../actions/DistributionActions';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class DistributionStepScreen extends Component {
  handleScanButtonClick = (locator) => {
    const {
      stepProps: { dropToLocator, qtyToMove },
      dispatch,
      onScanButtonClick,
    } = this.props;
    onScanButtonClick(locator);

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

    // TODO: Update on the backend
    dispatch(
      updateDistributionStepQty({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        droppedToLocator: null,
        qtyPicked: 0,
        qtyRejectedReasonCode: null,
      })
    );
  };

  onUnpickHUButtonClick = () => {
    const { wfProcessId, activityId, lineId, stepId, dispatch } = this.props;

    // TODO: Update on the backend
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
      stepProps: { huBarcode, qtyToMove, actualHUPicked, droppedToLocator, qtyPicked },
    } = this.props;

    const isValidHUCode = !!actualHUPicked;
    const isValidLocatorCode = !!droppedToLocator;

    const scanHUCaption = isValidHUCode ? `${actualHUPicked}` : counterpart.translate('activities.distribution.scanHU');
    const scanLocatorCaption = isValidLocatorCode
      ? `${droppedToLocator}`
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
          <div className="column is-half has-text-left pb-0">{huBarcode}</div>
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
            <ButtonWithIndicator caption="Unpick" />
          </button>
        </div>

        <div className="mt-0">
          <button className="button is-outlined complete-btn" onClick={() => this.handleScanButtonClick(true)}>
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
            <ButtonWithIndicator caption="Unpick" />
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
