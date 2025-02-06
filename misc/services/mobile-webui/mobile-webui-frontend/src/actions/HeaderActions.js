import { HEADER_PUSH_ENTRY } from '../constants/HeaderActionTypes';

export function pushHeaderEntry({
  location,
  caption,
  values = [],
  userInstructions,
  isHomeStop,
  homeIconClassName,
  backLocation,
}) {
  return {
    type: HEADER_PUSH_ENTRY,
    payload: { location, caption, values, userInstructions, isHomeStop, homeIconClassName, backLocation },
  };
}
