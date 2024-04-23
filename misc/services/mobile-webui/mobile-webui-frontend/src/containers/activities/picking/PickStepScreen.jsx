import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import * as CompleteStatus from '../../../constants/CompleteStatus';
import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import { postStepPicked, postStepUnPicked } from '../../../api/picking';
import { pickingStepScanScreenLocation } from '../../../routes/picking';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { getStepById } from '../../../reducers/wfProcesses';
import { getPickFromForStep, getQtyToPickForStep } from '../../../utils/picking';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ConfirmButton from '../../../components/buttons/ConfirmButton';
import { toQRCodeDisplayable, toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import UnpickDialog from './UnpickDialog';

const PickStepScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId, altStepId },
  } = useRouteMatch();

  const { pickFrom, qtyToPick, uom } = useSelector(
    (state) => getPropsFromState({ state, wfProcessId, activityId, lineId, stepId, altStepId }),
    shallowEqual
  );
  const dispatch = useDispatch();

  const [showTargetHUScanner, setShowTargetHUScanner] = useState(false);

  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.picking.PickHU'),
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
      })
    );
  }, []);

  const history = useHistory();
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
        history.go(-1);
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
          caption={trl('activities.picking.unPickBtn')}
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
