import React from 'react';
import { useRouteMatch } from 'react-router-dom';
import { shallowEqual, useSelector } from 'react-redux';
import { getActivityById } from '../../../reducers/wfProcesses';
import { getNextEligibleLineToPick } from '../../../utils/picking';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseQRCodeString } from '../../../utils/qrCode/hu';
import { pickingLineScanScreenLocation } from '../../../routes/picking';
import { NEXT_PickingJob } from './PickLineScanScreen';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';

const PickProductsScanScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'PickProductsScanScreen',
    captionKey: 'activities.picking.scanQRCode',
    back: getWFProcessScreenLocation,
  });

  const {
    params: { applicationId, workflowId: wfProcessId, activityId },
  } = useRouteMatch();

  const { activity } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId }), shallowEqual);

  const onBarcodeScanned = ({ scannedBarcode }) => {
    const qrCode = parseQRCodeString(scannedBarcode);
    const line = getNextEligibleLineToPick({ activity, productId: qrCode.productId });
    if (!line) {
      throw 'No matching lines found'; // TODO trl
    }

    const lineId = line.pickingLineId;
    console.log('onBarcodeScanned', { lineId, line, scannedBarcode });

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

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
};

const getPropsFromState = ({ state, wfProcessId, activityId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return { activity };
};

export default PickProductsScanScreen;
