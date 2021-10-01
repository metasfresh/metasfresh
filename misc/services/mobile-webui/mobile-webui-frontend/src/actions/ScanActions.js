import { START_SCANNING, STOP_SCANNING } from '../constants/ActionTypes';

/**
 * @method startScanning
 * @summary sets the scan flag to `true` in the store indicating that we started the scan
 */
export function startScanning() {
  return {
    type: START_SCANNING,
    payload: { active: true },
  };
}

/**
 * @method stopScanning
 * @summary sets the scan flag to `true` in the store indicating that we stopped the scan
 */
export function stopScanning() {
  return {
    type: STOP_SCANNING,
    payload: { active: false },
  };
}
