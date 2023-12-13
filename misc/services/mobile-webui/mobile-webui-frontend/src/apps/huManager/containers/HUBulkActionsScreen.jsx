import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { toQRCodeDisplayable } from '../../../utils/huQRCodes';
import * as api from '../api';
import { toastError } from '../../../utils/toast';

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
    setScannedHandlingUnitQRCodes([...scannedHandlingUnitQRCodes, scannedBarcode]);
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
      <br />
      <ButtonWithIndicator
        caption={
          showHUScanner ? trl('huManager.action.bulkActions.closeScanner') : trl('huManager.action.bulkActions.scan')
        }
        onClick={() => toggleHUScanner(!showHUScanner)}
      />
      {showHUScanner && <BarcodeScannerComponent onResolvedResult={onScannedHUResolved} />}
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
          {showTargetScanner && <BarcodeScannerComponent onResolvedResult={onTargetLocationScanned} />}
        </>
      )}
    </div>
  );
};

export default HUBulkActionsScreen;
