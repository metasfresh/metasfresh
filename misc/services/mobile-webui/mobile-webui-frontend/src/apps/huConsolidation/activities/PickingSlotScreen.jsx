import React from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huConsolidationJobLocation } from '../routes';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { usePickingSlot } from '../actions/usePickingSlot';
import { toQRCodeDisplayable } from '../../../utils/qrCode/hu';
import { useMobileLocation } from '../../../hooks/useMobileLocation';
import { toastErrorFromObj } from '../../../utils/toast';

export const PickingSlotScreen = () => {
  const { wfProcessId, activityId, id: pickingSlotId } = useMobileLocation();
  const { processing, pickingSlotQRCode, consolidateAll } = usePickingSlot({ wfProcessId, activityId, pickingSlotId });
  const { history } = useScreenDefinition({
    screenId: 'PickingSlotScreen',
    captionKey: 'huConsolidation.pickingSlot',
    back: huConsolidationJobLocation,
    values: [
      {
        captionKey: 'huConsolidation.pickingSlot',
        value: toQRCodeDisplayable(pickingSlotQRCode),
      },
    ],
  });

  const onConsolidateAllClicked = () => {
    consolidateAll()
      .then(() => history.goBack())
      .catch((error) => toastErrorFromObj(error));
  };

  return (
    <div className="section pt-2">
      <ButtonWithIndicator
        captionKey="huConsolidation.PickingSlotScreen.consolidateAll"
        disabled={processing}
        onClick={onConsolidateAllClicked}
        testId="consolidateAll-button"
      />
    </div>
  );
};

//
//
//--------------------------------------------------------------------------
//
//
