import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { getActivityById, getLineByIdFromActivity, getStepsArrayFromLine } from '../../../../reducers/wfProcesses';

import { manufacturingStepScreenLocation } from '../../../../routes/manufacturing_issue';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/buttons/ButtonQuantityProp';
import { toQRCodeDisplayable } from '../../../../utils/huQRCodes';

const RawMaterialIssueLineScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { caption, productName, uom, qtyToIssue, qtyToIssueTolerancePerc, qtyToIssueRemaining, qtyIssued, steps } =
    useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }), shallowEqual);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: caption,
        values: [
          { caption: trl('general.Product'), value: productName },
          {
            caption: trl('activities.mfg.issues.qtyToIssueTarget'),
            value: buildQtyWithToleranceString({ qty: qtyToIssue, uom, tolerance: qtyToIssueTolerancePerc }),
          },
          { caption: trl('activities.mfg.issues.qtyToIssueRemaining'), value: qtyToIssueRemaining + ' ' + uom },
          { caption: trl('activities.mfg.issues.qtyIssued'), value: qtyIssued + ' ' + uom },
        ],
      })
    );
  }, []);

  const history = useHistory();
  const onButtonClick = ({ stepId }) => {
    history.push(manufacturingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }));
  };

  return (
    <div className="section pt-2">
      {steps.length > 0 &&
        steps.map((stepItem) => {
          return (
            <ButtonWithIndicator
              key={stepItem.id}
              caption={stepItem.locatorName + ' - ' + toQRCodeDisplayable(stepItem.huQRCode)}
              completeStatus={stepItem.completeStatus}
              onClick={() => onButtonClick({ stepId: stepItem.id })}
            >
              <ButtonQuantityProp
                qtyCurrent={stepItem.qtyIssued ?? 0}
                qtyTarget={stepItem.qtyToIssue}
                uom={stepItem.uom}
                applicationId={applicationId}
                subtypeId="issues"
              />
            </ButtonWithIndicator>
          );
        })}
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const line = getLineByIdFromActivity(activity, lineId);
  return {
    caption: activity?.caption ?? 'Issue',
    productName: line?.productName,
    uom: line?.uom,
    qtyToIssue: line?.qtyToIssue,
    qtyToIssueTolerancePerc: line?.qtyToIssueTolerancePerc,
    // qtyToIssueMin: line?.qtyToIssueMin,
    // qtyToIssueMax: line?.qtyToIssueMax,
    qtyToIssueRemaining: line?.qtyToIssueRemaining,
    qtyIssued: line?.qtyIssued,
    steps: getStepsArrayFromLine(line),
  };
};

export const buildQtyWithToleranceString = ({ qty, uom, tolerance }) => {
  let result = qty + ' ' + uom;
  if (tolerance != null) {
    result += ' ±' + tolerance + '%';
  }
  return result;
};

export default RawMaterialIssueLineScreen;
