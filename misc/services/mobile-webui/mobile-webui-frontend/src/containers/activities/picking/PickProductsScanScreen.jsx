import React from 'react';
import { getCustomQRCodeFormats, useWFActivity } from '../../../reducers/wfProcesses';
import { getNextEligibleLineToPick, getQtyToPickRemainingForLine, isCatchWeight } from '../../../utils/picking';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { pickingLineScanScreenLocation } from '../../../routes/picking';
import { convertScannedBarcodeToResolvedResult, NEXT_PickingJob } from './PickLineScanScreen';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { isNoReadAttributes } from '../../../reducers/wfProcesses/picking/getReadAttributesFromActivity';
import { postStepPicked } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';

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

    const line = getNextEligibleLineToPick({ activity, productId: qrCode.productId });
    if (!line) {
      throw 'No matching lines found'; // TODO trl
    }

    const lineId = line.pickingLineId;
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

    const qtyToPickRemaining = getQtyToPickRemainingForLine({ line });
    console.log('usePickProductsScan', { lineId, line, qrCode, qtyToPickRemaining, activity });
    if (qtyToPickRemaining === 1 && !isCatchWeight({ line }) && isNoReadAttributes({ activity })) {
      return postStepPicked({
        wfProcessId,
        activityId,
        lineId,
        huQRCode: scannedBarcode,
        qtyPicked: 1,
        checkIfAlreadyPacked: true,
      })
        .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
        .then(() => history.replace(getWFProcessScreenLocation({ applicationId, wfProcessId })))
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
