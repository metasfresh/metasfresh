import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { trl } from '../../../utils/translations';
import React from 'react';
import { useCurrentPickingTargetInfo } from '../../../reducers/wfProcesses/picking/useCurrentPickTarget';
import PropTypes from 'prop-types';
import { reopenClosedLUScreenLocation, selectPickingTargetScreenLocation } from '../../../routes/picking';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';

const SelectCurrentLUTUButtons = ({ applicationId, wfProcessId, activityId, lineId, isUserEditable = true }) => {
  const history = useMobileNavigation();

  const {
    isAllowReopeningLU,
    isPickWithNewLU,
    isLUScanRequiredAndMissing,
    isAllowNewTU,
    luPickingTarget,
    tuPickingTarget,
  } = useCurrentPickingTargetInfo({
    wfProcessId,
    activityId,
    lineId,
  });

  const onReopenClosedLUClicked = () => {
    history.push(reopenClosedLUScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  const onSelectLUPickingTargetClick = () => {
    history.push(selectPickingTargetScreenLocation({ applicationId, wfProcessId, activityId, lineId, type: 'lu' }));
  };

  const onSelectTUPickingTargetClick = () => {
    history.push(selectPickingTargetScreenLocation({ applicationId, wfProcessId, activityId, lineId, type: 'tu' }));
  };

  return (
    <>
      {isAllowReopeningLU && (
        <ButtonWithIndicator
          id="reopenLU-button"
          captionKey="activities.picking.reopenLU"
          disabled={!isUserEditable}
          onClick={onReopenClosedLUClicked}
        />
      )}
      {isPickWithNewLU && (
        <ButtonWithIndicator
          id="targetLU-button"
          caption={
            luPickingTarget?.caption
              ? trl('activities.picking.pickingTarget.Current') + ': ' + luPickingTarget?.caption
              : trl('activities.picking.pickingTarget.New')
          }
          disabled={!isUserEditable}
          onClick={onSelectLUPickingTargetClick}
        />
      )}
      {isAllowNewTU && (
        <ButtonWithIndicator
          id="targetTU-button"
          caption={
            tuPickingTarget?.caption
              ? trl('activities.picking.tuPickingTarget.Current') + ': ' + tuPickingTarget?.caption
              : trl('activities.picking.tuPickingTarget.New')
          }
          disabled={!isUserEditable || isLUScanRequiredAndMissing}
          onClick={onSelectTUPickingTargetClick}
        />
      )}
    </>
  );
};
SelectCurrentLUTUButtons.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string,
  isUserEditable: PropTypes.bool,
};
export default SelectCurrentLUTUButtons;
