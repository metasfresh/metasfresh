import React from 'react';

import { trl } from '../../../../utils/translations';
import {
  manufacturingReceiptNewHUScreen,
  manufacturingReceiptScanScreenLocation,
  manufacturingReceiptScreenLocation,
} from '../../../../routes/manufacturing_receipt';

import Button from '../../../../components/buttons/Button';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';

const ReceiptReceiveTargetScreen = () => {
  const { history, applicationId, wfProcessId, activityId, lineId } = useScreenDefinition({
    captionKey: 'activities.mfg.receipts.btnReceiveTarget',
    back: manufacturingReceiptScreenLocation,
  });

  const handleNewHUClick = () => {
    history.push(manufacturingReceiptNewHUScreen({ applicationId, wfProcessId, activityId, lineId }));
  };

  const handleScanClick = () => {
    history.push(manufacturingReceiptScanScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="pt-2 section">
      <Button caption={trl('activities.mfg.receipts.newHU')} onClick={handleNewHUClick} />
      <Button caption={trl('activities.mfg.receipts.existingLU')} onClick={handleScanClick} />
    </div>
  );
};

export default ReceiptReceiveTargetScreen;
