import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import counterpart from 'counterpart';

import { toastError } from '../../../../utils/toast';
import { updateManufacturingReceipt, updateManufacturingReceiptQty } from '../../../../actions/ManufacturingActions';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { manufacturingReceiptReceiveTargetScreen } from '../../../../routes/manufacturing_receipt';
import { getActivityById, getLineByIdFromActivity } from '../../../../reducers/wfProcesses';

import PickQuantityButton from './PickQuantityButton';
import Button from '../../../../components/buttons/Button';

const MaterialReceiptLineScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const {
    activityCaption,
    lineProps: { aggregateToLU, currentReceivingHU, productName, uom, qtyReceived, qtyToReceive },
  } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }));

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: activityCaption,
        values: [
          {
            caption: counterpart.translate('activities.mfg.ProductName'),
            value: productName,
            bold: true,
          },
        ],
      })
    );
  }, []);

  const history = useHistory();
  const handleQuantityChange = (qtyPicked) => {
    // shall not happen
    if (aggregateToLU || currentReceivingHU) {
      console.log('skip receiving qty because there is no target');
    }

    dispatch(updateManufacturingReceiptQty({ wfProcessId, activityId, lineId, qtyPicked }));

    dispatch(
      updateManufacturingReceipt({
        wfProcessId,
        activityId,
        lineId,
      })
    ).catch((axiosError) => toastError({ axiosError }));

    history.goBack();
  };

  const handleClick = () => {
    history.push(manufacturingReceiptReceiveTargetScreen({ applicationId, wfProcessId, activityId, lineId }));
  };

  const caption = counterpart.translate('activities.mfg.receipts.receiveQty');

  let allowReceivingQty = false;
  let targetCaption = counterpart.translate('activities.mfg.receipts.receiveTarget');
  if (aggregateToLU) {
    targetCaption = aggregateToLU.newLU ? aggregateToLU.newLU.caption : aggregateToLU.existingLU.huBarcode;
    allowReceivingQty = true;
  } else if (currentReceivingHU) {
    targetCaption = currentReceivingHU.huBarcode;
    allowReceivingQty = true;
  }

  return (
    <div className="section pt-2">
      <Button caption={targetCaption} onClick={handleClick} />
      <PickQuantityButton
        qtyTarget={qtyToReceive - qtyReceived}
        isDisabled={!allowReceivingQty}
        onClick={handleQuantityChange}
        uom={uom}
        caption={caption}
      />
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const lineProps = getLineByIdFromActivity(activity, lineId);

  return {
    activityCaption: activity.caption,
    lineProps,
  };
};

export default MaterialReceiptLineScreen;
