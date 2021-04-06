import { produce, original } from 'immer';

import {
  FETCH_ATTRIBUTES_DATA,
  FETCH_ATTRIBUTES_LAYOUT,
  DELETE_ATTRIBUTES,
  PATCH_ATTRIBUTES,
  SET_ATTRIBUTES_DATA,
} from '../constants/ActionTypes';
import { parseToDisplay } from '../utils/documentListHelper';

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

      return;
    }
    case FETCH_ATTRIBUTES_LAYOUT: {
      const { elements } = action.payload;

      draftState.attributes.elements = elements;

      return;
    }

    case DELETE_ATTRIBUTES: {
      draftState.attributes = {
        fields: {},
        elements: [],
        dataId: null,
      };

      return;
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

      return;
    }

    case SET_ATTRIBUTES_DATA: {
      const { field, value } = action.payload;

      draftState.attributes.fields[field].value = value;

      return;
    }
  }
}, initialState);

export default reducer;
