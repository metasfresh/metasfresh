import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { trl } from '../../../../utils/translations';

import { toastError } from '../../../../utils/toast';
import { updateManufacturingReceiptQty } from '../../../../actions/ManufacturingActions';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { manufacturingReceiptReceiveTargetScreen } from '../../../../routes/manufacturing_receipt';
import { getActivityById, getLineByIdFromActivity } from '../../../../reducers/wfProcesses';

import PickQuantityButton from './PickQuantityButton';
import { toQRCodeDisplayable } from '../../../../utils/qrCode/hu';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';

const MaterialReceiptLineScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const {
    activityCaption,
    userInstructions,
    lineProps: { aggregateToLU, currentReceivingHU, productName, uom, qtyReceived, qtyToReceive },
  } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }));

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: activityCaption,
        userInstructions,
        values: [
          {
            caption: trl('activities.mfg.ProductName'),
            value: productName,
            bold: true,
          },
          {
            caption: trl('activities.mfg.receipts.qtyToReceiveTarget'),
            value: `${qtyToReceive} ${uom}`,
            bold: true,
          },
          {
            caption: trl('activities.mfg.receipts.qtyReceived'),
            value: `${qtyReceived} ${uom}`,
            bold: true,
          },
          {
            caption: trl('activities.mfg.receipts.qtyToReceive'),
            value: `${Math.max(qtyToReceive - qtyReceived, 0)} ${uom}`,
            bold: true,
          },
        ],
      })
    );
  }, []);

  const history = useHistory();
  const handleQuantityChange = (qtyReceived) => {
    // shall not happen
    if (aggregateToLU || currentReceivingHU) {
      console.log('skip receiving qty because there is no target');
    }

    dispatch(updateManufacturingReceiptQty({ wfProcessId, activityId, lineId, qtyReceived }))
      .then(() => history.goBack())
      .catch((axiosError) => toastError({ axiosError }));
  };

  const handleClick = () => {
    history.push(manufacturingReceiptReceiveTargetScreen({ applicationId, wfProcessId, activityId, lineId }));
  };

  let allowReceivingQty = false;
  let btnReceiveTargetCaption = trl('activities.mfg.receipts.btnReceiveTarget');
  let btnReceiveTargetCaption2 = '';
  if (aggregateToLU) {
    if (aggregateToLU.newLU) {
      btnReceiveTargetCaption = aggregateToLU.newLU.luCaption;
      btnReceiveTargetCaption2 = aggregateToLU.newLU.tuCaption;
      allowReceivingQty = true;
    } else if (aggregateToLU.existingLU) {
      btnReceiveTargetCaption = toQRCodeDisplayable(aggregateToLU.existingLU.huQRCode);
      allowReceivingQty = true;
    } else {
      console.warn('Unhandled aggregateToLU', aggregateToLU);
      allowReceivingQty = false;
    }
  } else if (currentReceivingHU) {
    btnReceiveTargetCaption = toQRCodeDisplayable(currentReceivingHU.huQRCode);
    allowReceivingQty = true;
  }

  return (
    <div className="section pt-2">
      <ButtonWithIndicator caption={btnReceiveTargetCaption} onClick={handleClick}>
        <div className="row is-full is-size-7">{btnReceiveTargetCaption2}</div>
      </ButtonWithIndicator>
      <PickQuantityButton
        qtyTarget={qtyToReceive - qtyReceived}
        isDisabled={!allowReceivingQty}
        onClick={handleQuantityChange}
        uom={uom}
        caption={trl('activities.mfg.receipts.btnReceiveProducts')}
      />
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const lineProps = getLineByIdFromActivity(activity, lineId);

  return {
    activityCaption: activity.caption,
    userInstructions: activity.userInstructions,
    lineProps,
  };
};

export default MaterialReceiptLineScreen;
