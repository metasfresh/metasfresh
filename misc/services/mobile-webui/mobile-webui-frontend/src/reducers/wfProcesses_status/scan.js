import * as types from '../../constants/ScanActionTypes';

import { original } from 'immer';
import { extractActivitiesStatus, updateActivitiesStatus } from './utils';

export const scanReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.SET_SCANNED_BARCODE: {
      const { wfProcessId, activityId, scannedBarcode } = action.payload;
      const draftWFProcess = draftState[wfProcessId];
      const activitiesStatus = extractActivitiesStatus({ wfProcess: original(draftWFProcess) });

      draftWFProcess.activities[activityId].dataStored.scannedBarcode = scannedBarcode;
      // reset the barcode caption. it will be set back when we get it back from server
      draftWFProcess.activities[activityId].componentProps.barcodeCaption = null;

      const isComplete = !!scannedBarcode;
      draftWFProcess.activities[activityId].dataStored.isComplete = isComplete;
      activitiesStatus[activityId].isComplete = isComplete;

      updateActivitiesStatus({ draftWFProcess, activitiesStatus });
      return draftState;
    }

    default: {
      return draftState;
    }
  }
};
