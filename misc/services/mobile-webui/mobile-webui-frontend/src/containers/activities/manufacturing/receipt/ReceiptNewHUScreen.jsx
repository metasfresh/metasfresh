import React, { useEffect } from 'react';

import { getLineById } from '../../../../reducers/wfProcesses';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import { useHistory, useRouteMatch } from 'react-router-dom';
import {
  updateManufacturingLUReceiptTarget,
  updateManufacturingTUReceiptTarget,
} from '../../../../actions/ManufacturingActions';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { trl } from '../../../../utils/translations';

const ReceiptNewHUScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { availableReceivingTargets, availableReceivingTUTargets } = useSelector((state) => {
    const line = getLineById(state, wfProcessId, activityId, lineId);
    return {
      availableReceivingTargets: line.availableReceivingTargets,
      availableReceivingTUTargets: line.availableReceivingTUTargets,
    };
  }, shallowEqual);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.mfg.receipts.newHU'),
      })
    );
  }, []);

  const history = useHistory();
  const handleLUTargetClick = (target) => {
    submitSelection(updateManufacturingLUReceiptTarget, target);
  };

  const handleTUTargetClick = (target) => {
    submitSelection(updateManufacturingTUReceiptTarget, target);
  };

  const submitSelection = (submitFunction, target) => {
    dispatch(submitFunction({ wfProcessId, activityId, lineId, target }));
    history.go(-2);
  };

  return (
    <div className="section pt-2">
      {availableReceivingTargets.values.map((target) => (
        <ButtonWithIndicator
          key={target.luPIItemId}
          caption={target.luCaption}
          onClick={() => handleLUTargetClick(target)}
        >
          <div className="row is-full is-size-7">{target.tuCaption}</div>
        </ButtonWithIndicator>
      ))}
      {availableReceivingTUTargets?.values?.length > 0 && <br />}
      {availableReceivingTUTargets?.values?.map((tuTarget) => (
        <ButtonWithIndicator
          key={tuTarget.tuPIItemProductId}
          caption={tuTarget.caption}
          onClick={() => handleTUTargetClick(tuTarget)}
        />
      ))}
    </div>
  );
};

export default ReceiptNewHUScreen;
