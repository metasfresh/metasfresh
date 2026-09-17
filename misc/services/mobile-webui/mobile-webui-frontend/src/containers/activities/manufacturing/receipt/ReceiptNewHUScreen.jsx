import React from 'react';

import { getLineById } from '../../../../reducers/wfProcesses';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import {
  updateManufacturingLUReceiptTarget,
  updateManufacturingTUReceiptTarget,
} from '../../../../actions/ManufacturingActions';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';
import {
  manufacturingReceiptReceiveTargetScreen,
  manufacturingReceiptScreenLocation,
} from '../../../../routes/manufacturing_receipt';

const ReceiptNewHUScreen = () => {
  const { history, wfProcessId, activityId, lineId } = useScreenDefinition({
    screenId: 'ReceiptNewHUScreen',
    captionKey: 'activities.mfg.receipts.newHU',
    back: manufacturingReceiptReceiveTargetScreen,
  });

  const { availableReceivingTargets, availableReceivingTUTargets } = useSelector((state) => {
    const line = getLineById(state, wfProcessId, activityId, lineId);
    return {
      availableReceivingTargets: line.availableReceivingTargets,
      availableReceivingTUTargets: line.availableReceivingTUTargets,
    };
  }, shallowEqual);

  const dispatch = useDispatch();

  const handleLUTargetClick = (target) => {
    submitSelection(updateManufacturingLUReceiptTarget, target);
  };

  const handleTUTargetClick = (target) => {
    submitSelection(updateManufacturingTUReceiptTarget, target);
  };

  const submitSelection = (submitFunction, target) => {
    dispatch(submitFunction({ wfProcessId, activityId, lineId, target }));
    history.goTo(manufacturingReceiptScreenLocation);
  };

  const luTargets = availableReceivingTargets?.values ?? [];
  const tuTargets = availableReceivingTUTargets?.values ?? [];
  const hasTargets = luTargets.length > 0 || tuTargets.length > 0;
  // When no receiving Gebinde can be offered, the backend supplies a localized, actionable
  // reason on either target list. Show it instead of an empty screen.
  const emptyReason = availableReceivingTargets?.emptyReason || availableReceivingTUTargets?.emptyReason;

  return (
    <div className="section pt-2">
      {!hasTargets && emptyReason && (
        <div className="notification is-warning" data-testid="receive-no-gebinde-guidance">
          {emptyReason}
        </div>
      )}
      {luTargets.map((target) => (
        <ButtonWithIndicator
          key={target.luPIItemId}
          caption={target.luCaption}
          onClick={() => handleLUTargetClick(target)}
          testId={target.testId}
        >
          <div className="row is-full is-size-7">{target.tuCaption}</div>
        </ButtonWithIndicator>
      ))}
      {tuTargets.length > 0 && <br />}
      {tuTargets.map((tuTarget) => (
        <ButtonWithIndicator
          key={tuTarget.tuPIItemProductId}
          caption={tuTarget.caption}
          onClick={() => handleTUTargetClick(tuTarget)}
          testId={tuTarget.testId}
        />
      ))}
    </div>
  );
};

export default ReceiptNewHUScreen;
