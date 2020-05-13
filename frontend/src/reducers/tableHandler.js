import * as types from '../constants/ActionTypes';

export const initialState = {
  activeSort: false,
};

export default function table(state = initialState, action) {
  switch (action.type) {
    case types.SET_ACTIVE_SORT: {
      return { ...state, activeSort: action.payload };
    }

    default: {
      return state;
    }
  }
}
