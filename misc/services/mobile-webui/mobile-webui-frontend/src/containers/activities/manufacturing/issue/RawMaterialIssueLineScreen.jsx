import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { getActivityById, getLineByIdFromActivity, getStepsArrayFromLine } from '../../../../reducers/wfProcesses';

import {
  manufacturingLineScanScreenLocation,
  manufacturingLineScreenLocation,
  manufacturingStepScreenLocation,
} from '../../../../routes/manufacturing_issue';
import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/buttons/ButtonQuantityProp';
import { toQRCodeDisplayable } from '../../../../utils/qrCode/hu';
import { formatQtyToHumanReadableStr } from '../../../../utils/qtys';

const RawMaterialIssueLineScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const {
    caption,
    userInstructions,
    productName,
    productValue,
    uom,
    qtyToIssue,
    qtyToIssueTolerance,
    qtyToIssueRemaining,
    qtyIssued,
    steps,
  } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }), shallowEqual);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry(
        computeHeaderEntriesFromParams({
          url,
          caption,
          userInstructions,
          productName,
          productValue,
          uom,
          qtyToIssue,
          qtyToIssueTolerance,
          qtyToIssueRemaining,
          qtyIssued,
        })
      )
    );
  }, []);

  const history = useHistory();
  const onScanHUClicked = () => {
    history.push(manufacturingLineScanScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };
  const onStepButtonClick = ({ stepId }) => {
    history.push(manufacturingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }));
  };

  return (
    <div className="section pt-2">
      <ButtonWithIndicator caption={trl('general.scanQRCode')} onClick={onScanHUClicked} />
      {steps.length > 0 &&
        steps.map((stepItem) => {
          return (
            <ButtonWithIndicator
              key={stepItem.id}
              caption={stepItem.locatorName + ' - ' + toQRCodeDisplayable(stepItem.huQRCode)}
              completeStatus={stepItem.completeStatus}
              onClick={() => onStepButtonClick({ stepId: stepItem.id })}
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
    userInstructions: line?.userInstructions || activity?.userInstructions,
    productName: line?.productName,
    productValue: line?.productValue,
    uom: line?.uom,
    qtyToIssue: line?.qtyToIssue,
    qtyToIssueTolerance: line?.qtyToIssueTolerance,
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
  userInstructions,
  productName,
  productValue,
  uom,
  qtyToIssue,
  qtyToIssueTolerance,
  qtyToIssueRemaining,
  qtyIssued,
}) => {
  return {
    location: url,
    caption: caption,
    userInstructions,
    values: [
      {
        caption: trl('general.ProductValue'),
        value: productValue,
      },
      { caption: trl('general.Product'), value: productName },
      {
        caption: trl('activities.mfg.issues.qtyToIssueTarget'),
        value: formatQtyToHumanReadableStr({
          qty: qtyToIssue,
          uom,
          tolerance: qtyToIssueTolerance,
          precision: 999,
        }),
      },
      {
        caption: trl('activities.mfg.issues.qtyToIssueRemaining'),
        value: formatQtyToHumanReadableStr({
          qty: qtyToIssueRemaining,
          uom,
          precision: 999,
        }),
      },
      {
        caption: trl('activities.mfg.issues.qtyIssued'),
        value: formatQtyToHumanReadableStr({ qty: qtyIssued, uom, precision: 999 }),
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
