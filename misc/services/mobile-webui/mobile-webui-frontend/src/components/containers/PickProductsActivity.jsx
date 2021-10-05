import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { v4 as uuidv4 } from 'uuid';
import PickProductsLine from '../PickProductsLine';
class PickProductsActivity extends Component {
  render() {
    const {
      componentProps: { lines },
      activityState,
      wfProcessId,
      activityId,
    } = this.props;

    const { isLinesListVisible } = activityState.dataStored;

    return (
      <div className="pick-products-activity-container mt-5">
        {/* Lines listing */}
        {lines.length > 0 &&
          isLinesListVisible &&
          lines.map((lineItem, lineIndex) => {
            let uniqueId = uuidv4();
            return (
              <PickProductsLine
                key={uniqueId}
                id={uniqueId}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineIndex={lineIndex}
                {...lineItem}
              />
            );
          })}
      </div>
    );
  }
}

PickProductsActivity.propTypes = {
  caption: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
  wfProcessId: PropTypes.string,
  activityId: PropTypes.string,
};

export default PickProductsActivity;
