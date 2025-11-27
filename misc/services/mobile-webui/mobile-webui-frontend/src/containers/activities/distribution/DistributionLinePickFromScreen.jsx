import React from 'react';
import { useDispatch } from 'react-redux';
import { postDistributionPickFrom } from '../../../api/distribution';
import { parseQRCodeString, toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDistributionLineProps, useDistributionScreenDefinition } from './DistributionLineScreen';
import { trl } from '../../../utils/translations';
import { distributionLineScreenLocation } from '../../../routes/distribution';
import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { getScannedHUQRCodeInfo } from '../../../api/picking';

const DistributionLinePickFromScreen = () => {
  const { applicationId, history, wfProcessId, activityId, lineId } = useDistributionScreenDefinition({
    screenId: 'DistributionLinePickFromScreen',
    captionKey: 'activities.distribution.scanHU',
    back: distributionLineScreenLocation,
  });

  const dispatch = useDispatch();

  const { productId, qtyToPickRemaining, uom } = useDistributionLineProps({ wfProcessId, activityId, lineId });

  const resolveScannedBarcode = async (scannedBarcode) => {
    let parsedQRCode = parseQRCodeString({
      string: scannedBarcode,
      returnFalseOnError: true,
      checkOnlyPreciseFormats: true, // consider only precise formats, all the others will be matched on backend.
    });

    if (!parsedQRCode) {
      const huInfoFromBackend = await getScannedHUQRCodeInfo({ qrCode: scannedBarcode });
      parsedQRCode = parseQRCodeString({
        string: huInfoFromBackend?.huQRCode?.code,
        returnFalseOnError: false, // fail
        checkOnlyPreciseFormats: false, // after we have checked the backend, it's fine to try matching everything
      });
    }

    if (productId != null && parsedQRCode?.productId != null && parsedQRCode.productId !== productId) {
      throw trl('activities.distribution.qrcode.differentProduct');
    }

    return { scannedBarcode: toQRCodeString(parsedQRCode) };
  };

  const onResult = ({ qty, scannedBarcode }) => {
    return postDistributionPickFrom({
      wfProcessId,
      activityId,
      lineId,
      pickFrom: {
        qrCode: toQRCodeString(scannedBarcode),
        qtyPicked: qty,
      },
    }).then((wfProcess) => {
      dispatch(updateWFProcess({ wfProcess }));
      history.goBack();
    });
  };

  return (
    <ScanHUAndGetQtyComponent
      key={`${applicationId}_${wfProcessId}_${activityId}_${lineId}_scan`}
      qtyTargetCaption={trl('general.QtyToPick')}
      qtyCaption={trl('general.Qty')}
      qtyTarget={qtyToPickRemaining}
      uom={uom}
      //
      resolveScannedBarcode={resolveScannedBarcode}
      onResult={onResult}
      onClose={() => history.goBack()}
    />
  );
};

export default DistributionLinePickFromScreen;
