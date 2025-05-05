import {
  DELETE_QUICKINPUT,
  PATCH_QUICKINPUT_DONE,
  PATCH_QUICKINPUT_PENDING,
  SET_QUICKINPUT_DATA,
  SET_QUICKINPUT_LAYOUT,
  UPDATE_QUICKINPUT_DATA,
} from '../constants/actions/TableQuickInputActionTypes';
import { parseToDisplay } from '../utils/documentListHelper';
import * as api from '../api/windowQuickInput';

/**
 * @summary Remove quick input's data
 */
export const deleteQuickInput = () => {
  return {
    type: DELETE_QUICKINPUT,
  };
};

/**
 * @summary Set the `inProgress` flag of QuickInput to true
 */
const setQuickinputPatchPending = () => {
  return {
    type: PATCH_QUICKINPUT_PENDING,
  };
};

/**
 * @summary Set the `inProgress` flag of QuickInput to true
 */
const setQuickinputPatchDone = () => {
  return {
    type: PATCH_QUICKINPUT_DONE,
  };
};

/**
 * @summary Save field changes locally
 *
 * @param {object} fieldData - object with field names as keys and their data as values
 */
export const updateQuickinputData = (fieldData) => {
  return {
    type: UPDATE_QUICKINPUT_DATA,
    payload: { fieldData },
  };
};

/*
 * @method patchQuickInput
 * @summary Save QuickInput changes on the server
 *
 * @param {number} windowId
 * @param {string} docId
 * @param {string} tabId
 * @param {string} prop
 * @param {any} value
 */
export const patchQuickInput = ({ windowId, docId, tabId, prop, value }) => {
  return (dispatch, getState) => {
    const state = getState();
    const { quickInputId } = state.tableQuickInputHandler;

    dispatch(setQuickinputPatchPending());

    return api
      .patchQuickInput({
        windowId,
        docId,
        tabId,
        quickInputId,
        fieldName: prop,
        fieldValue: value,
      })
      .then(({ data }) => {
        const fields = data[0] && data[0].fieldsByName;

        if (fields) {
          dispatch(updateQuickinputData(fields));
        }

        dispatch(setQuickinputPatchDone());
      });
  };
};

/*
 * @summary Get data for QuickInput's fields
 */
export const fetchQuickInputData = ({ windowId, docId, tabId }) => {
  return (dispatch) => {
    return api.initQuickInput(windowId, docId, tabId).then(({ data }) => {
      dispatch({
        type: SET_QUICKINPUT_DATA,
        payload: {
          quickInputId: data.id,
          data: parseToDisplay(data.fieldsByName),
        },
      });
    });
  };
};

/*
 * @summary Get QuickInput's layout and save it in the store
 */
export const fetchQuickInputLayout = ({ windowId, docId, tabId }) => {
  return (dispatch) => {
    return api.getQuickInputLayout(windowId, docId, tabId).then(({ data }) => {
      dispatch({
        type: SET_QUICKINPUT_LAYOUT,
        payload: {
          layout: data.elements,
        },
      });
    });
  };
};

export const completeQuickInput = ({
  windowId,
  docId,
  tabId,
  quickInputId,
}) => {
  return api.completeQuickInput(windowId, docId, tabId, quickInputId);
};
