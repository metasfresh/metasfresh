import { original, produce } from 'immer';
import {
  DELETE_QUICKINPUT,
  SET_QUICKINPUT_DATA,
  SET_QUICKINPUT_LAYOUT,
  PATCH_QUICKINPUT_DONE,
  PATCH_QUICKINPUT_PENDING,
  UPDATE_QUICKINPUT_DATA,
} from '../constants/actions/TableQuickInputActionTypes';

// @VisibleForTesting
export const initialState = {
  layout: null,
  data: null,
  quickInputId: null,
  inProgress: false,
};

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case SET_QUICKINPUT_LAYOUT: {
      const { layout } = action.payload;

      draftState.layout = layout;

      break;
    }

    case SET_QUICKINPUT_DATA: {
      const { data, quickInputId } = action.payload;

      draftState.quickInputId = quickInputId;
      draftState.data = data;
      draftState.inProgress = false;

      break;
    }

    case UPDATE_QUICKINPUT_DATA: {
      const { fieldData } = action.payload;

      if (fieldData) {
        const currentData = original(draftState.data);

        Object.keys(fieldData).map((fieldName) => {
          draftState.data[fieldName] = {
            ...currentData[fieldName],
            ...fieldData[fieldName],
          };
        });
      }

      break;
    }

    case PATCH_QUICKINPUT_PENDING: {
      draftState.inProgress = true;

      break;
    }

    case PATCH_QUICKINPUT_DONE: {
      draftState.inProgress = false;

      break;
    }

    case DELETE_QUICKINPUT: {
      draftState.layout = null;
      draftState.data = null;
      draftState.quickInputId = null;
      draftState.inProgress = false;

      break;
    }
  }
}, initialState);

export default reducer;
