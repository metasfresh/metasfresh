import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push } from 'connected-react-router';
import { connect } from 'react-redux';

import { pickingLineScreenLocation } from '../../../routes/picking';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/ButtonQuantityProp';

class PickLineButton extends PureComponent {
  handleClick = () => {
    const { push, applicationId, wfProcessId, activityId, lineId } = this.props;
    const location = pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId });

    push(location);
  };

  render() {
    const { caption, uom, qtyPicked, qtyToPick, completeStatus, applicationId, lineId, isUserEditable } = this.props;

    return (
      <div className="buttons">
        <button
          key={lineId}
          className="button is-outlined complete-btn"
          disabled={!isUserEditable}
          onClick={this.handleClick}
        >
          <ButtonWithIndicator caption={caption} completeStatus={completeStatus}>
            <ButtonQuantityProp qtyCurrent={qtyPicked} qtyTarget={qtyToPick} uom={uom} applicationId={applicationId} />
          </ButtonWithIndicator>
        </button>
      </div>
    );
  }
}

PickLineButton.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyPicked: PropTypes.number.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  //
  // Actions
  push: PropTypes.func.isRequired,
};

export default connect(null, { push })(PickLineButton);
