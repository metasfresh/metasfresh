import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { isUserEditable as isUserEditableFunc } from '../reducers';
import { trl } from '../../../utils/translations';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { selectTargetScreenLocation } from '../routes';
import { useWFActivity } from '../../../reducers/wfProcesses';
import { useCurrentTarget } from '../actions/useCurrentTarget';
import { toQRCodeDisplayableNoFail } from '../../../utils/qrCode/hu';
import { usePickingSlots } from '../actions/usePickingSlots';

const ConsolidateActivity = ({ applicationId, wfProcessId, activityId }) => {
  const history = useMobileNavigation();
  const { currentTarget } = useCurrentTarget({ wfProcessId, activityId });
  const activity = useWFActivity({ wfProcessId, activityId });
  const isUserEditable = isUserEditableFunc({ activity });

  const onSelectTargetClicked = () => {
    history.push(selectTargetScreenLocation({ applicationId, wfProcessId, activityId }));
  };

  return (
    <>
      <ButtonWithIndicator
        testId="targetLU-button"
        caption={
          currentTarget?.caption
            ? trl('activities.picking.pickingTarget.Current') + ': ' + currentTarget?.caption
            : trl('activities.picking.pickingTarget.New')
        }
        disabled={!isUserEditable}
        onClick={onSelectTargetClicked}
      />
      <PickingSlots wfProcessId={wfProcessId} activityId={activityId} />
    </>
  );
};

ConsolidateActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
};

export default ConsolidateActivity;

//
//
//--------------------------------------------------------------------------
//
//

const PickingSlots = ({ wfProcessId, activityId }) => {
  const { pickingSlots, consolidateFromPickingSlot } = usePickingSlots({ wfProcessId, activityId });

  return (
    <>
      {pickingSlots.map(({ pickingSlotId, pickingSlotQRCode }) => (
        <ButtonWithIndicator
          key={pickingSlotId}
          caption={toQRCodeDisplayableNoFail(pickingSlotQRCode)}
          onClick={() => consolidateFromPickingSlot({ fromPickingSlotQRCode: pickingSlotQRCode })}
        />
      ))}
    </>
  );
};
PickingSlots.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
};
