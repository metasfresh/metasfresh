import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push } from 'connected-react-router';

import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/ButtonQuantityProp';
import { distributionLineScreenLocation } from '../../../routes/distribution';
import { connect } from 'react-redux';

class DistributionLineButton extends PureComponent {
  handleClick = () => {
    const { dispatch, wfProcessId, activityId, lineId } = this.props;
    const location = distributionLineScreenLocation({ wfProcessId, activityId, lineId });

    dispatch(push(location));
  };

  render() {
    const { caption, uom, qtyPicked, qtyToMove, completeStatus, isUserEditable } = this.props;

    return (
      <ButtonWithIndicator
        caption={caption}
        completeStatus={completeStatus}
        disabled={!isUserEditable}
        onClick={this.handleClick}
      >
        <ButtonQuantityProp qtyCurrent={qtyPicked} qtyTarget={qtyToMove} uom={uom} applicationId="distribution" />
      </ButtonWithIndicator>
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
