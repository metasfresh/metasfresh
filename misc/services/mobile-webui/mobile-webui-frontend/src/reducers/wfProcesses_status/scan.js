import * as types from '../../constants/ScanActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';

import { updateUserEditable } from './utils';
import { registerHandler } from './activityStateHandlers';

const COMPONENT_TYPE = 'common/scanBarcode';

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
  draftActivityDataStored.scannedBarcode = scannedBarcode;
  draftActivityDataStored.completeStatus = computeActivityStatus({ draftActivityDataStored });
  updateUserEditable({ draftWFProcess });

  return draftState;
}

const computeActivityStatus = ({ draftActivityDataStored }) => {
  const scannedBarcode = draftActivityDataStored.scannedBarcode;
  const barcodeCaption = draftActivityDataStored.barcodeCaption;
  const completed = (scannedBarcode && scannedBarcode.length > 0) || (barcodeCaption && barcodeCaption.length > 0);
  console.log('computeActivityStatus', { scannedBarcode, barcodeCaption, completed });
  return completed ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.barcodeCaption = fromActivity.componentProps.barcodeCaption;
    draftActivityDataStored.completeStatus = computeActivityStatus({ draftActivityDataStored });
  },
});
