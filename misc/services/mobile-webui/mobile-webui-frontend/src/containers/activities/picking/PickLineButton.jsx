import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push } from 'connected-react-router';

import { getLocation } from '../../../utils';
import LineButton from '../common/LineButton';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/ButtonQuantityProp';

class PickLineButton extends PureComponent {
  handleClick = () => {
    const { dispatch } = this.props;
    const location = getLocation(this.props);

    dispatch(push(location));
  };

  render() {
    const { caption, uom, qtyPicked, qtyToPick, completeStatus, appId, lineId, isUserEditable } = this.props;

    return (
      <button
        key={lineId}
        className="button is-outlined complete-btn"
        disabled={!isUserEditable}
        onClick={this.handleClick}
      >
        <ButtonWithIndicator caption={caption} completeStatus={completeStatus}>
          <ButtonQuantityProp qtyCurrent={qtyPicked} qtyTarget={qtyToPick} uom={uom} appId={appId} />
        </ButtonWithIndicator>
      </button>
    );
  }
}

PickLineButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyPicked: PropTypes.number.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  appId: PropTypes.string.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default LineButton(PickLineButton);
