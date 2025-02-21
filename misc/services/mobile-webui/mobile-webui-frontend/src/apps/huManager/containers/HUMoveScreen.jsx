import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import * as api from '../api';
import { clearLoadedData } from '../actions';

import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { toastError } from '../../../utils/toast';
import { HUInfoComponent } from '../components/HUInfoComponent';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huManagerLocation } from '../routes';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ReadQtyDialog from '../../../components/dialogs/ReadQtyDialog';

const HUMoveScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'HUMoveScreen',
    captionKey: 'huManager.action.move.scanTarget',
    back: huManagerLocation,
  });

  const dispatch = useDispatch();

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  const [showQtyTUInput, setShowQtyTUInput] = useState(false);
  const [isMoveInProgress, setIsMoveInProgress] = useState(false);
  const [scannedTarget, setScannedTarget] = useState();

  const { url } = useRouteMatch();
  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }

    dispatch(pushHeaderEntry({ location: url, caption: trl('huManager.action.move.scanTarget') }));
  }, [handlingUnitInfo]);

  const toggleShowQtyTUInput = ({ isDisplayed, scannedTargetHU }) => {
    if (isDisplayed && !scannedTargetHU) {
      setShowQtyTUInput(false);
      return toastError({ messageKey: 'activities.huManager.missingTargetQrCode' });
    } else if (isDisplayed) {
      setShowQtyTUInput(true);
      setScannedTarget(scannedTargetHU);
    } else {
      setShowQtyTUInput(false);
      setScannedTarget(undefined);
    }
  };

  const onResolvedResult = ({ scannedBarcode }) => {
    if (handlingUnitInfo.numberOfAggregatedHUs && handlingUnitInfo.numberOfAggregatedHUs > 1) {
      toggleShowQtyTUInput({ isDisplayed: true, scannedTargetHU: scannedBarcode });
    } else {
      moveHUs({ scannedBarcode });
    }
  };

  const moveHUs = ({ scannedBarcode, numberOfTUs }) => {
    setIsMoveInProgress(true);
    api
      .moveHU({
        huId: handlingUnitInfo.id,
        huQRCode: handlingUnitInfo.qrCode,
        targetQRCode: scannedBarcode,
        numberOfTUs: numberOfTUs,
      })
      .then(() => {
        dispatch(clearLoadedData());
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => {
        setIsMoveInProgress(false);
        toggleShowQtyTUInput({ isDisplayed: false });
      });
  };

  if (!handlingUnitInfo) return <></>;

  return (
    <>
      {showQtyTUInput && (
        <ReadQtyDialog
          qtyLabelTrlKey={'huManager.action.move.qtyTULabel'}
          submitButtonTrlKey={'huManager.action.move.buttonCaption'}
          onCloseDialog={() => toggleShowQtyTUInput({ isDisplayed: false })}
          onSubmit={(qty) => moveHUs({ scannedBarcode: scannedTarget, numberOfTUs: qty.qty })}
          isReadOnly={isMoveInProgress}
        />
      )}
      <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />
      {!showQtyTUInput && <BarcodeScannerComponent onResolvedResult={onResolvedResult} />}
    </>
  );
};

export default HUMoveScreen;
