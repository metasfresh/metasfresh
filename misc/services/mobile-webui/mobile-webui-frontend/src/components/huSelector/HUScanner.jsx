import PropTypes from 'prop-types';
import BarcodeScannerComponent from '../BarcodeScannerComponent';
import { trl } from '../../utils/translations';
import SelectHUIntermediateList from '../../apps/huManager/containers/SelectHUIntermediateList';
import React, { useState } from 'react';
import { extractErrorResponseFromAxiosError, toastError } from '../../utils/toast';
import * as api from '../../apps/huManager/api';
import { isKnownQRCodeFormat } from '../../utils/qrCode/hu';

const HUScanner = ({ onResolvedBarcode, locatorQrCode, eligibleBarcode }) => {
  const [huListByDisplayableQrCode, setHuListByDisplayableQrCode] = useState([]);
  const [locatingQrCodeScannerInfo, setLocatingQrCodeScannerInfo] = useState(undefined);

  const resolveHUScannedBarcode = async ({ scannedBarcode }) => {
    if (eligibleBarcode && scannedBarcode !== eligibleBarcode) {
      throw trl('activities.picking.notEligibleHUBarcode');
    }

    if (isKnownQRCodeFormat(scannedBarcode)) {
      if (locatorQrCode) {
        return getListByLocatingAndHuQR({
          qrCode: scannedBarcode,
          upperLevelLocatingQrCode: locatorQrCode,
        });
      }

      try {
        const handlingUnitInfo = await api.getHUByQRCode(scannedBarcode);
        return { handlingUnitInfo };
      } catch (axiosError) {
        const errorResponse = extractErrorResponseFromAxiosError(axiosError);
        if (errorResponse && errorResponse.multipleHUsFound) {
          return { targetQrCode: scannedBarcode };
        } else {
          throw axiosError;
        }
      }
    }
    return api.getHUsByDisplayableQRCode(scannedBarcode).then((huList) => ({ huListByQRCode: huList }));
  };

  const resolveLocatingScannedBarcode = async ({ scannedBarcode }) => {
    if (!locatingQrCodeScannerInfo || !locatingQrCodeScannerInfo.targetQrCode) {
      return toastError({ messageKey: 'activities.huManager.missingTargetQrCode' });
    }
    return getListByLocatingAndHuQR({
      qrCode: locatingQrCodeScannerInfo.targetQrCode,
      upperLevelLocatingQrCode: scannedBarcode,
    });
  };

  const getListByLocatingAndHuQR = ({ qrCode, upperLevelLocatingQrCode }) => {
    return api
      .listHUsByQRCode({
        qrCode: qrCode,
        upperLevelLocatingQrCode: upperLevelLocatingQrCode,
      })
      .then((huList) => {
        return { huListByQRCode: huList };
      });
  };

  const onResolvedHUScannedResult = (result) => {
    console.log('onResolvedResult', { result });

    if (result.huListByQRCode) {
      setLocatingQrCodeScannerInfo(undefined);
      if (!result.huListByQRCode.length) {
        toastError({ messageKey: 'general.noHUFound' });
      } else if (result.huListByQRCode.length === 1) {
        handleHandlingUnitLoaded(result.huListByQRCode[0]);
      } else {
        setHuListByDisplayableQrCode(result.huListByQRCode);
      }
      return;
    }
    if (result.targetQrCode) {
      setLocatingQrCodeScannerInfo({ targetQrCode: result.targetQrCode });
      return;
    }

    const { handlingUnitInfo } = result;
    handleHandlingUnitLoaded(handlingUnitInfo);
  };

  const handleHandlingUnitLoaded = (handlingUnitInfo) => {
    onResolvedBarcode(handlingUnitInfo);
    setHuListByDisplayableQrCode([]);
    setLocatingQrCodeScannerInfo(undefined);
  };

  if (locatingQrCodeScannerInfo && locatingQrCodeScannerInfo.targetQrCode) {
    return (
      <BarcodeScannerComponent
        scannerPlaceholder={trl('activities.huManager.scanLuOrLocator')}
        resolveScannedBarcode={resolveLocatingScannedBarcode}
        onResolvedResult={onResolvedHUScannedResult}
        key={'locatingQrCodeScanner'}
      />
    );
  } else if (huListByDisplayableQrCode && huListByDisplayableQrCode.length) {
    return (
      <SelectHUIntermediateList
        huList={huListByDisplayableQrCode}
        onHuSelected={(hu) => handleHandlingUnitLoaded(hu)}
      />
    );
  } else {
    return (
      <BarcodeScannerComponent
        resolveScannedBarcode={resolveHUScannedBarcode}
        onResolvedResult={onResolvedHUScannedResult}
      />
    );
  }
};

HUScanner.propTypes = {
  onResolvedBarcode: PropTypes.func.isRequired,
  locatorQrCode: PropTypes.string,
  eligibleBarcode: PropTypes.string,
};

export default HUScanner;
