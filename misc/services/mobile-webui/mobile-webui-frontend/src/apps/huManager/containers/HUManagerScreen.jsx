import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import * as api from '../api';
import { changeClearanceStatus, clearLoadedData, handlingUnitLoaded } from '../actions';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { huManagerDisposeLocation, huManagerMoveLocation } from '../routes';

import { HUInfoComponent } from '../components/HUInfoComponent';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ClearanceDialog from '../components/ClearanceDialog';
import { toastError } from '../../../utils/toast';
import ChangeHUQtyDialog from '../../../components/dialogs/ChangeHUQtyDialog';

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

  const resolveScannedBarcode = ({ scannedBarcode }) => {
    return api.getHUByQRCode(scannedBarcode).then((handlingUnitInfo) => ({ handlingUnitInfo }));
  };

  const onResolvedResult = (result) => {
    //console.log('onResolvedResult', { result });
    const { handlingUnitInfo } = result;
    dispatch(handlingUnitLoaded({ handlingUnitInfo }));
  };

  const onDisposeClick = () => {
    history.push(huManagerDisposeLocation());
  };
  const onMoveClick = () => {
    history.push(huManagerMoveLocation());
  };
  const onScanAgainClick = () => {
    dispatch(clearLoadedData());
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
        huId: handlingUnitInfo.id,
        huQRCode: handlingUnitInfo.qrCode,
        description: description,
        qty: qty,
      })
      .then(() => {
        dispatch(clearLoadedData());
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => toggleChangeQtyModal(false));
  };

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  useEffect(() => {
    if (handlingUnitInfo?.id && !clearanceStatuses.length) {
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

  const computeInitialQtyForChangeModal = () => {
    const isSingleStorage = handlingUnitInfo && handlingUnitInfo.products && handlingUnitInfo.products.length === 1;
    if (!isSingleStorage) {
      toastError({ plainMessage: 'huManager.action.changeQty.allowedOnlyForSingleProducts' });
      return 0;
    }

    const totalQty = Number(handlingUnitInfo.products[0].qty);
    if (handlingUnitInfo.numberOfAggregatedHUs && handlingUnitInfo.numberOfAggregatedHUs > 1) {
      return totalQty / handlingUnitInfo.numberOfAggregatedHUs;
    } else {
      return totalQty;
    }
  };

  if (handlingUnitInfo) {
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
            currentQty={computeInitialQtyForChangeModal()}
            uom={handlingUnitInfo.products[0].uom}
            onCloseDialog={() => toggleChangeQtyModal(false)}
            onSubmit={onChangeQtySubmit}
          />
        ) : null}
        <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />
        <div className="pt-3 section">
          {handlingUnitInfo?.id && (
            <ButtonWithIndicator caption={trl('huManager.action.dispose.buttonCaption')} onClick={onDisposeClick} />
          )}
          {handlingUnitInfo?.id && (
            <ButtonWithIndicator caption={trl('huManager.action.move.buttonCaption')} onClick={onMoveClick} />
          )}
          {handlingUnitInfo?.id && (
            <ButtonWithIndicator
              caption={trl('huManager.action.setClearance.buttonCaption')}
              onClick={onSetClearanceClick}
            />
          )}
          {changeQtyAllowed && (
            <ButtonWithIndicator
              caption={trl('huManager.action.changeQty.buttonCaption')}
              onClick={() => toggleChangeQtyModal(true)}
            />
          )}
          <ButtonWithIndicator caption={trl('huManager.action.scanAgain.buttonCaption')} onClick={onScanAgainClick} />
        </div>
      </>
    );
  } else {
    return (
      <BarcodeScannerComponent
        continuousRunning={true}
        resolveScannedBarcode={resolveScannedBarcode}
        onResolvedResult={onResolvedResult}
      />
    );
  }
};

export default HUManagerScreen;
