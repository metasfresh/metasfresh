import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import * as api from '../api';
import { handlingUnitLoaded } from '../actions';

import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { toastError } from '../../../utils/toast';
import { HUInfoComponent } from '../components/HUInfoComponent';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huManagerLocation } from '../routes';

const HUMoveScreen = () => {
  const { history } = useScreenDefinition({
    captionKey: 'huManager.action.move.scanTarget',
    back: huManagerLocation,
  });

  const dispatch = useDispatch();

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
    }
  }, []);

  const onResolvedResult = ({ scannedBarcode }) => {
    api
      .moveHU({
        huId: handlingUnitInfo.id,
        huQRCode: handlingUnitInfo.qrCode,
        targetQRCode: scannedBarcode,
      })
      .then((handlingUnitInfo) => {
        dispatch(handlingUnitLoaded({ handlingUnitInfo }));
      })
      .catch((axiosError) => toastError({ axiosError }));

    // we have to go back anyway because at this point the scanner is no longer displayed
    history.goBack();
  };

  return (
    <>
      <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />
      <BarcodeScannerComponent onResolvedResult={onResolvedResult} />
    </>
  );
};

export default HUMoveScreen;
