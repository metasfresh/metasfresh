import React, { useEffect } from 'react';

import { getLineById } from '../../../../reducers/wfProcesses';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { updateManufacturingReceiptTarget } from '../../../../actions/ManufacturingActions';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { trl } from '../../../../utils/translations';

const ReceiptNewHUScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { availableReceivingTargets } = useSelector((state) => {
    const line = getLineById(state, wfProcessId, activityId, lineId);
    return {
      availableReceivingTargets: line.availableReceivingTargets,
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
  const handleClick = (target) => {
    dispatch(updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target }));
    history.go(-2);
  };

  return (
    <div className="section pt-2">
      {availableReceivingTargets.values.map((target) => (
        <ButtonWithIndicator key={target.luPIItemId} caption={target.luCaption} onClick={() => handleClick(target)}>
          <div className="row is-full is-size-7">{target.tuCaption}</div>
        </ButtonWithIndicator>
      ))}
    </div>
  );
};

export default ReceiptNewHUScreen;
