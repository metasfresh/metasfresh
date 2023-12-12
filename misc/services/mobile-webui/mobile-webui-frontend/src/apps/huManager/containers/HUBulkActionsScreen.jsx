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

  const onScannedBarCodeResolved = ({ scannedBarcode }) => {
    setScannedHandlingUnitQRCodes([...scannedHandlingUnitQRCodes, scannedBarcode]);
    setShowHUScanner(false);
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
      .finally(() => setShowTargetScanner(false));
  };

  return (
    <ul>
      {scannedHandlingUnitQRCodes.map((handlingUnitQRCode, index) => (
        <li key={index}>
          <ButtonWithIndicator
            caption={toQRCodeDisplayable(handlingUnitQRCode)}
            onClick={() => removeHUQrCode(handlingUnitQRCode)}
          />
        </li>
      ))}
      <li>
        <br />
      </li>
      <li>
        <ButtonWithIndicator
          caption={trl('huManager.action.bulkActions.scan')}
          onClick={() => setShowHUScanner(true)}
        />
      </li>
      {showHUScanner && (
        <li>
          <BarcodeScannerComponent onResolvedResult={onScannedBarCodeResolved} />
        </li>
      )}
      {!showHUScanner && (
        <>
          <li>
            <ButtonWithIndicator
              caption={trl('huManager.action.bulkActions.move')}
              onClick={() => setShowTargetScanner(true)}
            />
          </li>
          {showTargetScanner && (
            <li>
              <BarcodeScannerComponent onResolvedResult={onTargetLocationScanned} />
            </li>
          )}
        </>
      )}
    </ul>
  );
};

export default HUBulkActionsScreen;
