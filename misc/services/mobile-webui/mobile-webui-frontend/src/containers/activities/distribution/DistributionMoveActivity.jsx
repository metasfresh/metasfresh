import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { forEach } from 'lodash';

import DistributionLineButton from './DistributionLineButton';
import * as CompleteStatus from '../../../constants/CompleteStatus';

const getLinePickingQuantities = (line) => {
  let picked = 0;
  let toMove = 0;
  let uom = '';

  forEach(line.steps, (step) => {
    const { qtyPicked, qtyToMove, uom: stepUom } = step;

    picked += qtyPicked;
    toMove += qtyToMove;
    uom = stepUom;
  });

  return { picked, toMove, uom };
};

class DistributionMoveActivity extends Component {
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
              const { picked, toMove, uom } = getLinePickingQuantities(dataStored.lines[lineIndex]);

              return (
                <DistributionLineButton
                  key={lineId}
                  wfProcessId={wfProcessId}
                  activityId={activityId}
                  lineId={lineId}
                  caption={lineItem.caption}
                  isUserEditable={isUserEditable}
                  completeStatus={completeStatus || CompleteStatus.NOT_STARTED}
                  qtyToMove={toMove}
                  qtyPicked={picked}
                  uom={uom}
                />
              );
            })
          : null}
      </div>
    );
  }
}

DistributionMoveActivity.propTypes = {
  wfProcessId: PropTypes.string,
  activityId: PropTypes.string,
  caption: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default DistributionMoveActivity;
