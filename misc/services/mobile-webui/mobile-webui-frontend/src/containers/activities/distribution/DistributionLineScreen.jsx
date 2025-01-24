import React, { useEffect } from 'react';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { getLineById, getStepsArrayFromLine } from '../../../reducers/wfProcesses';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import DistributionStepButton from './DistributionStepButton';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { distributionLinePickFromScreenLocation } from '../../../routes/distribution';

const DistributionLineScreen = () => {
  const {
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { steps, allowPickingAnyHU } = useDistributionLineProps({
    wfProcessId,
    activityId,
    lineId,
  });

  useHeaderUpdate();

  const history = useHistory();
  const onScanButtonClick = () => {
    history.push(distributionLinePickFromScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="section pt-2">
      <div className="buttons">
        {allowPickingAnyHU && <ButtonWithIndicator caption={trl('general.scanQRCode')} onClick={onScanButtonClick} />}
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            return (
              <DistributionStepButton
                key={idx}
                applicationId={applicationId}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.id}
                productName={stepItem.productName}
                pickFromHU={stepItem.pickFromHU}
                uom={stepItem.uom}
                qtyPicked={stepItem.qtyPicked}
                qtyToMove={stepItem.qtyToMove}
                completeStatus={stepItem.completeStatus}
              />
            );
          })}
      </div>
    </div>
  );
};

//
//
//
//
//

export const useDistributionLineProps = ({ wfProcessId, activityId, lineId }) => {
  return useSelector((state) => {
    const line = getLineById(state, wfProcessId, activityId, lineId);
    return {
      ...line,
      steps: getStepsArrayFromLine(line),
    };
  }, shallowEqual);
};

//
//
//
//
//

export const useHeaderUpdate = ({ captionKey } = {}) => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { productName, uom, qtyToMove } = useDistributionLineProps({ wfProcessId, activityId, lineId });

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: captionKey ? trl(captionKey) : null,
        values: [
          {
            caption: trl('general.Product'),
            value: productName,
            bold: true,
          },
          {
            caption: trl('general.QtyToMove'),
            value: formatQtyToHumanReadableStr({ qty: qtyToMove, uom }),
            bold: true,
          },
        ],
      })
    );
  }, []);
};

//
//
//
//
//

export default DistributionLineScreen;
