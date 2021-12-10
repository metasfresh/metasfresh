import React, { Component } from 'react';
import PropTypes from 'prop-types';

import PickStepButton from './PickStepButton';

class PickAlternatives extends Component {
  renderAlternatives = () => {
    const { wfProcessId, activityId, lineId, stepId, pickFromAlternatives, uom } = this.props;

    return (
      <div>
        {pickFromAlternatives &&
          Object.values(pickFromAlternatives)
            .filter((pickFromAlternative) => pickFromAlternative.isDisplayed)
            .map((pickFromAlternative) => {
              return (
                <PickStepButton
                  key={pickFromAlternative.alternativeId}
                  wfProcessId={wfProcessId}
                  activityId={activityId}
                  lineId={lineId}
                  stepId={stepId}
                  altStepId={pickFromAlternative.alternativeId}
                  //
                  uom={uom}
                  qtyToPick={pickFromAlternative.qtyToPick}
                  pickFrom={pickFromAlternative}
                />
              );
            })}
      </div>
    );
  };

  render() {
    return <div>{this.renderAlternatives()}</div>;
  }
}

PickAlternatives.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  pickFromAlternatives: PropTypes.object.isRequired,
  uom: PropTypes.string.isRequired,
};

export default PickAlternatives;
