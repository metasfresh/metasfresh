import React, { useState, useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import * as api from '../api';
import { clearLoadedData, handlingUnitLoaded, changeClearanceStatus } from '../actions';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { huManagerDisposeLocation, huManagerMoveLocation } from '../routes';

import { HUInfoComponent } from '../components/HUInfoComponent';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ClearanceDialog from '../components/ClearanceDialog';

const HUManagerScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const [clearanceModalDisplayed, toggleClearanceModal] = useState(false);
  const [clearanceStatuses, setClearanceStatuses] = useState([]);

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
    // .catch((axiosError) => ({
    //   error: extractUserFriendlyErrorMessageFromAxiosError({ axiosError }),
    // }));
  };

  const onResolvedResult = (result) => {
    console.log('onResolvedResult', { result });
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

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  useEffect(() => {
    if (handlingUnitInfo && !clearanceStatuses.length) {
      api
        .getAllowedClearanceStatusesRequest({ huId: handlingUnitInfo.id })
        .then((statuses) => mergeClearanceStatuses(statuses));
    }
  }, [handlingUnitInfo]);

  if (handlingUnitInfo && handlingUnitInfo.id) {
    return (
      <>
        {clearanceModalDisplayed ? (
          <ClearanceDialog
            onCloseDialog={() => toggleClearanceModal(false)}
            onClearanceChange={onClearanceChange}
            clearanceStatuses={clearanceStatuses}
            handlingUnitInfo={handlingUnitInfo}
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
