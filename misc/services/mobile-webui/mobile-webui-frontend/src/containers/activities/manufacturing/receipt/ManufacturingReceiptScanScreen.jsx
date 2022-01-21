import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { toastError } from '../../../../utils/toast';

import { updateManufacturingReceipt, updateManufacturingReceiptTarget } from '../../../../actions/ManufacturingActions';
import { getLineById } from '../../../../reducers/wfProcesses_status';

import CodeScanner from '../../scan/CodeScanner';

const getQtyReceivedFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  return line != null ? line.qtyReceived : null;
};

const ManufacturingReceiptScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const dispatch = useDispatch();
  const qtyReceived = useSelector((state) => getQtyReceivedFromState({ state, wfProcessId, activityId, lineId }));

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
      <CodeScanner onBarcodeScanned={onBarcodeScanned} />
    </>
  );
};

export default ManufacturingReceiptScanScreen;
