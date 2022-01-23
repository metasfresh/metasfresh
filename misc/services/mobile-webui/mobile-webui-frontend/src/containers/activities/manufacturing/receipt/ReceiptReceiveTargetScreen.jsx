import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import counterpart from 'counterpart';

import {
  manufacturingReceiptScanScreenLocation,
  manufacturingReceiptNewHUScreen,
} from '../../../../routes/manufacturing_receipt';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';

import Button from '../../../../components/buttons/Button';

const ReceiptReceiveTargetScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: 'Target HU', // TODO trl
      })
    );
  }, []);

  const history = useHistory();
  const handleNewHUClick = () => {
    history.push(manufacturingReceiptNewHUScreen({ applicationId, wfProcessId, activityId, lineId }));
  };

  const handleScanClick = () => {
    history.push(manufacturingReceiptScanScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="pt-2 section">
      <Button caption={counterpart.translate('activities.mfg.receipts.newHU')} onClick={handleNewHUClick} />
      <Button caption={counterpart.translate('activities.mfg.receipts.existingLU')} onClick={handleScanClick} />
    </div>
  );
};

export default ReceiptReceiveTargetScreen;
