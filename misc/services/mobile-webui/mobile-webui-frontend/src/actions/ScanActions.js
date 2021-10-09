// FIXME DELETE IT!!!

import { START_SCANNING, STOP_SCANNING } from '../constants/ActionTypes';

export function startScanning() {
  return {
    type: START_SCANNING,
    payload: { active: true },
  };
}

export function stopScanning() {
  return {
    type: STOP_SCANNING,
    payload: { active: false },
  };
}
