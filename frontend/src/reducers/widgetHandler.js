import { produce, original } from 'immer';

import {
  FETCH_ATTRIBUTES_DATA,
  FETCH_ATTRIBUTES_LAYOUT,
  DELETE_ATTRIBUTES,
  PATCH_ATTRIBUTES,
  SET_ATTRIBUTES_DATA,
  FETCH_QUICKINPUT_DATA,
  FETCH_QUICKINPUT_LAYOUT,
  DELETE_QUICKINPUT,
  SET_QUICKINPUT_DATA,
  PATCH_QUICKINPUT_PENDING,
  PATCH_QUICKINPUT_DONE,
} from '../constants/ActionTypes';
import { parseToDisplay } from '../utils/documentListHelper';

export const initialState = {
  attributes: {
    dataId: null,
    fields: {},
    elements: [],
  },
  quickInput: {
    layout: null,
    data: null,
    id: null,
    inProgress: false,
  },
};

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case FETCH_ATTRIBUTES_DATA: {
      const { fieldsByName, id } = action.payload;

      draftState.attributes.fields = fieldsByName;
      draftState.attributes.dataId = id;

      break;
    }
    case FETCH_ATTRIBUTES_LAYOUT: {
      const { elements } = action.payload;

      draftState.attributes.elements = elements;

      break;
    }

    case DELETE_ATTRIBUTES: {
      draftState.attributes = {
        fields: {},
        elements: [],
        dataId: null,
      };

      break;
    }

    case PATCH_ATTRIBUTES: {
      const { data } = action.payload;
      const preparedData = parseToDisplay(data);

      if (preparedData) {
        const current = original(draftState.attributes);
        const newFields = {};

        Object.keys(preparedData).map((key) => {
          newFields[key] = {
            ...current.fields[key],
            ...preparedData[key],
          };
        });

        draftState.attributes.fields = {
          ...current.fields,
          ...newFields,
        };
      }

      break;
    }

    case SET_ATTRIBUTES_DATA: {
      const { field, value } = action.payload;

      draftState.attributes.fields[field].value = value;

      break;
    }

    case FETCH_QUICKINPUT_DATA: {
      const { data, id } = action.payload;

      draftState.quickInput.data = data;
      draftState.quickInput.id = id;

      break;
    }

    case FETCH_QUICKINPUT_LAYOUT: {
      const { layout } = action.payload;

      draftState.quickInput.layout = layout;

      break;
    }

    case SET_QUICKINPUT_DATA: {
      const { fieldData } = action.payload;
      const currentData = original(draftState.quickInput.data);

      Object.keys(fieldData).map((fieldName) => {
        draftState.quickInput.data[fieldName] = {
          ...currentData[fieldName],
          ...fieldData[fieldName],
        };
      });

      break;
    }

    case PATCH_QUICKINPUT_PENDING:
      draftState.quickInput.inProgress = true;

      break;

    case PATCH_QUICKINPUT_DONE:
      draftState.quickInput.inProgress = false;

      break;

    case DELETE_QUICKINPUT:
      draftState.quickInput = {
        layout: null,
        data: null,
        id: null,
        inProgress: false,
      };

      break;
  }
}, initialState);

export default reducer;
