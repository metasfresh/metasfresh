import React, { useEffect, useState } from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { reportInventoryCounting, resolveHU, resolveLocator } from '../api';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryJobLocation } from '../routes';
import { toastError, toastErrorFromObj } from '../../../utils/toast';
import InventoryCountComponent from './InventoryCountComponent';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';

const STATUS_ScanLocator = 'ScanLocator';
const STATUS_ScanHU = 'ScanHU';
const STATUS_FillData = 'FillData';

const DEBUGGING = false;

const InventoryScanScreen = () => {
  const { applicationId, wfProcessId, lineId, history } = useScreenDefinition({
    screenId: 'InventoryScanScreen',
    back: inventoryJobLocation,
  });
  const dispatch = useDispatch();

  const [processing, setProcessing] = useState(false);
  const [status, setStatus] = useState(STATUS_ScanLocator);
  const [locatorQRCode, setLocatorQRCode] = useState();
  const [resolvedHU, setResolvedHU] = useState();

  const isReadonly = processing;

  // FIXME: DEBUGGING
  if (DEBUGGING) {
    useEffect(() => {
      setLocatorQRCode('LOC#1#{"warehouseId":1000229,"locatorId":1000119,"caption":"wh_20251002T093155523_Locator"}');
      setResolvedHU({
        lineId: 1000169,
        huId: 1003822,
        productId: 2007215,
        uom: 'Stk',
        qtyBooked: 68,
        attributes: [
          {
            code: 'HU_BestBeforeDate',
            caption: 'Mindesthaltbarkeit',
            valueType: 'DATE',
            value: null,
          },
          {
            code: 'Lot-Nummer',
            caption: 'Lot-Nummer',
            valueType: 'STRING',
            value: null,
          },
        ],
      });
      setStatus(STATUS_FillData);
    }, []);
  }
  // console.log('InventoryScanScreen - locatorQRCode: ' + JSON.stringify(locatorQRCode, null, 2));
  // console.log('InventoryScanScreen - resolvedHU: ' + JSON.stringify(resolvedHU, null, 2));

  const onLocatorScanned = ({ scannedBarcode }) => {
    resolveLocator({ scannedBarcode, wfProcessId, lineId }).then((response) => {
      if (response.qrCode) {
        setLocatorQRCode(response.qrCode);
        setStatus(STATUS_ScanHU);
      } else {
        toastError({ messageKey: 'error.qrCode.invalid', context: { scannedBarcode, wfProcessId, lineId, response } });
      }
    });
  };

  const onHUScanned = ({ scannedBarcode }) => {
    resolveHU({ scannedBarcode, wfProcessId, lineId, locatorQRCode }).then((response) => {
      setResolvedHU(response);
      setStatus(STATUS_FillData);
    });
  };

  const onInventoryCountSubmit = ({ qtyCount, attributes, lineCountingDone }) => {
    setProcessing(true);
    reportInventoryCounting({
      wfProcessId,
      lineId: resolvedHU?.lineId,
      scannedBarcode: 'TODO', // TODO
      huId: resolvedHU?.huId,
      qtyCount,
      lineCountingDone,
      attributes,
    })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        history.goTo(inventoryJobLocation({ applicationId, wfProcessId }));
      })
      .catch((error) => toastErrorFromObj(error))
      .finally(() => setProcessing(false));
  };

  if (status === STATUS_ScanLocator) {
    return (
      <BarcodeScannerComponent
        key="scanLocator"
        inputPlaceholderText="Scan Locator"
        onResolvedResult={onLocatorScanned}
        continuousRunning={true}
      />
    );
  } else if (status === STATUS_ScanHU) {
    return (
      <BarcodeScannerComponent
        key="scanHU"
        inputPlaceholderText="Scan HU"
        onResolvedResult={onHUScanned}
        continuousRunning={true}
      />
    );
  } else if (status === STATUS_FillData) {
    return (
      <InventoryCountComponent
        disabled={isReadonly}
        resolvedHU={resolvedHU}
        onInventoryCountSubmit={onInventoryCountSubmit}
      />
    );
  }
};

export default InventoryScanScreen;
