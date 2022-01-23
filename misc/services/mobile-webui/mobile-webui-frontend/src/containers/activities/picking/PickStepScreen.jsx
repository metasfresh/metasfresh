import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import counterpart from 'counterpart';

import * as CompleteStatus from '../../../constants/CompleteStatus';
import { toastError } from '../../../utils/toast';
import { postStepPicked, postStepUnPicked } from '../../../api/picking';
import { pickingLineScreenLocation, pickingStepScanScreenLocation } from '../../../routes/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { getStepById } from '../../../reducers/wfProcesses_status';
import { getPickFrom, getQtyToPick } from '../../../utils/picking';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ConfirmButton from '../../../components/buttons/ConfirmButton';

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
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: 'Pick HU', // TODO trl
        values: [
          {
            caption: counterpart.translate('general.Locator'),
            value: pickFrom.locatorName,
          },
          {
            caption: counterpart.translate('general.QtyToPick'),
            value: qtyToPick + ' ' + uom,
          },
          {
            caption: counterpart.translate('general.Barcode'),
            value: pickFrom.huBarcode,
          },
        ],
      })
    );
  }, []);

  const history = useHistory();
  const onUnpickButtonClick = () => {
    const location = pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId });

    postStepUnPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: pickFrom.huBarcode,
    })
      .then(() => {
        dispatch(
          updatePickingStepQty({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            qtyPicked: 0,
            qtyRejected: 0,
            qtyRejectedReasonCode: null,
          })
        );
        history.push(location);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  const handleNotFound = () => {
    const qtyRejected = qtyToPick;

    postStepPicked({
      wfProcessId,
      activityId,
      stepId,
      qtyPicked: 0,
      qtyRejected,
      qtyRejectedReasonCode: 'N',
      huBarcode: pickFrom.huBarcode,
    }).then(() => {
      dispatch(
        updatePickingStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          altStepId,
          qtyPicked: 0,
          qtyRejected,
          qtyRejectedReasonCode: 'N', // FIXME: hardcoded NotFound reason code
        })
      );
    });
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

  const isPickedFromHU = pickFrom.qtyPicked > 0;

  const scanButtonCaption = isPickedFromHU
    ? `${pickFrom.huBarcode}`
    : counterpart.translate('activities.picking.scanHUBarcode');

  const scanButtonStatus = isPickedFromHU ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
  const nothingPicked = !isPickedFromHU && !pickFrom.qtyRejectedReasonCode;

  return (
    <div className="section pt-2">
      <div className="buttons">
        <ButtonWithIndicator
          caption={scanButtonCaption}
          completeStatus={scanButtonStatus}
          disabled={isPickedFromHU}
          onClick={onScanButtonClick}
        />
        <ButtonWithIndicator
          caption={counterpart.translate('activities.picking.unPickBtn')}
          disabled={nothingPicked}
          onClick={onUnpickButtonClick}
        />
        <ConfirmButton
          caption={counterpart.translate('activities.confirmButton.notFound')}
          isDangerousAction={true}
          isUserEditable={nothingPicked}
          onUserConfirmed={handleNotFound}
        />
      </div>
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId, stepId, altStepId }) => {
  const stepProps = getStepById(state, wfProcessId, activityId, lineId, stepId);
  return {
    pickFrom: getPickFrom({ stepProps, altStepId }),
    qtyToPick: getQtyToPick({ stepProps, altStepId }),
    uom: stepProps.uom,
  };
};

export default PickStepScreen;
