import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import counterpart from 'counterpart';

import * as CompleteStatus from '../../../constants/CompleteStatus';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { getStepById } from '../../../reducers/wfProcesses_status';
import {
  distributionStepDropToScreenLocation,
  distributionStepPickFromScreenLocation,
} from '../../../routes/distribution';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

const HIDE_UNDO_BUTTONS = true; // hide them because they are not working

const DistributionStepScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const {
    qtyToMove,
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
            caption: counterpart.translate('general.Locator'),
            value: pickFromLocator.caption,
          },
          {
            caption: counterpart.translate('activities.distribution.scanHU'),
            value: pickFromHU.barcode,
          },
          {
            caption: counterpart.translate('general.QtyToMove'),
            value: qtyToMove,
          },
          {
            caption: counterpart.translate('general.QtyPicked'),
            value: qtyPicked,
          },
          {
            caption: counterpart.translate('general.DropToLocator'),
            value: dropToLocator.caption,
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
            caption: counterpart.translate('general.DropToLocator'),
            value: dropToLocator.caption + ' (' + dropToLocator.barcode + ')',
          },
          {
            caption: counterpart.translate('general.QtyToMove'),
            value: qtyToMove,
          },
        ],
      })
    );
  };

  const pickFromHUCaption = isPickedFrom ? pickFromHU.caption : counterpart.translate('activities.distribution.scanHU');
  const pickFromHUStatus = isPickedFrom ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

  const dropToLocatorCaption = isDroppedToLocator
    ? dropToLocator.caption
    : counterpart.translate('activities.distribution.scanLocator');
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
          caption={counterpart.translate('activities.picking.unPickBtn')}
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
          caption={counterpart.translate('activities.picking.unPickBtn')}
          disabled={!isDroppedToLocator}
          onClick={() => console.warn('TODO: not implemented')} // TODO: implement
        />
      )}
    </div>
  );
};

export default DistributionStepScreen;
