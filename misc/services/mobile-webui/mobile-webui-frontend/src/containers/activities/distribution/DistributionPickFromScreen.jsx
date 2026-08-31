import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { getNextEligiblePickFromLine } from '../../../api/distribution';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { ATTR_barcodeType, BARCODE_TYPE_EAN13 } from '../../../utils/qrCode/common';
import { trl } from '../../../utils/translations';
import { extractErrorCodeFromAxiosError, extractUserFriendlyErrorMessageFromAxiosError } from '../../../utils/toast';
import { distributionJobScreenLocation, distributionLineScreenLocation } from '../../../routes/distribution';
import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { resolveDistributionScannedBarcodeToParsedQRCode } from '../../../apps/distribution/services/barcodeResolverService';
import { useSearchParams } from '../../../hooks/useSearchParams';
import { useMobileLocation } from '../../../hooks/useMobileLocation';
import { getLineByIdFromActivity, useWFActivity } from '../../../reducers/wfProcesses';
import { computeQtyToPickRemaining } from '../../../reducers/wfProcesses/distribution/computeQtyToPickRemaining';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { isRequireScanningProductCode } from '../../../reducers/wfProcesses/distribution/getDistributionJobCompleteStatus';
import { postDistributionPickFromThunk } from '../../../apps/distribution/redux/postDistributionPickFromThunk';
import { useDistributionLineHeaders } from './DistributionLineScreen';

// The rejections that say the applied handling unit itself cannot serve the pick, so the operator has
// to name another one. Any other failure leaves it the best information the screen has about where to
// pick from, and reads as itself: relabelling it sends the operator after a handling unit for nothing.
const HU_REFUSAL_ERROR_CODES = new Set([
  // DistributionHUService.assertHUCanBePickedFrom
  'DISTRIBUTION_HU_RESERVED',
  'DISTRIBUTION_HU_ALREADY_AT_TARGET',
  'DISTRIBUTION_HU_NOT_AT_TARGET',
  // DistributionJobPickFromCommand.resolveHuIdToPick, keyed by AD_Message because neither message
  // carries an AD_Message.ErrorCode and AdempiereException then reports the key.
  'de.metas.distribution.workflows_api.ProductDoesNotMatch',
  'de.metas.distribution.workflows_api.NotEnoughQty',
]);

const DistributionPickFromScreen = () => {
  const {
    history,
    applicationId,
    wfProcessId,
    activityId,
    activity,
    lineId: lineIdParam,
    huQRCode: huQRCodeParam,
  } = useDistributionScreenDefinition();
  const dispatch = useDispatch();
  const [lineId, setLineId] = useState(lineIdParam);

  // The handling unit the screen was opened with — a job-screen scan, or one carried across an
  // auto-advance — until the server refuses to pick from it. The refusal belongs to the order it was
  // met on: this screen stays mounted across auto-advances, and a later order may carry the same
  // handling unit in legitimately.
  const [refusedOnWFProcessId, setRefusedOnWFProcessId] = useState(null);
  const appliedHUQRCode = refusedOnWFProcessId === wfProcessId ? undefined : huQRCodeParam;

  const resolveHUScannedCode = async (scannedBarcode) => {
    const parsedQRCode = await resolveDistributionScannedBarcodeToParsedQRCode(scannedBarcode);

    // An article code names an article, not one specific handling unit — and a source locator holds
    // many handling units of the same article, so it cannot say which one to pick from. The huQRCode
    // slot only ever accepts a code identifying a single unit (DistributionHUService.resolveHUQRCode)
    // and answers an EAN13 with 422 QR_WRONG_TYPE, so say it here instead: throwing leaves
    // ScanHUAndGetQtyComponent in STATUS_READ_HU_BARCODE, so the article code is never remembered as
    // the chosen handling unit, never reaches getNextEligiblePickFromLine in the huQRCode slot, and
    // the operator keeps their place for the next scan.
    //
    // Two deliberate limits. Only EAN13 is refused here — the one article-code format this parse
    // recognises without a round trip, since it asks for precise formats and GS1 is not one; a GS1
    // article code therefore still gets the backend's wrong-QR-type message. And a 13-digit numeric
    // M_HU.Value / ExternalBarcode carrying a valid EAN13 check digit would be read as an article
    // code here; no handling-unit barcode of that shape is in use.
    if (parsedQRCode?.[ATTR_barcodeType] === BARCODE_TYPE_EAN13) {
      throw trl('activities.distribution.qrcode.productCodeWhereHUExpected');
    }

    const { productId, qtyToPickRemaining, uom } = getLineInfo({ activity, lineId: lineIdParam });
    if (productId != null && parsedQRCode?.productId != null && parsedQRCode.productId !== productId) {
      throw trl('activities.distribution.qrcode.differentProduct');
    }

    const huScannedCode = toQRCodeString(parsedQRCode);
    const isScanProductCodeRequired = lineIdParam == null || isRequireScanningProductCode({ activity });

    // Without this cap the dialog would default to the whole outstanding line qty even when the scanned HU
    // holds less. We can only resolve the HU's qty here when no product scan is required (the line is known);
    // otherwise the qty is resolved later in resolveProductScannedCode.
    let qtyTarget = qtyToPickRemaining;
    let qtyMax;
    if (!isScanProductCodeRequired) {
      const { qtyAvailable } = await getNextEligiblePickFromLine({
        wfProcessId,
        huQRCode: huScannedCode,
        lineId: lineIdParam,
      });
      if (qtyAvailable != null) {
        qtyTarget = Math.min(qtyAvailable, qtyToPickRemaining);
        qtyMax = qtyTarget;
      }
    }

    return {
      scannedBarcode: huScannedCode,
      isScanProductCodeRequired,
      qtyTarget,
      qtyMax,
      uom,
    };
  };

  const resolveProductScannedCode = async ({ huScannedCode, productScannedCode }) => {
    const { lineId, qtyAvailable } = await getNextEligiblePickFromLine({
      wfProcessId,
      huQRCode: huScannedCode,
      productScannedCode,
      lineId: lineIdParam,
    });
    console.debug('resolveProductScannedCode', { huScannedCode, productScannedCode, lineId, lineIdParam });
    if (!lineId) {
      throw 'No line found'; // TODO trl
    }
    setLineId(lineId);

    const { qtyToPickRemaining, uom } = getLineInfo({ activity, lineId });

    if (qtyToPickRemaining === 1) {
      await onResult({ qty: 1, scannedBarcode: huScannedCode, lineId });
      return {};
    }

    // Cap the proposed move-qty to what the scanned HU actually holds: min(HU available qty, line remaining).
    const qtyTarget = qtyAvailable != null ? Math.min(qtyAvailable, qtyToPickRemaining) : qtyToPickRemaining;
    return {
      qtyTarget,
      qtyMax: qtyTarget,
      uom,
    };
  };

  const onResult = ({ qty, scannedBarcode: huScannedCode, lineId: lineIdParam }) => {
    return dispatch(
      postDistributionPickFromThunk({
        history,
        wfProcessId,
        activityId,
        lineId: lineIdParam ? lineIdParam : lineId,
        huScannedCode,
        qty,
      })
    ).catch((axiosError) => {
      // A refused handling unit the operator did not choose here is a dead end: this screen renders no
      // handling-unit input while one is applied. Forget it for this order so the prompt returns, with
      // the reason. A failure that says nothing about it — a conflict elsewhere, a server fault, a lost
      // connection, which carries no response at all — travels on untouched, and it stays applied.
      const isHURefusal = HU_REFUSAL_ERROR_CODES.has(extractErrorCodeFromAxiosError(axiosError));
      if (appliedHUQRCode == null || !isHURefusal) {
        throw axiosError;
      }

      setRefusedOnWFProcessId(wfProcessId);
      throw trl('activities.distribution.cannotPickFromSelectedHU', {
        reason: extractUserFriendlyErrorMessageFromAxiosError({ axiosError }),
      });
    });
  };

  return (
    <ScanHUAndGetQtyComponent
      key={`${applicationId}_${wfProcessId}_${activityId}_${lineIdParam ?? '-'}_scan`}
      scanHUPlaceholderText={trl('activities.distribution.scanHUBarcodePlaceholder')}
      scanProductPlaceholderText={trl('activities.distribution.scanProductGtinPlaceholder')}
      scannedBarcode={appliedHUQRCode}
      resolveScannedBarcode={resolveHUScannedCode}
      resolveProductScannedCode={resolveProductScannedCode}
      qtyTargetCaption={trl('general.QtyToPick')}
      qtyCaption={trl('general.Qty')}
      // qtyTarget={qtyToPickRemaining}
      // uom={uom}
      //
      onResult={onResult}
      onClose={() => history.goBack()}
    />
  );
};

export default DistributionPickFromScreen;

//
//
//
//
//

const getLineInfo = ({ activity, lineId }) => {
  const line = lineId ? getLineByIdFromActivity(activity, lineId) : {};
  return {
    productId: line.productId,
    productName: line.productName,
    uom: line.uom,
    qtyToMove: line.qtyToMove,
    qtyToPickRemaining: computeQtyToPickRemaining({ line }),
    pickFromLocator: line.pickFromLocator,
  };
};

const useDistributionScreenDefinition = () => {
  const { applicationId, wfProcessId, activityId } = useMobileLocation();
  const [urlParams] = useSearchParams();
  const lineId = urlParams.get('lineId');
  const huQRCode = urlParams.get('huQRCode');

  const activity = useWFActivity({ wfProcessId, activityId });
  const headers = useDistributionLineHeaders({ wfProcessId, activityId, lineId });

  const { history } = useScreenDefinition({
    screenId: 'DistributionLinePickFromScreen',
    captionKey: 'activities.distribution.scanHU',
    back: lineId
      ? distributionLineScreenLocation({ applicationId, wfProcessId, activityId, lineId })
      : distributionJobScreenLocation({ applicationId, wfProcessId, activityId }),
    values: headers,
  });

  return { history, applicationId, wfProcessId, activityId, activity, lineId, huQRCode };
};
