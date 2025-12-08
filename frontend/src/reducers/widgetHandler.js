import { original, produce } from 'immer';

import {
  DELETE_ATTRIBUTES,
  FETCH_ATTRIBUTES_DATA,
  FETCH_ATTRIBUTES_LAYOUT,
  PATCH_ATTRIBUTES,
  SET_ATTRIBUTES_DATA,
} from '../constants/ActionTypes';
import { parseToDisplay } from '../utils/documentListHelper';

// @VisibleForTesting
export const initialState = {
  attributes: {
    dataId: null,
    fields: {},
    elements: [],
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
  }
}, initialState);

export default reducer;
