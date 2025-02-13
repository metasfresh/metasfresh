import { HEADER_PUSH_ENTRY, HEADER_UPDATE_ENTRY } from '../constants/HeaderActionTypes';

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

export function updateHeaderEntry({
  location,
  caption,
  values = [],
  userInstructions,
  isHomeStop,
  homeIconClassName,
  backLocation,
}) {
  return {
    type: HEADER_UPDATE_ENTRY,
    payload: { location, caption, values, userInstructions, isHomeStop, homeIconClassName, backLocation },
  };
}
