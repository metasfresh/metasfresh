import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { toastError } from '../../../../utils/toast';

import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { updateManufacturingReceipt, updateManufacturingReceiptTarget } from '../../../../actions/ManufacturingActions';
import { getLineById } from '../../../../reducers/wfProcesses';

import BarcodeScannerComponent from '../../../../components/BarcodeScannerComponent';

const getQtyReceivedFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  return line?.qtyReceived ?? 0;
};

const ManufacturingReceiptScanScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const qtyReceived = useSelector((state) => getQtyReceivedFromState({ state, wfProcessId, activityId, lineId }));

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: 'Receive to HU', // TODO trl
      })
    );
  }, []);

  const history = useHistory();
  const onBarcodeScanned = ({ scannedBarcode }) => {
    dispatch(
      updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target: { huBarcode: scannedBarcode } })
    );

    // TODO: If quantity is already picked, update on the backend
    if (qtyReceived) {
      dispatch(
        updateManufacturingReceipt({
          wfProcessId,
          activityId,
          lineId,
        })
      ).catch((axiosError) => toastError({ axiosError }));
    }
    history.go(-2);
  };

  return (
    <>
      <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} />
    </>
  );
};

export default ManufacturingReceiptScanScreen;
