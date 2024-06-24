import { HEADER_PUSH_ENTRY } from '../constants/HeaderActionTypes';

export function pushHeaderEntry({ location, caption, values = [], userInstructions, isHomeStop, homeIconClassName }) {
  return {
    type: HEADER_PUSH_ENTRY,
    payload: { location, caption, values, userInstructions, isHomeStop, homeIconClassName },
  };
}
