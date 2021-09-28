import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { v4 as uuidv4 } from 'uuid';
import PickProductsLine from '../PickProductsLine';
class PickProductsActivity extends Component {
  render() {
    const {
      caption,
      componentProps: { lines },
      activityState,
      wfProcessId,
    } = this.props;

    const { activityId } = activityState;
    const { isLinesListVisible } = activityState.dataStored;

    return (
      <div className="pick-products-activity-container">
        <div className="title is-4 header-caption">{caption}</div>
        {/* Lines listing */}
        {lines.length > 0 &&
          isLinesListVisible &&
          lines.map((lineItem) => {
            let uniqueId = uuidv4();
            return (
              <PickProductsLine
                key={uniqueId}
                id={uniqueId}
                wfProcessId={wfProcessId}
                activityId={activityId}
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
};

export default PickProductsActivity;
