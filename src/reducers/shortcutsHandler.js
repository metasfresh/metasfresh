import blacklist from '../shortcuts/blacklist';
import keymap from '../shortcuts/keymap';
import {
  SHORTCUT_SUBSCRIBE,
  SHORTCUT_UNSUBSCRIBE,
} from '../constants/ActionTypes';
import { generateHotkeys } from '../components/keyshortcuts';

const initialState = generateHotkeys({ keymap, blacklist });

export default function appHandler(state = initialState, action) {
  switch (action.type) {
    case SHORTCUT_SUBSCRIBE:
      console.log('subscribe action: ', action);
      return {
        ...state,
        [`${action.payload.key}`]: action.payload.handlers,
      };
    case SHORTCUT_UNSUBSCRIBE:
      console.log('unsubscribe action: ', action);
      return {
        ...state,
        [`${action.payload.key}`]: action.payload.handlers,
      };
    default: {
      return state;
    }
  }
}
