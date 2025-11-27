import React from 'react';
import PropTypes from 'prop-types';

import * as CompleteStatus from '../../../constants/CompleteStatus';

import DistributionLineButton from './DistributionLineButton';
import { getLinesArrayFromActivity } from '../../../reducers/wfProcesses';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { trl } from '../../../utils/translations';
import { distributionDropAllToScreenLocation } from '../../../routes/distribution';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';

const DistributionMoveActivity = ({ applicationId, wfProcessId, activityId, activityState }) => {
  const history = useMobileNavigation();
  const lines = getLinesArrayFromActivity(activityState);
  const {
    dataStored: { isUserEditable, hasLinesInTransit },
  } = activityState;

  const onDropAllToLocator = () => {
    history.push(
      distributionDropAllToScreenLocation({
        applicationId,
        wfProcessId,
        activityId,
      })
    );
  };

  return (
    <div className="mt-5">
      {lines.map((line, lineIdx) => {
        const lineId = line.lineId;
        return (
          <DistributionLineButton
            key={lineId}
            testId={`line-${lineIdx + 1}-button`}
            applicationId={applicationId}
            wfProcessId={wfProcessId}
            activityId={activityId}
            lineId={lineId}
            caption={line.caption}
            isUserEditable={isUserEditable}
            completeStatus={line.completeStatus ?? CompleteStatus.NOT_STARTED}
            uom={line.uom}
            qtyToMove={line.qtyToMove}
            qtyPicked={line.qtyPicked}
          />
        );
      })}
      <ButtonWithIndicator
        testId="scanDropToLocator-button"
        caption={trl('activities.distribution.scanLocator')}
        disabled={!isUserEditable || !hasLinesInTransit}
        onClick={onDropAllToLocator}
      />
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
