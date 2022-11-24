import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { forEach } from 'lodash';

import PickLineButton from './PickLineButton';
import * as CompleteStatus from '../../../constants/CompleteStatus';

const getLinePickingQuantities = (line) => {
  let picked = 0;
  let toPick = 0;
  let uom = '';

  forEach(line.steps, (step) => {
    const { qtyPicked, qtyToPick, uom: stepUom } = step;

    picked += qtyPicked;
    toPick += qtyToPick;
    uom = stepUom;
  });

  return { picked, toPick, uom };
};

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
              const { picked, toPick, uom } = getLinePickingQuantities(dataStored.lines[lineIndex]);

              return (
                <PickLineButton
                  key={lineId}
                  wfProcessId={wfProcessId}
                  activityId={activityId}
                  lineId={lineId}
                  caption={lineItem.caption}
                  isUserEditable={isUserEditable}
                  completeStatus={completeStatus || CompleteStatus.NOT_STARTED}
                  qtyPicked={picked}
                  qtyToPick={toPick}
                  uom={uom}
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
