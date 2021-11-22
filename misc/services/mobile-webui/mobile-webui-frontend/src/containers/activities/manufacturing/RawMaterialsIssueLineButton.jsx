import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import LineButton from '../common/LineButton';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/ButtonQuantityProp';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class RawMaterialsIssueLineButton extends Component {
  handleClick = () => {
    const { dispatch, caption } = this.props;

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('activities.mfg.ProductName'),
            value: caption,
            bold: true,
          },
        ],
      })
    );
  };

  render() {
    const { caption, uom, qtyIssued, qtyToIssue, completeStatus, appId } = this.props;

    return (
      <>
        <ButtonWithIndicator caption={caption} completeStatus={completeStatus}>
          <ButtonQuantityProp
            qtyCurrent={qtyIssued}
            qtyTarget={qtyToIssue}
            uom={uom}
            appId={appId}
            subtypeId="issues"
          />
        </ButtonWithIndicator>
      </>
    );
  }
}

RawMaterialsIssueLineButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyIssued: PropTypes.number.isRequired,
  qtyToIssue: PropTypes.number.isRequired,
  appId: PropTypes.string.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default LineButton(RawMaterialsIssueLineButton);
