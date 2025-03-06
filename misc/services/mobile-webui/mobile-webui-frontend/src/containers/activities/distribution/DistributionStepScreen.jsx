import React from 'react';
import { useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import { updateHeaderEntry } from '../../../actions/HeaderActions';
import { getStepById } from '../../../reducers/wfProcesses';
import {
  distributionLineScreenLocation,
  distributionStepDropToScreenLocation,
  distributionStepPickFromScreenLocation,
} from '../../../routes/distribution';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable } from '../../../utils/qrCode/hu';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { postDistributionReversePicking } from '../../../api/distribution';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toastError } from '../../../utils/toast';

const HIDE_UNDO_BUTTONS = true; // hide them because they are not working

const DistributionStepScreen = () => {
  const {
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const {
    qtyToMove,
    uom,
    //
    // Pick From:
    pickFromLocator,
    isPickedFrom,
    pickFromHU,
    qtyPicked,
    //
    // Drop To
    dropToLocator,
    droppedToLocator: isDroppedToLocator,
  } = useSelector((state) => getStepById(state, wfProcessId, activityId, lineId, stepId));

  const dispatch = useDispatch();
  const { history } = useScreenDefinition({
    screenId: 'DistributionStepScreen',
    back: distributionLineScreenLocation,
    values: [
      {
        caption: trl('general.Locator'),
        value: pickFromLocator.caption,
      },
      {
        caption: trl('general.DropToLocator'),
        value: dropToLocator.caption,
      },
      {
        caption: trl('general.QtyToMove'),
        value: qtyToMove + ' ' + uom,
      },
      {
        caption: trl('general.QtyPicked'),
        value: qtyPicked + ' ' + uom,
      },
      {
        caption: trl('activities.distribution.scanHU'),
        value: toQRCodeDisplayable(pickFromHU.qrCode),
      },
    ],
  });

  const onScanPickFromHU = () => {
    history.push(
      distributionStepPickFromScreenLocation({
        applicationId,
        wfProcessId,
        activityId,
        lineId,
        stepId,
      })
    );
  };

  const onScanDropToLocator = () => {
    const location = distributionStepDropToScreenLocation({
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
    });
    history.push(location);
    dispatch(
      updateHeaderEntry({
        location,
        values: [
          {
            caption: trl('general.DropToLocator'),
            value: dropToLocator.caption + ' (' + dropToLocator.qrCode + ')',
          },
          {
            caption: trl('general.QtyToMove'),
            value: qtyToMove,
          },
        ],
      })
    );
  };

  const onUnpick = () => {
    postDistributionReversePicking({
      wfProcessId,
      activityId,
      lineId,
      stepId,
    })
      .then((wfProcess) => {
        history.goTo(distributionLineScreenLocation);
        dispatch(updateWFProcess({ wfProcess }));
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  const pickFromHUCaption = isPickedFrom
    ? toQRCodeDisplayable(pickFromHU.qrCode)
    : trl('activities.distribution.scanHU');
  const pickFromHUStatus = isPickedFrom ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

  const dropToLocatorCaption = isDroppedToLocator ? dropToLocator.caption : trl('activities.distribution.scanLocator');
  const dropToLocatorStatus = isDroppedToLocator ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

  return (
    <div className="section pt-3">
      <ButtonWithIndicator
        caption={pickFromHUCaption}
        completeStatus={pickFromHUStatus}
        disabled={isPickedFrom}
        onClick={onScanPickFromHU}
      />

      <ButtonWithIndicator
        testId="unpick-button"
        captionKey="activities.picking.unPickBtn"
        disabled={!(isPickedFrom && !isDroppedToLocator)}
        onClick={onUnpick}
      />

      <ButtonWithIndicator
        testId="scanDropToLocator-button"
        caption={dropToLocatorCaption}
        completeStatus={dropToLocatorStatus}
        disabled={!(isPickedFrom && !isDroppedToLocator)}
        onClick={onScanDropToLocator}
      />

      {!HIDE_UNDO_BUTTONS && (
        <ButtonWithIndicator
          caption={trl('activities.picking.unPickBtn')}
          disabled={!isDroppedToLocator}
          onClick={() => console.warn('TODO: not implemented')} // TODO: implement
        />
      )}
    </div>
  );
};

export default DistributionStepScreen;
