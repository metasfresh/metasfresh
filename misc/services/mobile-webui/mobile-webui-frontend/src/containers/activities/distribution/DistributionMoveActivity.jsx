import React from 'react';
import PropTypes from 'prop-types';

import * as CompleteStatus from '../../../constants/CompleteStatus';

import DistributionLineButton from './DistributionLineButton';
import { getLinesArrayFromActivity } from '../../../reducers/wfProcesses';

const DistributionMoveActivity = ({ applicationId, wfProcessId, activityId, activityState }) => {
  const lines = getLinesArrayFromActivity(activityState);
  const {
    dataStored: { completeStatus, isUserEditable },
  } = activityState;

  return (
    <div className="mt-5">
      {lines.map((line) => {
        const lineId = line.lineId;
        return (
          <DistributionLineButton
            key={lineId}
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityId={activityId}
            lineId={lineId}
            caption={line.caption}
            isUserEditable={isUserEditable}
            completeStatus={completeStatus || CompleteStatus.NOT_STARTED}
            uom={line.uom}
            qtyToMove={line.qtyToMove}
            qtyPicked={line.qtyPicked}
          />
        );
      })}
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
