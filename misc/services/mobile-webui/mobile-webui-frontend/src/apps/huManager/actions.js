import * as api from './api';
import { setClearanceStatusRequest } from './api';

import { CHANGE_CLEARANCE_STATUS, CLEAR_LOADED_DATA, HANDLING_UNIT_LOADED } from './actionTypes';
import { postToAndroidPrinterProxy } from '../../api/androidPrinterProxy';

export const clearLoadedData = () => {
  return {
    type: CLEAR_LOADED_DATA,
  };
};

export const handlingUnitLoaded = ({ handlingUnitInfo }) => {
  return {
    type: HANDLING_UNIT_LOADED,
    payload: handlingUnitInfo,
  };
};

export const changeClearanceStatus = ({ huId, clearanceNote, clearanceStatus }) => {
  return (dispatch) => {
    if (clearanceStatus.key) {
      return setClearanceStatusRequest({ huId, clearanceNote, clearanceStatus: clearanceStatus.key }).then(() => {
        dispatch({
          type: CHANGE_CLEARANCE_STATUS,
          payload: { clearanceNote, clearanceStatus },
        });

        return Promise.resolve();
      });
    }
    return Promise.reject();
  };
};

export const printHULabels = ({ huId, huLabelProcessId, nrOfCopies }) => {
  return api
    .printHULabels({
      huId,
      huLabelProcessId,
      nrOfCopies,
    })
    .then((response) => {
      if (response?.printData?.length > 0) {
        response.printData.forEach((printDataItem) => {
          console.log('printHULabels - item:', { printDataItem });
          postToAndroidPrinterProxy({
            printerUri: printDataItem.printerURI,
            fileName: printDataItem.filename,
            dataBase64Encoded: printDataItem.dataBase64Encoded,
          });
        });
      }
    });
};
