import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { push } from 'connected-react-router';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/ButtonQuantityProp';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { distributionLineScreenLocation } from '../../../routes/distribution';
import { connect } from 'react-redux';

class DistributionLineButton extends PureComponent {
  handleClick = () => {
    const { dispatch, caption, wfProcessId, activityId, lineId } = this.props;

    const location = distributionLineScreenLocation({ wfProcessId, activityId, lineId });
    dispatch(push(location));
    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('activities.distribution.DistributionLine'),
            value: caption,
            bold: true,
          },
        ],
      })
    );
  };

  render() {
    const { caption, uom, qtyPicked, qtyToMove, completeStatus, lineId, isUserEditable } = this.props;

    return (
      <button
        key={lineId}
        className="button is-outlined complete-btn"
        disabled={!isUserEditable}
        onClick={this.handleClick}
      >
        <ButtonWithIndicator caption={caption} completeStatus={completeStatus}>
          <ButtonQuantityProp qtyCurrent={qtyPicked} qtyTarget={qtyToMove} uom={uom} appId="distribution" />
        </ButtonWithIndicator>
      </button>
    );
  }
}

DistributionLineButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyToMove: PropTypes.number.isRequired,
  qtyPicked: PropTypes.number.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default connect()(DistributionLineButton);
