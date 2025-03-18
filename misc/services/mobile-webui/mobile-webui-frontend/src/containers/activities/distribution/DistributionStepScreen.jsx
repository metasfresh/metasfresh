import React, { useState } from 'react';
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
import { postDistributionUnpickEvent } from '../../../api/distribution';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toastError } from '../../../utils/toast';
import UnpickDialog from '../picking/UnpickDialog';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';

const HIDE_UNDO_BUTTONS = true; // hide them because they are not working

const DistributionStepScreen = () => {
  const { applicationId, wfProcessId, activityId, lineId, stepId } = useMobileNavigation();

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
  const [showTargetHUScanner, setShowTargetHUScanner] = useState(false);

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

  const onUnpick = ({ unpickToTargetQRCode }) => {
    postDistributionUnpickEvent({
      wfProcessId,
      activityId,
      lineId,
      stepId,
      unpickToTargetQRCode,
    })
      .then((wfProcess) => {
        history.goBack();
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
      {showTargetHUScanner && <UnpickDialog onSubmit={onUnpick} onCloseDialog={() => setShowTargetHUScanner(false)} />}
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
        onClick={() => setShowTargetHUScanner(true)}
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
