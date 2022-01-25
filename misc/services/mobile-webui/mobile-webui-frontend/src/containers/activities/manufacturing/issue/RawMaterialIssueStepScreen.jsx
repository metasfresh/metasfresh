import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import { manufacturingScanScreenLocation } from '../../../../routes/manufacturing_issue';
import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { getStepById } from '../../../../reducers/wfProcesses';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';

import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';

const RawMaterialIssueStepScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

  const { locatorName, huBarcode, uom, qtyToIssue, qtyIssued, qtyRejected } = useSelector((state) =>
    getStepById(state, wfProcessId, activityId, lineId, stepId)
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: 'Issue HU', // TODO trl
        values: [
          {
            caption: trl('general.Locator'),
            value: locatorName,
          },
          {
            caption: trl('activities.mfg.issues.qtyToIssue'),
            value: qtyToIssue + ' ' + uom,
          },
          {
            caption: trl('activities.mfg.issues.qtyIssued'),
            value: (qtyIssued || 0) + ' ' + uom,
          },
          {
            caption: trl('activities.mfg.issues.qtyRejected'),
            value: qtyRejected + ' ' + uom,
            hidden: !qtyRejected,
          },
          {
            caption: trl('general.Barcode'),
            value: huBarcode,
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
  const scanButtonCaption = isIssued ? `${huBarcode}` : trl('activities.picking.scanHUBarcode');

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
