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
    screenId: 'ReceiptReceiveTargetScreen',
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
      <Button caption={trl('activities.mfg.receipts.newHU')} onClick={handleNewHUClick} testId="new-hu-button" />
      <Button
        caption={trl('activities.mfg.receipts.existingLU')}
        onClick={handleScanClick}
        testId="existing-lu-button"
      />
    </div>
  );
};

export default ReceiptReceiveTargetScreen;
