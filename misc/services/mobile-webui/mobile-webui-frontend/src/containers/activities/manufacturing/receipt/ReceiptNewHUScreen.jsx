import React, { useEffect } from 'react';

import { getLineById } from '../../../../reducers/wfProcesses';
import { toastError } from '../../../../utils/toast';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { updateManufacturingReceipt, updateManufacturingReceiptTarget } from '../../../../actions/ManufacturingActions';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';

const ReceiptNewHUScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { availableReceivingTargets, userQtyReceived } = useSelector((state) => {
    const line = getLineById(state, wfProcessId, activityId, lineId);
    return {
      availableReceivingTargets: line.availableReceivingTargets,
      userQtyReceived: line.userQtyReceived,
    };
  }, shallowEqual);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: 'New HU', // TODO trl
      })
    );
  }, []);

  const history = useHistory();
  const handleClick = (target) => {
    dispatch(updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target }));

    if (userQtyReceived) {
      dispatch(
        updateManufacturingReceipt({
          wfProcessId,
          activityId,
          lineId,
        })
      ).catch((axiosError) => toastError({ axiosError }));
    }

    history.go(-2);
  };

  return (
    <div className="section pt-2">
      {availableReceivingTargets.values.map((target) => (
        <ButtonWithIndicator key={target.luPIItemId} caption={target.caption} onClick={() => handleClick(target)}>
          <div className="row is-full is-size-7">{target.tuCaption}</div>
        </ButtonWithIndicator>
      ))}
    </div>
  );
};

export default ReceiptNewHUScreen;
