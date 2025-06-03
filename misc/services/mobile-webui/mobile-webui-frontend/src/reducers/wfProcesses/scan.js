import * as types from '../../constants/ScanActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';

import { updateUserEditable } from './utils';
import { registerHandler } from './activityStateHandlers';
import { COMPONENTTYPE_ScanAndValidateBarcode } from '../../containers/activities/scan/ScanAndValidateActivity';
import { COMPONENTTYPE_ScanBarcode } from '../../containers/activities/scan/ScanActivity';

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

  const draftActivityDataStored = draftActivity.dataStored;
  draftActivityDataStored.currentValue = scannedBarcode ? { qrCode: scannedBarcode, caption: scannedBarcode } : null;
  draftActivityDataStored.completeStatus = computeActivityStatus({ draftActivityDataStored });
  updateUserEditable({ draftWFProcess });

  return draftState;
}

const computeActivityStatus = ({ draftActivityDataStored }) => {
  const currentValue = draftActivityDataStored.currentValue;
  const completed = currentValue && currentValue.qrCode && currentValue.qrCode.length > 0;
  return completed ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};

registerHandler({
  componentType: COMPONENTTYPE_ScanBarcode,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.currentValue = fromActivity.componentProps.currentValue;
    draftActivityDataStored.validOptions = fromActivity.componentProps.validOptions;
    draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? false;
    draftActivityDataStored.completeStatus = computeActivityStatus({ draftActivityDataStored });
    draftActivityDataStored.confirmationModalMsg = fromActivity.componentProps.confirmationModalMsg;
  },
});

registerHandler({
  componentType: COMPONENTTYPE_ScanAndValidateBarcode,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.currentValue = fromActivity.componentProps.currentValue;
    draftActivityDataStored.validOptions = fromActivity.componentProps.validOptions;
    draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? false;
    draftActivityDataStored.completeStatus = computeActivityStatus({ draftActivityDataStored });
  },
});
