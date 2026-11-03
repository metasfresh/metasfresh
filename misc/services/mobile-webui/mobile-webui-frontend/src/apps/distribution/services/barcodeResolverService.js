import { parseQRCodeString } from '../../../utils/qrCode/hu';
import { getDistributionScannedHUQRCodeInfo } from '../../../api/distribution';

export const resolveDistributionScannedBarcodeToParsedQRCode = async (scannedBarcode) => {
  let parsedQRCode = parseQRCodeString({
    string: scannedBarcode,
    returnFalseOnError: true,
    checkOnlyPreciseFormats: true, // consider only precise formats, all the others will be matched on backend.
  });

  if (!parsedQRCode) {
    const huInfoFromBackend = await getDistributionScannedHUQRCodeInfo({ qrCode: scannedBarcode });
    parsedQRCode = parseQRCodeString({
      string: huInfoFromBackend?.qrCode?.code,
      returnFalseOnError: false, // fail
      checkOnlyPreciseFormats: false, // after we have checked the backend, it's fine to try matching everything
    });
  }

  return parsedQRCode;
};
