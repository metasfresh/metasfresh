import React from 'react';
import PropTypes from 'prop-types';
import { forEach } from 'lodash';

import * as CompleteStatus from '../../../constants/CompleteStatus';

import DistributionLineButton from './DistributionLineButton';

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

const DistributionMoveActivity = ({ applicationId, wfProcessId, activityId, activityState }) => {
  const lines = activityState.componentProps.lines;
  const dataStored = activityState.dataStored;
  const { completeStatus, isUserEditable } = dataStored;

  return (
    <div className="mt-5">
      {activityState && lines.length > 0
        ? lines.map((lineItem, lineIndex) => {
            const lineId = '' + lineIndex;
            const { picked, toMove, uom } = getLinePickingQuantities(dataStored.lines[lineIndex]);

            return (
              <DistributionLineButton
                key={lineId}
                applicationId={applicationId}
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
};

DistributionMoveActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string,
  activityId: PropTypes.string,
  caption: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default DistributionMoveActivity;
