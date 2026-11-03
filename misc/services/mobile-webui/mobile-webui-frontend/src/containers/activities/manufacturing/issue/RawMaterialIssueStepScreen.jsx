import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import {
  manufacturingLineScreenLocation,
  manufacturingStepScanScreenLocation,
} from '../../../../routes/manufacturing_issue';
import * as CompleteStatus from '../../../../constants/CompleteStatus';
import { getLineById, getStepByIdFromLine } from '../../../../reducers/wfProcesses';
import { updateHeaderEntry } from '../../../../actions/HeaderActions';

import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable } from '../../../../utils/qrCode/hu';
import { formatQtyToHumanReadableStr } from '../../../../utils/qtys';

import { useLineHeaderEntriesRefresh } from './RawMaterialIssueLineScreen';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';

const RawMaterialIssueStepScreen = () => {
  const { history, url, applicationId, wfProcessId, activityId, lineId, stepId } = useScreenDefinition({
    screenId: 'RawMaterialIssueStepScreen',
    back: manufacturingLineScreenLocation,
  });

  const { readOnly, locatorName, huQRCode, uom, qtyToIssue, qtyIssued, qtyRejected } = useSelector((state) => {
    const line = getLineById(state, wfProcessId, activityId, lineId);
    let step = getStepByIdFromLine(line, stepId);
    return {
      ...step,
      readOnly: line.readOnly,
    };
  });

  useLineHeaderEntriesRefresh({ applicationId, wfProcessId, activityId, lineId });

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      updateHeaderEntry(
        computeHeaderEntriesFromParams({
          url,
          locatorName,
          huQRCode,
          uom,
          qtyToIssue,
          qtyIssued,
          qtyRejected,
        })
      )
    );
  }, []);

  const onScanButtonClick = () => {
    if (readOnly) return;
    history.push(manufacturingStepScanScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }));
  };

  const isIssued = qtyIssued > 0 || qtyRejected > 0;
  const scanButtonCaption = isIssued ? toQRCodeDisplayable(huQRCode) : trl('activities.picking.scanQRCode');

  const scanButtonStatus = isIssued ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;

  return (
    <div className="section pt-3">
      {!readOnly && (
        <ButtonWithIndicator
          caption={scanButtonCaption}
          completeStatus={scanButtonStatus}
          disabled={isIssued}
          onClick={onScanButtonClick}
        />
      )}
      {/* Unpick button */}
    </div>
  );
};

const computeHeaderEntriesFromParams = ({ url, locatorName, huQRCode, uom, qtyToIssue, qtyIssued, qtyRejected }) => {
  return {
    location: url,
    caption: trl('activities.mfg.issues.step.name'),
    values: [
      {
        caption: trl('general.Locator'),
        value: locatorName,
      },
      {
        caption: 'HU ' + trl('activities.mfg.issues.qtyToIssueTarget'),
        value: formatQtyToHumanReadableStr({ qty: qtyToIssue, uom }),
      },
      {
        caption: 'HU ' + trl('activities.mfg.issues.qtyIssued'),
        value: formatQtyToHumanReadableStr({ qty: qtyIssued || 0, uom }),
      },
      {
        caption: 'HU ' + trl('activities.mfg.issues.qtyRejected'),
        value: formatQtyToHumanReadableStr({ qty: qtyRejected, uom }),
        hidden: !qtyRejected,
      },
      {
        caption: 'HU ' + trl('general.QRCode'),
        value: toQRCodeDisplayable(huQRCode),
      },
    ],
  };
};

export default RawMaterialIssueStepScreen;
