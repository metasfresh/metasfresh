import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { toQRCodeDisplayable } from '../../../utils/qrCode/hu';
import * as api from '../api';
import { toastError, toastNotification } from '../../../utils/toast';

const HUBulkActionsScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  const [scannedHandlingUnitQRCodes, setScannedHandlingUnitQRCodes] = useState([handlingUnitInfo.qrCode.code]);
  const [showHUScanner, setShowHUScanner] = useState(false);
  const [showTargetScanner, setShowTargetScanner] = useState(false);

  const { url } = useRouteMatch();
  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }

    dispatch(pushHeaderEntry({ location: url, caption: trl('huManager.action.bulkActions.windowName') }));
  }, []);

  const onScannedHUResolved = ({ scannedBarcode }) => {
    const huWasAlreadyScanned = scannedHandlingUnitQRCodes.includes(scannedBarcode);
    if (!huWasAlreadyScanned) {
      setScannedHandlingUnitQRCodes([...scannedHandlingUnitQRCodes, scannedBarcode]);
    }
    toggleHUScanner(false);
  };

  const removeHUQrCode = (huQRCodeToRemove) => {
    const updatedScannedQRCodes = scannedHandlingUnitQRCodes.filter((qrCode) => qrCode !== huQRCodeToRemove);
    setScannedHandlingUnitQRCodes(updatedScannedQRCodes);
  };

  const onTargetLocationScanned = ({ scannedBarcode }) => {
    api
      .moveBulkHUs({
        huQRCodes: scannedHandlingUnitQRCodes,
        targetQRCode: scannedBarcode,
      })
      .then(() => toastNotification({ messageKey: 'huManager.action.bulkActions.moveSuccess' }))
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => toggleTargetScanner(false));
  };

  const toggleHUScanner = (isDisplayed) => {
    setShowHUScanner(isDisplayed);
    setShowTargetScanner(false);
  };

  const toggleTargetScanner = (isDisplayed) => {
    setShowHUScanner(false);
    setShowTargetScanner(isDisplayed);
  };

  return (
    <div className="pt-3 section">
      {scannedHandlingUnitQRCodes.map((handlingUnitQRCode, index) => (
        <ButtonWithIndicator
          key={index}
          caption={toQRCodeDisplayable(handlingUnitQRCode)}
          onClick={() => removeHUQrCode(handlingUnitQRCode)}
        />
      ))}
      <ButtonWithIndicator
        caption={showHUScanner ? trl('huManager.action.bulkActions.closeScanner') : '+'}
        onClick={() => toggleHUScanner(!showHUScanner)}
      />
      {showHUScanner && (
        <BarcodeScannerComponent
          onResolvedResult={onScannedHUResolved}
          scannerPlaceholder={'huManager.action.bulkActions.scanHUPlaceholder'}
        />
      )}
      <br />
      {!showHUScanner && (
        <>
          <ButtonWithIndicator
            caption={
              showTargetScanner
                ? trl('huManager.action.bulkActions.closeScanner')
                : trl('huManager.action.bulkActions.move')
            }
            onClick={() => toggleTargetScanner(!showTargetScanner)}
          />
          {showTargetScanner && (
            <BarcodeScannerComponent
              onResolvedResult={onTargetLocationScanned}
              inputPlaceholderText={'huManager.action.bulkActions.scanTargetPlaceholder'}
            />
          )}
        </>
      )}
    </div>
  );
};

export default HUBulkActionsScreen;
