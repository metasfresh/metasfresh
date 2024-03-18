import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import { getActivityById } from '../../../reducers/wfProcesses';
import { getLinesByProductId, isAllowPickingAnyHUForLine, isLineNotCompleted } from '../../../utils/picking';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseQRCodeString } from '../../../utils/qrCode/hu';
import { pickingLineScanScreenLocation } from '../../../routes/picking';

const PickProductsScanScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId },
  } = useRouteMatch();

  const { activity } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId }), shallowEqual);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.picking.scanQRCode'),
        values: [],
      })
    );
  }, []);

  const history = useHistory();
  const onBarcodeScanned = ({ scannedBarcode }) => {
    const qrCode = parseQRCodeString(scannedBarcode);
    const line = getEligibleLineByProductId({ activity, productId: qrCode.productId });
    const lineId = line.pickingLineId;
    console.log('onBarcodeScanned', { lineId, line, scannedBarcode });

    history.push(
      pickingLineScanScreenLocation({ applicationId, wfProcessId, activityId, lineId, qrCode: scannedBarcode })
    );
  };

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
};

const getPropsFromState = ({ state, wfProcessId, activityId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return { activity };
};

const getEligibleLineByProductId = ({ activity, productId }) => {
  const eligibleLines = getLinesByProductId(activity, productId).filter(
    (line) => isLineNotCompleted({ line }) && isAllowPickingAnyHUForLine({ line })
  );

  // console.log('getEligibleLineByProductId', { eligibleLines });

  if (!eligibleLines?.length) {
    throw 'No matching lines found'; // TODO trl
  } else {
    return eligibleLines[0];
  }
};

export default PickProductsScanScreen;
