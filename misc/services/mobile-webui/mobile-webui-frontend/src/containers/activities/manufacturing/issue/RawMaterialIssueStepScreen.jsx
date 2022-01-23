import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import counterpart from 'counterpart';

import { manufacturingScanScreenLocation } from '../../../../routes/manufacturing_issue';
import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { getStepById } from '../../../../reducers/wfProcesses_status';
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
            caption: counterpart.translate('general.Locator'),
            value: locatorName,
          },
          {
            caption: counterpart.translate('activities.mfg.issues.qtyToIssue'),
            value: qtyToIssue + ' ' + uom,
          },
          {
            caption: counterpart.translate('activities.mfg.issues.qtyIssued'),
            value: (qtyIssued || 0) + ' ' + uom,
          },
          {
            caption: counterpart.translate('activities.mfg.issues.qtyRejected'),
            value: qtyRejected + ' ' + uom,
            hidden: !qtyRejected,
          },
          {
            caption: counterpart.translate('general.Barcode'),
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
  const scanButtonCaption = isIssued ? `${huBarcode}` : counterpart.translate('activities.picking.scanHUBarcode');

  const scanButtonStatus = isIssued ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

  return (
    <div className="pt-3 section">
      <div className="picking-step-details centered-text is-size-5">
        <div>
          <div className="mt-0">
            <ButtonWithIndicator
              caption={scanButtonCaption}
              completeStatus={scanButtonStatus}
              disabled={isIssued}
              onClick={onScanButtonClick}
            />
            {/* Unpick button */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default RawMaterialIssueStepScreen;
