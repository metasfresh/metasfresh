import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class RawMaterialIssueStepScreen extends Component {
  onScanHUButtonClick = () => {
    const {
      stepProps: { huBarcode, qtyToIssue },
      dispatch,
      onScanButtonClick,
    } = this.props;
    onScanButtonClick();

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.Barcode'),
            value: huBarcode,
          },
          {
            caption: counterpart.translate('activities.mfg.issues.qtyToIssue'),
            value: qtyToIssue,
          },
        ],
      })
    );
  };

  render() {
    const {
      stepProps: { huBarcode, qtyToIssue, scannedHUBarcode },
    } = this.props;

    const isValidCode = !!scannedHUBarcode;
    const scanButtonCaption = isValidCode
      ? `${scannedHUBarcode}`
      : counterpart.translate('activities.picking.scanHUBarcode');

    const scanButtonStatus = isValidCode ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

    return (
      <>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('general.Barcode')}
          </div>
          <div className="column is-half has-text-left pb-0">{huBarcode}</div>
        </div>
        <div className="columns is-mobile">
          <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">
            {counterpart.translate('activities.mfg.issues.qtyToIssue')}:
          </div>
          <div className="column is-half has-text-left pb-0">{qtyToIssue}</div>
        </div>
        <div className="mt-0">
          <button className="button is-outlined complete-btn" onClick={this.onScanHUButtonClick}>
            <ButtonWithIndicator caption={scanButtonCaption} completeStatus={scanButtonStatus} />
          </button>
        </div>
        {/* Unpick button */}
      </>
    );
  }
}

RawMaterialIssueStepScreen.propTypes = {
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

export default RawMaterialIssueStepScreen;
