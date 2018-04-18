import { handleAction } from 'redux-actions';

const initialState = {
  files: [],
};

export default handleAction(
  'ADD-PLUGINS',
  (state, action) => {
    return {
      ...state,
      files: [...state.files, ...action.payload],
    };
  },
  initialState
);
