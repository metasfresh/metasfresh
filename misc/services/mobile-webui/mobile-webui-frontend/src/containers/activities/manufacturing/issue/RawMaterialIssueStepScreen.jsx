import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { manufacturingScanScreenLocation } from '../../../../routes/manufacturing_issue';
import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { getStepById } from '../../../../reducers/wfProcesses';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';

import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable } from '../../../../utils/huQRCodes';

const RawMaterialIssueStepScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { locatorName, huQRCode, uom, qtyToIssue, qtyIssued, qtyRejected } = useSelector((state) =>
    getStepById(state, wfProcessId, activityId, lineId, stepId)
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.mfg.issues.step.name'),
        values: [
          {
            caption: trl('general.Locator'),
            value: locatorName,
          },
          {
            caption: 'HU ' + trl('activities.mfg.issues.qtyToIssueTarget'),
            value: qtyToIssue + ' ' + uom,
          },
          {
            caption: 'HU ' + trl('activities.mfg.issues.qtyIssued'),
            value: (qtyIssued || 0) + ' ' + uom,
          },
          {
            caption: 'HU ' + trl('activities.mfg.issues.qtyRejected'),
            value: qtyRejected + ' ' + uom,
            hidden: !qtyRejected,
          },
          {
            caption: 'HU ' + trl('general.QRCode'),
            value: toQRCodeDisplayable(huQRCode),
          },
        ],
      })
    );
  }, []);

  const history = useHistory();
  const onScanButtonClick = () => {
    history.push(manufacturingScanScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }));
  };

  const isIssued = qtyIssued > 0 || qtyRejected > 0;
  const scanButtonCaption = isIssued ? toQRCodeDisplayable(huQRCode) : trl('activities.picking.scanQRCode');

  const scanButtonStatus = isIssued ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

  return (
    <div className="section pt-3">
      <ButtonWithIndicator
        caption={scanButtonCaption}
        completeStatus={scanButtonStatus}
        disabled={isIssued}
        onClick={onScanButtonClick}
      />
      {/* Unpick button */}
    </div>
  );
};

export default RawMaterialIssueStepScreen;
