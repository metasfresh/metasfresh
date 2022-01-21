import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import counterpart from 'counterpart';

import {
  manufacturingReceiptScanScreenLocation,
  manufacturingReceiptNewHUScreen,
} from '../../../../routes/manufacturing_receipt';

import Button from '../../../../components/buttons/Button';

const ReceiptReceiveTargetScreen = () => {
  const {
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const history = useHistory();

  const handleNewHUClick = () => {
    history.push(manufacturingReceiptNewHUScreen({ applicationId, wfProcessId, activityId, lineId }));
  };

  const handleScanClick = () => {
    history.push(manufacturingReceiptScanScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="pt-2 section">
      <div className="buttons">
        <Button caption={counterpart.translate('activities.mfg.receipts.newHU')} onClick={handleNewHUClick} />
        <Button caption={counterpart.translate('activities.mfg.receipts.existingLU')} onClick={handleScanClick} />
      </div>
    </div>
  );
};

export default ReceiptReceiveTargetScreen;
