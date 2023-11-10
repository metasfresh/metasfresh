import React, { useEffect } from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { assignExternalLotNumber } from '../api';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import { handlingUnitLoaded } from '../actions';
import { toastError } from '../../../utils/toast';

export const AssociateExternalLotNoAction = () => {
  const history = useHistory();
  const dispatch = useDispatch();
  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  const { url } = useRouteMatch();
  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }

    dispatch(
      pushHeaderEntry({ location: url, caption: trl('components.huManager.action.assignExternalLotNumber.scanTarget') })
    );
  }, []);

  const assignLotNo = (qrCode) => {
    assignExternalLotNumber({ huId: handlingUnitInfo.id, qrCode })
      .then((handlingUnitInfo) => {
        dispatch(handlingUnitLoaded({ handlingUnitInfo }));
      })
      .catch((axiosError) => toastError({ axiosError }));

    history.goBack();
  };

  return <BarcodeScannerComponent onResolvedResult={({ scannedBarcode }) => assignLotNo(scannedBarcode)} />;
};
