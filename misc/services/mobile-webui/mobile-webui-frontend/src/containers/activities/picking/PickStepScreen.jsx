import React, { useState } from 'react';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import * as CompleteStatus from '../../../constants/CompleteStatus';
import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import { postStepPicked, postStepUnPicked } from '../../../api/picking';
import { pickingLineScreenLocation, pickingStepScanScreenLocation } from '../../../routes/picking';
import { getStepById } from '../../../reducers/wfProcesses';
import { getPickFromForStep, getQtyToPickForStep } from '../../../utils/picking';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import { toQRCodeDisplayable, toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import UnpickDialog from './UnpickDialog';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';

const PickStepScreen = () => {
  const { applicationId, wfProcessId, activityId, lineId, stepId, altStepId } = useMobileNavigation();
  const { pickFrom, qtyToPick, uom } = useSelector(
    (state) => getPropsFromState({ state, wfProcessId, activityId, lineId, stepId, altStepId }),
    shallowEqual
  );

  const { history } = useScreenDefinition({
    screenId: 'PickStepScreen',
    captionKey: 'activities.picking.PickHU',
    back: pickingLineScreenLocation,
    values: [
      {
        caption: trl('general.Locator'),
        value: pickFrom?.locatorName,
      },
      {
        caption: trl('general.QtyToPick'),
        value: qtyToPick + ' ' + uom,
      },
      {
        caption: trl('general.QRCode'),
        value: toQRCodeDisplayable(pickFrom?.huQRCode),
      },
    ],
  });

  const dispatch = useDispatch();

  const [showTargetHUScanner, setShowTargetHUScanner] = useState(false);

  const unpick = ({ unpickToTargetQRCode }) => {
    postStepUnPicked({
      wfProcessId,
      activityId,
      lineId,
      stepId,
      huQRCode: toQRCodeString(pickFrom.huQRCode),
      unpickToTargetQRCode: toQRCodeString(unpickToTargetQRCode),
    })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        history.goBack();
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  const handleNotFound = () => {
    const qtyRejected = qtyToPick;

    postStepPicked({
      wfProcessId,
      activityId,
      lineId,
      stepId,
      qtyPicked: 0,
      qtyRejected,
      qtyRejectedReasonCode: 'N',
      huQRCode: toQRCodeString(pickFrom.huQRCode),
    }).then((wfProcess) => dispatch(updateWFProcess({ wfProcess })));
  };

  const onScanButtonClick = () =>
    history.push(
      pickingStepScanScreenLocation({
        applicationId,
        wfProcessId,
        activityId,
        lineId,
        stepId,
        altStepId,
      })
    );

  const isPickedFromHU = pickFrom?.qtyPicked > 0;

  const scanButtonCaption = isPickedFromHU
    ? `${toQRCodeDisplayable(pickFrom.huQRCode)}`
    : trl('activities.picking.scanQRCode');

  const scanButtonStatus = isPickedFromHU ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
  const nothingPicked = !isPickedFromHU && !pickFrom?.qtyRejectedReasonCode;

  return (
    <div className="section pt-2">
      {showTargetHUScanner && <UnpickDialog onSubmit={unpick} onCloseDialog={() => setShowTargetHUScanner(false)} />}
      <div className="buttons">
        <ButtonWithIndicator
          caption={scanButtonCaption}
          completeStatus={scanButtonStatus}
          disabled={isPickedFromHU}
          onClick={onScanButtonClick}
        />
        <ButtonWithIndicator
          id="unpick-button"
          captionKey="activities.picking.unPickBtn"
          disabled={nothingPicked}
          onClick={() => setShowTargetHUScanner(true)}
        />
        {nothingPicked && (
          <ConfirmButton
            caption={trl('activities.confirmButton.notFound')}
            isDangerousAction={true}
            isUserEditable={nothingPicked}
            onUserConfirmed={handleNotFound}
          />
        )}
      </div>
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId, altStepId }) => {
  const stepProps = getStepById(state, wfProcessId, activityId, lineId, stepId);
  return {
    pickFrom: stepProps != null ? getPickFromForStep({ stepProps, altStepId }) : null,
    qtyToPick: stepProps != null ? getQtyToPickForStep({ stepProps, altStepId }) : 0,
    uom: stepProps?.uom ?? '',
  };
};

export default PickStepScreen;
