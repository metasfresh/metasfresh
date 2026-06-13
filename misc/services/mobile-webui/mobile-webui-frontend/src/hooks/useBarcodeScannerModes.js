import { useBooleanSetting, useSetting } from '../reducers/settings';

export const MODE = { HARDWARE: 'hardware', CAMERA: 'camera', MANUAL: 'manual' };
const PRIORITY = [MODE.HARDWARE, MODE.CAMERA, MODE.MANUAL];

export const useBarcodeScannerModes = ({ invisible = false } = {}) => {
  const hardware = useBooleanSetting('barcodeScanner.mode.hardware.enabled', true);
  const camera = useBooleanSetting('barcodeScanner.mode.camera.enabled', true);
  const manual = useBooleanSetting('barcodeScanner.mode.manual.enabled', true);
  const configuredDefault = useSetting('barcodeScanner.defaultMode');

  if (invisible) {
    return { enabledModes: { hardware: true, camera: false, manual: false }, defaultMode: MODE.HARDWARE };
  }

  let enabled = { hardware, camera, manual };
  if (!PRIORITY.some((m) => enabled[m])) {
    enabled = { ...enabled, manual: true }; // fail-safe: operator never locked out
  }

  const defaultMode =
    (configuredDefault && enabled[configuredDefault] && configuredDefault) ||
    PRIORITY.find((m) => enabled[m]) ||
    MODE.MANUAL;

  return { enabledModes: enabled, defaultMode };
};
