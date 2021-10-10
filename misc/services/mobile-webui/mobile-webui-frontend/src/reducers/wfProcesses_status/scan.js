import * as types from '../../constants/ScanActionTypes';

import { updateActivitiesStatus } from './utils';

export const scanReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.SET_SCANNED_BARCODE: {
      const { wfProcessId, activityId, scannedBarcode } = action.payload;
      const draftWFProcess = draftState[wfProcessId];

      draftWFProcess.activities[activityId].dataStored.scannedBarcode = scannedBarcode;
      // reset the barcode caption. it will be set back when we get it back from server
      draftWFProcess.activities[activityId].componentProps.barcodeCaption = null;
      draftWFProcess.activities[activityId].dataStored.isComplete = !!(scannedBarcode && scannedBarcode.length > 0);

      updateActivitiesStatus({ draftWFProcess });
      return draftState;
    }

    default: {
      return draftState;
    }
  }
};
