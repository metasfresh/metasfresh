import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { isUserEditable as isUserEditableFunc } from '../reducers';
import { trl } from '../../../utils/translations';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { pickingSlotScreenLocation, selectTargetScreenLocation } from '../routes';
import { useWFActivity } from '../../../reducers/wfProcesses';
import { useCurrentTarget } from '../actions/useCurrentTarget';
import { toQRCodeDisplayableNoFail } from '../../../utils/qrCode/hu';
import { usePickingSlots } from '../actions/usePickingSlots';

const HUConsolidationActivity = ({ applicationId, wfProcessId, activityId }) => {
  const history = useMobileNavigation();
  const { currentTarget } = useCurrentTarget({ wfProcessId, activityId });
  const activity = useWFActivity({ wfProcessId, activityId });

  const isUserEditable = isUserEditableFunc({ activity });
  const isCurrentTargetSet = !!currentTarget;

  const onSelectTargetClicked = () => {
    history.push(selectTargetScreenLocation({ applicationId, wfProcessId, activityId }));
  };

  return (
    <>
      <ButtonWithIndicator
        testId="targetLU-button"
        caption={
          isCurrentTargetSet
            ? trl('activities.picking.pickingTarget.Current') + ': ' + currentTarget?.caption
            : trl('activities.picking.pickingTarget.New')
        }
        disabled={!isUserEditable}
        onClick={onSelectTargetClicked}
      />
      <PickingSlots
        applicationId={applicationId}
        wfProcessId={wfProcessId}
        activityId={activityId}
        disabled={!isUserEditable || !isCurrentTargetSet}
      />
    </>
  );
};

HUConsolidationActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
};

export default HUConsolidationActivity;

//
//
//--------------------------------------------------------------------------
//
//

const PickingSlots = ({ applicationId, wfProcessId, activityId, disabled }) => {
  const history = useMobileNavigation();
  const { pickingSlots } = usePickingSlots({ wfProcessId, activityId });

  return (
    <div className="mt-5">
      {pickingSlots.map(({ pickingSlotId, pickingSlotQRCode }) => (
        <ButtonWithIndicator
          key={pickingSlotId}
          caption={toQRCodeDisplayableNoFail(pickingSlotQRCode)}
          disabled={disabled}
          onClick={() =>
            history.push(pickingSlotScreenLocation({ applicationId, wfProcessId, activityId, pickingSlotId }))
          }
          data-pickingslotid={pickingSlotId}
        />
      ))}
    </div>
  );
};
PickingSlots.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  disabled: PropTypes.bool.isRequired,
};
