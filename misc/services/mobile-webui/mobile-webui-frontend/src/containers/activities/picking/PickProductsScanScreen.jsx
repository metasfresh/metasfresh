import React from 'react';
import { getCustomQRCodeFormats, getLineByIdFromActivity, useWFActivity } from '../../../reducers/wfProcesses';
import { getQtyToPickRemainingForLine, isCatchWeight } from '../../../utils/picking';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { pickingLineScanScreenLocation } from '../../../routes/picking';
import { convertScannedBarcodeToResolvedResult, NEXT_PickingJob } from './PickLineScanScreen';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { isNoReadAttributes } from '../../../reducers/wfProcesses/picking/getReadAttributesFromActivity';
import { useDispatch } from 'react-redux';
import { postStepPickedThunk } from '../../../apps/picking/redux/postStepPickedThunk';
import * as api from '../../../api/picking';

const PickProductsScanScreen = () => {
  const { applicationId, wfProcessId, activityId } = useScreenDefinition({
    screenId: 'PickProductsScanScreen',
    captionKey: 'activities.picking.scanQRCode',
    back: getWFProcessScreenLocation,
  });
  const { onBarcodeScanned } = usePickProductsScan({ applicationId, wfProcessId, activityId });

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} />;
};

export default PickProductsScanScreen;

//
//
// ----------------------------------------------------------
//
//

export const usePickProductsScan = ({ applicationId, wfProcessId, activityId }) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();
  const activity = useWFActivity({ wfProcessId, activityId });
  const customQRCodeFormats = getCustomQRCodeFormats({ activity });

  const onBarcodeScanned = async ({ scannedBarcode }) => {
    const { qrCode } = await convertScannedBarcodeToResolvedResult({
      scannedBarcode,
      customQRCodeFormats,
    });
    // console.log('onBarcodeScanned', { scannedBarcode, qrCode });

    const { lineId } = await api.getNextEligibleLineToPack({ wfProcessId, huScannedCode: qrCode });
    if (!lineId) {
      throw 'No matching lines found'; // TODO trl
    }

    const openDialogScreen = () => {
      history.push(
        pickingLineScanScreenLocation({
          applicationId,
          wfProcessId,
          activityId,
          lineId,
          qrCode: scannedBarcode,
          next: NEXT_PickingJob,
        })
      );
    };

    const line = getLineByIdFromActivity(activity, lineId);
    const qtyToPickRemaining = getQtyToPickRemainingForLine({ line });
    if (qtyToPickRemaining === 1 && !isCatchWeight({ line }) && isNoReadAttributes({ activity })) {
      return dispatch(
        postStepPickedThunk({
          history,
          wfProcessId,
          activityId,
          lineId,
          huQRCode: scannedBarcode,
          qtyPicked: 1,
          checkIfAlreadyPacked: true,
        })
      )
        .then(
          ({ isPickingJobCompleted }) =>
            isPickingJobCompleted && history.replace(getWFProcessScreenLocation({ applicationId, wfProcessId }))
        )
        .catch((error) => {
          console.log('Got error while trying to pick directly. Opening dialog...', error);
          openDialogScreen();
        });
    } else {
      openDialogScreen();
    }
  };

  return {
    onBarcodeScanned,
  };
};
