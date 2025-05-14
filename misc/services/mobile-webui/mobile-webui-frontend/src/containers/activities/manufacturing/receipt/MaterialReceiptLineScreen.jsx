import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { trl } from '../../../../utils/translations';

import { toastError } from '../../../../utils/toast';
import { updateManufacturingReceiptQty } from '../../../../actions/ManufacturingActions';
import { updateHeaderEntry } from '../../../../actions/HeaderActions';
import { manufacturingReceiptReceiveTargetScreen } from '../../../../routes/manufacturing_receipt';
import { getActivityByIdFromWFProcess, getLineByIdFromActivity, getWfProcess } from '../../../../reducers/wfProcesses';

import PickQuantityButton from './PickQuantityButton';
import { toQRCodeDisplayable } from '../../../../utils/qrCode/hu';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import Spinner from '../../../../components/Spinner';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../../routes/workflow_locations';
import { APPLICATION_ID_Picking } from '../../../../apps/picking';

const MaterialReceiptLineScreen = () => {
  const { history, url, applicationId, wfProcessId, activityId, lineId } = useScreenDefinition({
    screenId: 'MaterialReceiptLineScreen',
    back: getWFProcessScreenLocation,
  });

  const {
    activityCaption,
    userInstructions,
    lineProps: {
      aggregateToLU,
      aggregateToTU,
      currentReceivingHU,
      productName,
      uom,
      catchWeightUomSymbol,
      qtyReceived,
      qtyToReceive,
    },
    pickTo,
  } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }));
  const [showSpinner, setShowSpinner] = useState(false);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      updateHeaderEntry({
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

  const handleQuantityChange = ({
    qtyEnteredAndValidated,
    catchWeight,
    catchWeightUom,
    bestBeforeDate,
    lotNo,
    isDone = true,
  }) => {
    // shall not happen
    if (!aggregateToLU && !currentReceivingHU && !aggregateToTU) {
      console.log('skip receiving qty because there is no target');
      return;
    }

    setShowSpinner(true);
    dispatch(
      updateManufacturingReceiptQty({
        wfProcessId,
        activityId,
        lineId,
        qtyReceived: Number(qtyEnteredAndValidated),
        pickTo,
        catchWeight,
        catchWeightUom,
        bestBeforeDate,
        lotNo,
      })
    )
      .then(() => {
        if (isDone) {
          history.goBack();
        }
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setShowSpinner(false));
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
  } else if (aggregateToTU?.newTU) {
    btnReceiveTargetCaption = aggregateToTU.newTU?.caption;
    allowReceivingQty = true;
  } else if (currentReceivingHU) {
    btnReceiveTargetCaption = toQRCodeDisplayable(currentReceivingHU.huQRCode);
    allowReceivingQty = true;
  }

  return (
    <>
      {showSpinner && <Spinner />}
      <div className="section pt-2">
        <ButtonWithIndicator caption={btnReceiveTargetCaption} onClick={handleClick} testId="receive-target-button">
          <div className="row is-full is-size-7">{btnReceiveTargetCaption2}</div>
        </ButtonWithIndicator>
        <PickQuantityButton
          qtyTarget={qtyToReceive - qtyReceived}
          catchWeightUom={catchWeightUomSymbol}
          isDisabled={!allowReceivingQty}
          onClick={handleQuantityChange}
          uom={uom}
          caption={trl('activities.mfg.receipts.btnReceiveProducts')}
        />
      </div>
    </>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const wfProcess = getWfProcess(state, wfProcessId);
  const activity = getActivityByIdFromWFProcess(wfProcess, activityId);
  const lineProps = getLineByIdFromActivity(activity, lineId);

  return {
    activityCaption: activity.caption,
    userInstructions: activity.userInstructions,
    lineProps,
    pickTo: getPickTo({ wfProcess }),
  };
};

const getPickTo = ({ wfProcess }) => {
  const parent = wfProcess?.parent;
  if (!parent) {
    return;
  }
  if (parent.applicationId !== APPLICATION_ID_Picking) {
    return null;
  }

  return {
    wfProcessId: parent.wfProcessId,
    activityId: parent.activityId,
    lineId: parent.lineId,
  };
};

export default MaterialReceiptLineScreen;
