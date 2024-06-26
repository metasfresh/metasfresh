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
import ChangeCurrentLocatorDialog from '../components/ChangeCurrentLocatorDialog';
import { HU_ATTRIBUTE_BestBeforeDate, HU_ATTRIBUTE_LotNo } from '../../../constants/HUAttributes';

const MODALS = {
  CHANGE_QTY: 'CHANGE_QTY',
  CLEARANCE_STATUS: 'CLEARANCE_STATUS',
  SCAN_CURRENT_LOCATOR: 'SCAN_CURRENT_LOCATOR',
};

const HUManagerScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const [modalToDisplay, setModalToDisplay] = useState('');
  const [currentLocatorQRCode, setCurrentLocatorQRCode] = useState();
  const [handlingUnitInfo, setHandlingUnitInfo] = useHandlingUnitInfo();

  const { url } = useRouteMatch();
  useEffect(() => {
    // IMPORTANT, else it won't restore the title when we move back to this screen
    dispatch(pushHeaderEntry({ location: url }));
  }, []);

  const resolveScannedBarcode = ({ scannedBarcode }) => {
    return api.getHUByQRCode(scannedBarcode).then((handlingUnitInfo) => ({ handlingUnitInfo }));
  };

  const onResolvedResult = (result) => {
    //console.log('onResolvedResult', { result });
    const { handlingUnitInfo } = result;
    setHandlingUnitInfo(handlingUnitInfo);
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
  const onClearanceChange = ({ clearanceNote, clearanceStatus }) => {
    dispatch(changeClearanceStatus({ huId: handlingUnitInfo.id, clearanceNote, clearanceStatus })) //
      .finally(() => setModalToDisplay(''));
  };
  const onChangeQtySubmit = ({ qty, setBestBeforeDate, bestBeforeDate, setLotNo, lotNo, description }) => {
    api
      .changeQty({
        huId: handlingUnitInfo.id,
        huQRCode: handlingUnitInfo.qrCode,
        description: description,
        qty: qty,
        locatorQRCode: currentLocatorQRCode,
        setBestBeforeDate,
        bestBeforeDate,
        setLotNo,
        lotNo,
      })
      .then(setHandlingUnitInfo)
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setModalToDisplay(''));
  };

  const clearanceStatuses = useClearanceStatuses({
    huId: handlingUnitInfo?.id,
    huClearanceStatus: handlingUnitInfo?.clearanceStatus,
  });

  const {
    isSingleStorage,
    qty: singleStorageQty,
    uom: singleStorageUom,
  } = computeSingleStorageQtyAndUOM(handlingUnitInfo);

  const isExistingHU = !!handlingUnitInfo?.id;

  // NOTE: keep in sync with de.metas.handlingunits.movement.MoveHUCommand.getMoveToCUConsumer
  const isAllowMove = isExistingHU && isCU(handlingUnitInfo);

  const isAllowQtyChange =
    isSingleStorage && //
    (isExistingHU || !!currentLocatorQRCode?.locatorId); // either we have an huId or we scanned the locator where the new HU will be created

  if (handlingUnitInfo) {
    return (
      <>
        {modalToDisplay === MODALS.CLEARANCE_STATUS && (
          <ClearanceDialog
            handlingUnitInfo={handlingUnitInfo}
            clearanceStatuses={clearanceStatuses}
            onClearanceChange={onClearanceChange}
            onCloseDialog={() => setModalToDisplay('')}
          />
        )}
        {modalToDisplay === MODALS.CHANGE_QTY && (
          <ChangeHUQtyDialog
            currentQty={singleStorageQty}
            uom={singleStorageUom}
            isShowBestBeforeDate={!isExistingHU}
            bestBeforeDate={getBestBeforeDate(handlingUnitInfo)}
            isShowLotNo={!isExistingHU}
            lotNo={getLotNo(handlingUnitInfo)}
            onSubmit={onChangeQtySubmit}
            onCloseDialog={() => setModalToDisplay('')}
          />
        )}
        {modalToDisplay === MODALS.SCAN_CURRENT_LOCATOR && (
          <ChangeCurrentLocatorDialog
            onOK={(locatorQRCode) => {
              setCurrentLocatorQRCode(locatorQRCode);
              setModalToDisplay('');
            }}
            onClose={() => setModalToDisplay('')}
          />
        )}
        <HUInfoComponent handlingUnitInfo={handlingUnitInfo} currentLocatorQRCode={currentLocatorQRCode} />
        <div className="pt-3 section">
          {isExistingHU && (
            <ButtonWithIndicator caption={trl('huManager.action.dispose.buttonCaption')} onClick={onDisposeClick} />
          )}
          {isAllowMove && (
            <ButtonWithIndicator caption={trl('huManager.action.move.buttonCaption')} onClick={onMoveClick} />
          )}
          {isExistingHU && (
            <ButtonWithIndicator
              caption={trl('huManager.action.setClearance.buttonCaption')}
              onClick={() => setModalToDisplay(MODALS.CLEARANCE_STATUS)}
            />
          )}
          {!isExistingHU && (
            <ButtonWithIndicator
              caption={trl('huManager.action.setCurrentLocator.buttonCaption')}
              onClick={() => setModalToDisplay(MODALS.SCAN_CURRENT_LOCATOR)}
            />
          )}
          {isAllowQtyChange && (
            <ButtonWithIndicator
              caption={trl('huManager.action.changeQty.buttonCaption')}
              onClick={() => setModalToDisplay(MODALS.CHANGE_QTY)}
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

const useHandlingUnitInfo = () => {
  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  const dispatch = useDispatch();
  const setHandlingUnitInfo = (handlingUnitInfo) => dispatch(handlingUnitLoaded({ handlingUnitInfo }));

  return [handlingUnitInfo, setHandlingUnitInfo];
};

const useClearanceStatuses = ({ huId, huClearanceStatus }) => {
  const [clearanceStatuses, setClearanceStatuses] = useState([]);

  useEffect(() => {
    if (huId && !clearanceStatuses.length) {
      api.getAllowedClearanceStatusesRequest({ huId }).then((statuses) => {
        const mergedStatuses = [...statuses];

        if (huClearanceStatus) {
          mergedStatuses.push(huClearanceStatus);
        }

        setClearanceStatuses(mergedStatuses);
      });
    }
  }, [huId, huClearanceStatus, clearanceStatuses]);

  return clearanceStatuses;
};

const computeSingleStorageQtyAndUOM = (handlingUnitInfo) => {
  const isSingleStorage = handlingUnitInfo && handlingUnitInfo.products && handlingUnitInfo.products.length === 1;
  if (!isSingleStorage) {
    return { isSingleStorage: false, qty: null, uom: null };
  }

  let qty = Number(handlingUnitInfo.products[0].qty);
  if (handlingUnitInfo.numberOfAggregatedHUs && handlingUnitInfo.numberOfAggregatedHUs > 1) {
    qty = qty / handlingUnitInfo.numberOfAggregatedHUs;
  }

  return {
    isSingleStorage: true,
    qty,
    uom: handlingUnitInfo.products[0].uom,
  };
};

const isCU = (handlingUnitInfo) => {
  return getHUUnitType(handlingUnitInfo) === 'CU';
};

const getHUUnitType = (handlingUnitInfo) => {
  return handlingUnitInfo?.jsonHUType;
};

const getBestBeforeDate = (handlingUnitInfo) => {
  return getAttributeValue(handlingUnitInfo, HU_ATTRIBUTE_BestBeforeDate);
};
const getLotNo = (handlingUnitInfo) => {
  return getAttributeValue(handlingUnitInfo, HU_ATTRIBUTE_LotNo);
};

const getAttributeValue = (handlingUnitInfo, attributeCode) => {
  const attributesList = handlingUnitInfo?.attributes2?.list ?? [];
  const attribute = attributesList.find((attribute) => attribute.code === attributeCode);
  return attribute?.value;
};

export default HUManagerScreen;
