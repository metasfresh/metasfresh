import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import * as api from '../api';
import { changeClearanceStatus, clearLoadedData, handlingUnitLoaded } from '../actions';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import {
  huManagerBulkActionsLocation,
  huManagerDisposeLocation,
  huManagerHuLabelsLocation,
  huManagerMoveLocation,
} from '../routes';

import { HUInfoComponent } from '../components/HUInfoComponent';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ClearanceDialog from '../components/ClearanceDialog';
import { extractErrorResponseFromAxiosError, toastError } from '../../../utils/toast';
import ChangeHUQtyDialog from '../../../components/dialogs/ChangeHUQtyDialog';
import { isKnownQRCodeFormat } from '../../../utils/huQRCodes';
import SelectHUIntermediateList from './SelectHUIntermediateList';

const MODALS = {
  CHANGE_QTY: 'CHANGE_QTY',
  CLEARANCE_STATUS: 'CLEARANCE_STATUS',
};

const HUManagerScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const [clearanceStatuses, setClearanceStatuses] = useState([]);
  const [modalToDisplay, setModalToDisplay] = useState('');
  const [changeQtyAllowed, setChangeQtyAllowed] = useState(false);
  const [huListByDisplayableQrCode, setHuListByDisplayableQrCode] = useState([]);
  const [locatingQrCodeScannerInfo, setLocatingQrCodeScannerInfo] = useState(false);

  const { url } = useRouteMatch();
  useEffect(() => {
    // IMPORTANT, else it won't restore the title when we move back to this screen
    dispatch(pushHeaderEntry({ location: url }));
  }, []);

  const mergeClearanceStatuses = (statuses) => {
    const mergedStatuses = [...statuses];

    if (handlingUnitInfo.clearanceStatus) {
      mergedStatuses.push(handlingUnitInfo.clearanceStatus);
    }
    setClearanceStatuses(mergedStatuses);
  };

  const resolveHUScannedBarcode = async ({ scannedBarcode }) => {
    if (isKnownQRCodeFormat(scannedBarcode)) {
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
    return api
      .listHUsByQRCode({
        qrCode: locatingQrCodeScannerInfo.targetQrCode,
        upperLevelLocatingQrCode: scannedBarcode,
      })
      .then((huList) => {
        setLocatingQrCodeScannerInfo(undefined);
        return { huListByQRCode: huList };
      });
  };

  const onResolvedHUScannedResult = (result) => {
    console.log('onResolvedResult', { result });

    if (result.huListByQRCode) {
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
    dispatch(handlingUnitLoaded({ handlingUnitInfo }));
    setHuListByDisplayableQrCode([]);
    setLocatingQrCodeScannerInfo(undefined);
  };
  const onDisposeClick = () => {
    history.push(huManagerDisposeLocation());
  };
  const onMoveClick = () => {
    history.push(huManagerMoveLocation());
  };
  const onBulkActionsClick = () => {
    history.push(huManagerBulkActionsLocation());
  };
  const onScanAgainClick = () => {
    dispatch(clearLoadedData());
  };
  const onPrintLabelsClicked = () => {
    history.push(huManagerHuLabelsLocation());
  };
  const onSetClearanceClick = () => {
    toggleClearanceModal(true);
  };
  const onClearanceChange = ({ clearanceNote, clearanceStatus }) => {
    dispatch(changeClearanceStatus({ huId: handlingUnitInfo.id, clearanceNote, clearanceStatus })).finally(() => {
      toggleClearanceModal(false);
    });
  };
  const onChangeQtySubmit = ({ qty, description }) => {
    api
      .changeQty({
        huQRCode: handlingUnitInfo.qrCode,
        description: description,
        qty: qty,
      })
      .then((handlingUnitInfo) => {
        handlingUnitLoaded(handlingUnitInfo);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => toggleChangeQtyModal(false));
  };

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  useEffect(() => {
    if (handlingUnitInfo && !clearanceStatuses.length) {
      api
        .getAllowedClearanceStatusesRequest({ huId: handlingUnitInfo.id })
        .then((statuses) => mergeClearanceStatuses(statuses));
    }
  }, [handlingUnitInfo]);

  useEffect(() => {
    const isSingleStorage = handlingUnitInfo && handlingUnitInfo.products && handlingUnitInfo.products.length === 1;
    setChangeQtyAllowed(isSingleStorage);
  }, [handlingUnitInfo]);

  const toggleChangeQtyModal = (showModal) => {
    setModalToDisplay(showModal ? MODALS.CHANGE_QTY : '');
  };

  const toggleClearanceModal = (showModal) => {
    setModalToDisplay(showModal ? MODALS.CLEARANCE_STATUS : '');
  };

  if (handlingUnitInfo && handlingUnitInfo.id) {
    return (
      <>
        {modalToDisplay === MODALS.CLEARANCE_STATUS ? (
          <ClearanceDialog
            onCloseDialog={() => toggleClearanceModal(false)}
            onClearanceChange={onClearanceChange}
            clearanceStatuses={clearanceStatuses}
            handlingUnitInfo={handlingUnitInfo}
          />
        ) : null}
        {modalToDisplay === MODALS.CHANGE_QTY ? (
          <ChangeHUQtyDialog
            currentQty={Number(handlingUnitInfo.products[0].qty)}
            uom={handlingUnitInfo.products[0].uom}
            onCloseDialog={() => toggleChangeQtyModal(false)}
            onSubmit={onChangeQtySubmit}
          />
        ) : null}
        <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />
        <div className="pt-3 section">
          <ButtonWithIndicator caption={trl('huManager.action.dispose.buttonCaption')} onClick={onDisposeClick} />
          <ButtonWithIndicator caption={trl('huManager.action.move.buttonCaption')} onClick={onMoveClick} />
          <ButtonWithIndicator
            caption={trl('huManager.action.setClearance.buttonCaption')}
            onClick={onSetClearanceClick}
          />
          <ButtonWithIndicator
            caption={trl('huManager.action.bulkActions.buttonCaption')}
            onClick={onBulkActionsClick}
          />
          {changeQtyAllowed && (
            <ButtonWithIndicator
              caption={trl('huManager.action.changeQty.buttonCaption')}
              onClick={() => toggleChangeQtyModal(true)}
            />
          )}
          <ButtonWithIndicator
            caption={trl('huManager.action.printLabels.buttonCaption')}
            onClick={onPrintLabelsClicked}
          />
          <ButtonWithIndicator caption={trl('huManager.action.scanAgain.buttonCaption')} onClick={onScanAgainClick} />
        </div>
      </>
    );
  } else if (locatingQrCodeScannerInfo && locatingQrCodeScannerInfo.targetQrCode) {
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

export default HUManagerScreen;
