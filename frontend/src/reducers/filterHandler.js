import * as types from '../constants/FilterTypes';

export const initialState = {
  clearAll: false,
};

export default function filters(state = initialState, action) {
  switch (action.type) {
    case types.SET_CLEAR_ALL_FILTER: {
      return { ...state, clearAll: action.payload };
    }

    default: {
      return state;
    }
  }
}
