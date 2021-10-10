import React, { Component } from 'react';
import PropTypes from 'prop-types';

import PickLineButton from './PickLineButton';
import * as CompleteStatus from '../../../constants/CompleteStatus';

class PickProductsActivity extends Component {
  render() {
    const {
      componentProps: { lines },
      activityState,
      wfProcessId,
      activityId,
    } = this.props;
    const dataStored = activityState ? activityState.dataStored : {};
    const { completeStatus, isUserEditable } = dataStored;

    return (
      <div className="pick-products-activity-container mt-5">
        {activityState && lines.length > 0
          ? lines.map((lineItem, lineIndex) => {
              const lineId = '' + lineIndex;
              return (
                <PickLineButton
                  key={lineId}
                  wfProcessId={wfProcessId}
                  activityId={activityId}
                  lineId={lineId}
                  isUserEditable={isUserEditable}
                  completeStatus={completeStatus || CompleteStatus.NOT_STARTED}
                  {...lineItem}
                />
              );
            })
          : null}
      </div>
    );
  }
}

PickProductsActivity.propTypes = {
  wfProcessId: PropTypes.string,
  activityId: PropTypes.string,
  caption: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default PickProductsActivity;
