import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { getStepById } from '../../../reducers/wfProcesses';
import {
  distributionStepDropToScreenLocation,
  distributionStepPickFromScreenLocation,
} from '../../../routes/distribution';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable } from '../../../utils/qrCode/hu';

const HIDE_UNDO_BUTTONS = true; // hide them because they are not working

const DistributionStepScreen = () => {
  const {
    url,
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
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
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
      })
    );
  }, []);

  const history = useHistory();
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
      pushHeaderEntry({
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

      {!HIDE_UNDO_BUTTONS && (
        <ButtonWithIndicator
          caption={trl('activities.picking.unPickBtn')}
          disabled={!(isPickedFrom && !isDroppedToLocator)}
          onClick={() => console.warn('TODO: not implemented')} // TODO: implement
        />
      )}

      <ButtonWithIndicator
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
