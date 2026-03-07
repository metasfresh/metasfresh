import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { getNextEligiblePickFromLine } from '../../../api/distribution';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { trl } from '../../../utils/translations';
import { distributionJobScreenLocation, distributionLineScreenLocation } from '../../../routes/distribution';
import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { resolveDistributionScannedBarcodeToParsedQRCode } from '../../../apps/distribution/services/barcodeResolverService';
import { useSearchParams } from '../../../hooks/useSearchParams';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import { useMobileLocation } from '../../../hooks/useMobileLocation';
import { getLineByIdFromActivity, useWFActivity } from '../../../reducers/wfProcesses';
import { computeQtyToPickRemaining } from '../../../reducers/wfProcesses/distribution/computeQtyToPickRemaining';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { isRequireScanningProductCode } from '../../../reducers/wfProcesses/distribution/getDistributionJobCompleteStatus';
import { postDistributionPickFromThunk } from '../../../apps/distribution/redux/postDistributionPickFromThunk';

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

  const resolveHUScannedCode = async (scannedBarcode) => {
    const parsedQRCode = await resolveDistributionScannedBarcodeToParsedQRCode(scannedBarcode);

    const { productId, qtyToPickRemaining, uom } = getLineInfo({ activity, lineId: lineIdParam });
    if (productId != null && parsedQRCode?.productId != null && parsedQRCode.productId !== productId) {
      throw trl('activities.distribution.qrcode.differentProduct');
    }

    return {
      scannedBarcode: toQRCodeString(parsedQRCode),
      isScanProductCodeRequired: lineIdParam == null || isRequireScanningProductCode({ activity }),
      qtyTarget: qtyToPickRemaining,
      uom,
    };
  };

  const resolveProductScannedCode = async ({ huScannedCode, productScannedCode }) => {
    const { lineId } = await getNextEligiblePickFromLine({
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

    return {
      qtyTarget: qtyToPickRemaining,
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
    );
  };

  return (
    <ScanHUAndGetQtyComponent
      key={`${applicationId}_${wfProcessId}_${activityId}_${lineIdParam ?? '-'}_scan`}
      scanHUPlaceholderText={trl('activities.distribution.scanHU')}
      scanProductPlaceholderText={trl('activities.distribution.scanProduct')}
      scannedBarcode={huQRCodeParam}
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
  };
};

const useDistributionScreenDefinition = () => {
  const { applicationId, wfProcessId, activityId } = useMobileLocation();
  const [urlParams] = useSearchParams();
  const lineId = urlParams.get('lineId');
  const huQRCode = urlParams.get('huQRCode');

  const activity = useWFActivity({ wfProcessId, activityId });
  const { productName, uom, qtyToMove } = getLineInfo({ activity, lineId });

  const { history } = useScreenDefinition({
    screenId: 'DistributionLinePickFromScreen',
    captionKey: 'activities.distribution.scanHU',
    back:
      lineId != null
        ? distributionLineScreenLocation({ applicationId, wfProcessId, activityId, lineId })
        : distributionJobScreenLocation({ applicationId, wfProcessId, activityId }),
    values: [
      {
        caption: trl('general.Product'),
        value: productName,
        bold: true,
        hidden: productName == null,
      },
      {
        caption: trl('general.QtyToMove'),
        value: qtyToMove != null ? formatQtyToHumanReadableStr({ qty: qtyToMove, uom }) : null,
        bold: true,
        hidden: qtyToMove == null,
      },
    ],
  });

  return { history, applicationId, wfProcessId, activityId, activity, lineId, huQRCode };
};
