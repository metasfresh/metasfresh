import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { go } from 'connected-react-router';

import { updateManufacturingIssueQty } from '../../../actions/ManufacturingActions';
import Button from './RawMaterialIssueButton';

class RawMaterialIssueLineScreen extends PureComponent {
  handleClick = (qtyPicked) => {
    const { dispatch, wfProcessId, activityId, lineId } = this.props;

    dispatch(updateManufacturingIssueQty({ wfProcessId, activityId, lineId, qtyPicked }));
    dispatch(go(-1));
  };

  render() {
    const { lineProps } = this.props;

    return (
      <div className="pt-2 section lines-screen-container">
        <div className="steps-container">
          <Button line={lineProps} onClick={this.handleClick} />
        </div>
      </div>
    );
  }
}

RawMaterialIssueLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
  lineId: PropTypes.string.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default RawMaterialIssueLineScreen;
