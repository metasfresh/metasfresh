import React, { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { go } from 'connected-react-router';

import { updateManufacturingIssueQty } from '../../../actions/ManufacturingActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import IssueQuantityButton from './RawMaterialIssueButton';

class RawMaterialIssueStepScreen extends Component {
  componentDidMount() {
    const {
      stepProps: { huBarcode, qtyToIssue },
      location,
      dispatch,
    } = this.props;

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.Barcode'),
            value: huBarcode,
          },
          {
            caption: counterpart.translate('activities.mfg.issues.toIssue'),
            value: qtyToIssue,
          },
        ],
      })
    );
  }

  handleClick = (qtyPicked) => {
    const { dispatch, wfProcessId, activityId, lineId, stepId } = this.props;

    dispatch(updateManufacturingIssueQty({ wfProcessId, activityId, lineId, stepId, qtyPicked }));
    dispatch(go(-1));
  };

  render() {
    const { activityId, wfProcessId, lineId, stepProps } = this.props;

    return (
      <IssueQuantityButton
        wfProcessId={wfProcessId}
        activityId={activityId}
        lineId={lineId}
        stepId={stepProps.id}
        locatorName={stepProps.productName}
        onClick={this.handleClick}
        {...stepProps}
      />
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
  location: PropTypes.object.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default RawMaterialIssueStepScreen;
