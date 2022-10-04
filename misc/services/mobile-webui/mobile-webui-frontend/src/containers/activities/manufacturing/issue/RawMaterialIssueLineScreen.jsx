import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { getActivityById, getLineByIdFromActivity, getStepsArrayFromLine } from '../../../../reducers/wfProcesses';

import {
  manufacturingLineScreenLocation,
  manufacturingStepScreenLocation,
} from '../../../../routes/manufacturing_issue';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/buttons/ButtonQuantityProp';
import { toQRCodeDisplayable } from '../../../../utils/huQRCodes';
import { formatQtyToHumanReadable } from '../../../../utils/qtys';

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
      pushHeaderEntry(
        computeHeaderEntriesFromParams({
          url,
          caption,
          productName,
          uom,
          qtyToIssue,
          qtyToIssueTolerancePerc,
          qtyToIssueRemaining,
          qtyIssued,
        })
      )
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

const computeHeaderEntriesFromParams = ({
  url,
  caption,
  productName,
  uom,
  qtyToIssue,
  qtyToIssueTolerancePerc,
  qtyToIssueRemaining,
  qtyIssued,
}) => {
  return {
    location: url,
    caption: caption,
    values: [
      { caption: trl('general.Product'), value: productName },
      {
        caption: trl('activities.mfg.issues.qtyToIssueTarget'),
        value: formatQtyToHumanReadable({
          qty: qtyToIssue,
          uom,
          tolerancePercent: qtyToIssueTolerancePerc,
          precision: 999,
        }),
      },
      {
        caption: trl('activities.mfg.issues.qtyToIssueRemaining'),
        value: formatQtyToHumanReadable({
          qty: qtyToIssueRemaining,
          uom,
          precision: 999,
        }),
      },
      {
        caption: trl('activities.mfg.issues.qtyIssued'),
        value: formatQtyToHumanReadable({ qty: qtyIssued, uom, precision: 999 }),
      },
    ],
  };
};

export const useLineHeaderEntriesRefresh = ({ applicationId, wfProcessId, activityId, lineId }) => {
  const props = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }), shallowEqual);

  const headerEntries = computeHeaderEntriesFromParams({
    url: manufacturingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }),
    ...props,
  });

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(pushHeaderEntry(headerEntries));
  }, []);
};

export default RawMaterialIssueLineScreen;
