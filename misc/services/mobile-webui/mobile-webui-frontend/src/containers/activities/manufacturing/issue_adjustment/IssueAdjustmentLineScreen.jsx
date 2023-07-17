import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { trl } from '../../../../utils/translations';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { getActivityById, getLineByIdFromActivity } from '../../../../reducers/wfProcesses';
import { issueAdjustmentScanScreenLocation } from '../../../../routes/manufacturing_issue_adjustment';

import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import { formatQtyToHumanReadableStr } from '../../../../utils/qtys';

const IssueAdjustmentLineScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const {
    productName,
    uom,
    qtyToIssue,
    qtyToIssueMin,
    qtyToIssueMax,
    qtyToIssueTolerance,
    qtyToIssueRemaining,
    qtyIssued,
  } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }));

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        values: [
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
            caption: trl('activities.mfg.issues.qtyToIssueTarget') + ' (min)',
            value: qtyToIssueMin + ' ' + uom,
            hidden: qtyToIssueMin == null,
          },
          {
            caption: trl('activities.mfg.issues.qtyToIssueTarget') + ' (max)',
            value: qtyToIssueMax + ' ' + uom,
            hidden: qtyToIssueMax == null,
          },
          { caption: trl('activities.mfg.issues.qtyToIssueRemaining'), value: qtyToIssueRemaining + ' ' + uom },
          { caption: trl('activities.mfg.issues.qtyIssued'), value: qtyIssued + ' ' + uom },
        ],
      })
    );
  }, []);

  const history = useHistory();
  const onScanHUClicked = () => {
    history.push(issueAdjustmentScanScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  return (
    <div className="section pt-2">
      <ButtonWithIndicator caption={trl('general.scanQRCode')} onClick={onScanHUClicked} />
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const rawMaterialsIssueActivityId = activity.dataStored.rawMaterialsIssueActivityId;
  const rawMaterialsIssueActivity = getActivityById(state, wfProcessId, rawMaterialsIssueActivityId);
  const line = getLineByIdFromActivity(rawMaterialsIssueActivity, lineId);

  return {
    productName: line?.productName,
    uom: line?.uom,
    qtyToIssue: line?.qtyToIssue,
    qtyToIssueTolerance: line?.qtyToIssueTolerance,
    qtyToIssueMin: line?.qtyToIssueMin,
    qtyToIssueMax: line?.qtyToIssueMax,
    qtyToIssueRemaining: line?.qtyToIssueRemaining,
    qtyIssued: line?.qtyIssued,
  };
};

export default IssueAdjustmentLineScreen;
