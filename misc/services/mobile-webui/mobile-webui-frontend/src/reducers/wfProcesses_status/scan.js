import * as types from '../../constants/ScanActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';

import { updateUserEditable } from './utils';

export const scanReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.SET_SCANNED_BARCODE: {
      return reduceOnSetScannedBarcode(draftState, action.payload);
    }

    default: {
      return draftState;
    }
  }
};

function reduceOnSetScannedBarcode(draftState, payload) {
  const { wfProcessId, activityId, scannedBarcode } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftActivity = draftWFProcess.activities[activityId];

  draftActivity.dataStored.scannedBarcode = scannedBarcode;
  // reset the barcode caption. it will be set back when we get it back from server
  draftActivity.componentProps.barcodeCaption = null;

  draftActivity.dataStored.completeStatus = computeActivityStatus({ draftActivity });
  updateUserEditable({ draftWFProcess });

  return draftState;
}

const computeActivityStatus = ({ draftActivity }) => {
  const scannedBarcode = draftActivity.dataStored.scannedBarcode;
  return scannedBarcode && scannedBarcode.length > 0 ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};
