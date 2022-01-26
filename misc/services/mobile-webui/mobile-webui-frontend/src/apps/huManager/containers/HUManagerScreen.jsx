import React from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { extractUserFriendlyErrorMessageFromAxiosError } from '../../../utils/toast';
import { getHUByBarcode } from '../api';
import { clearLoadedData, handlingUnitLoaded } from '../actions';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { huManagerDisposeLocation } from '../routes';

import { HUInfoComponent } from '../components/HUInfoComponent';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

const HUManagerScreen = () => {
  const dispatch = useDispatch();

  const resolveScannedBarcode = ({ scannedBarcode }) => {
    return getHUByBarcode(scannedBarcode)
      .then((handlingUnitInfo) => ({ handlingUnitInfo }))
      .catch((axiosError) => ({
        error: extractUserFriendlyErrorMessageFromAxiosError({ axiosError }),
      }));
  };

  const onResolvedResult = (result) => {
    console.log('onResolvedResult', result);
    const { handlingUnitInfo } = result;
    dispatch(handlingUnitLoaded({ handlingUnitInfo }));
  };

  const history = useHistory();
  const onDisposeClick = () => {
    history.push(huManagerDisposeLocation());
  };

  const onScanAgainClick = () => {
    dispatch(clearLoadedData());
  };

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  if (handlingUnitInfo && handlingUnitInfo.id) {
    return (
      <>
        <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />
        <div className="pt-3 section">
          <ButtonWithIndicator caption={trl('huManager.action.dispose.buttonCaption')} onClick={onDisposeClick} />
          <ButtonWithIndicator caption={trl('huManager.action.scanAgain.buttonCaption')} onClick={onScanAgainClick} />
        </div>
      </>
    );
  } else {
    return (
      <BarcodeScannerComponent resolveScannedBarcode={resolveScannedBarcode} onResolvedResult={onResolvedResult} />
    );
  }
};

export default HUManagerScreen;
