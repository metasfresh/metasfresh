import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';

import * as api from '../api';
import { handlingUnitLoaded } from '../actions';

import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { toastError } from '../../../utils/toast';

const HUMoveScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }
  }, []);

  const onResolvedResult = ({ scannedBarcode }) => {
    console.log('HUMoveScreen.onResolvedResult', { scannedBarcode, handlingUnitInfo });
    api
      .moveHU({ huId: handlingUnitInfo.id, targetQRCode: scannedBarcode })
      .then((handlingUnitInfo) => {
        dispatch(handlingUnitLoaded({ handlingUnitInfo }));
        history.goBack();
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return <BarcodeScannerComponent onResolvedResult={onResolvedResult} />;
};

export default HUMoveScreen;
