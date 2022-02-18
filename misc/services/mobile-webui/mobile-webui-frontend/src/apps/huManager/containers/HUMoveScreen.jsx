import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import * as api from '../api';
import { handlingUnitLoaded } from '../actions';

import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { toastError } from '../../../utils/toast';
import { HUInfoComponent } from '../components/HUInfoComponent';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

const HUMoveScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  const { url } = useRouteMatch();
  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }

    dispatch(pushHeaderEntry({ location: url, caption: trl('huManager.action.move.scanTarget') }));
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
