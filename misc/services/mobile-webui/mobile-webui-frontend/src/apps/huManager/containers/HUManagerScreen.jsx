import React from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import { getHUByBarcode } from '../api';
import { clearLoadedData, handlingUnitLoaded } from '../actions';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { huManagerDisposeLocation } from '../routes';

import { HUInfoComponent } from '../components/HUInfoComponent';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

const HUManagerScreen = () => {
  const dispatch = useDispatch();

  const onHUBarcodeScanned = ({ scannedBarcode }) => {
    getHUByBarcode(scannedBarcode)
      .then((handlingUnitInfo) => {
        dispatch(handlingUnitLoaded({ handlingUnitInfo }));
      })
      .catch((axiosError) => toastError({ axiosError }));
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
    return <BarcodeScannerComponent onBarcodeScanned={onHUBarcodeScanned} />;
  }
};

export default HUManagerScreen;
