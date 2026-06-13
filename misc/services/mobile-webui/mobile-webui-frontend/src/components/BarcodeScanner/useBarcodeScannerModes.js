import { useBooleanSetting, useSetting } from '../../reducers/settings';

export const MODE = { HARDWARE: 'hardware', CAMERA: 'camera', MANUAL: 'manual' };
// Mode-preference order — used by useBarcodeScannerModes to resolve the default mode and by
// BarcodeScannerFooter to pick the "back to scanner" target from MANUAL (HARDWARE before CAMERA).
export const PRIORITY = [MODE.HARDWARE, MODE.CAMERA, MODE.MANUAL];

export const useBarcodeScannerModes = ({ invisible = false } = {}) => {
  const isHardwareEnabled = useBooleanSetting('barcodeScanner.mode.hardware.enabled', true);
  const isCameraEnabled = useBooleanSetting('barcodeScanner.mode.camera.enabled', false);
  const isManualEnabled = useBooleanSetting('barcodeScanner.mode.manual.enabled', true);
  const configuredDefault = useSetting('barcodeScanner.defaultMode');

  if (invisible) {
    return { enabledModes: { hardware: true, camera: false, manual: false }, defaultMode: MODE.HARDWARE };
  }

  // Object keys MUST stay as MODE string values ('hardware'/'camera'/'manual') —
  // they're looked up dynamically below via `enabled[configuredDefault]` and
  // `enabled[m]` where m is a MODE value.
  let enabled = { hardware: isHardwareEnabled, camera: isCameraEnabled, manual: isManualEnabled };
  if (!PRIORITY.some((m) => enabled[m])) {
    enabled = { ...enabled, manual: true }; // fail-safe: operator never locked out
  }

  const defaultMode =
    (configuredDefault && enabled[configuredDefault] && configuredDefault) ||
    PRIORITY.find((m) => enabled[m]) ||
    MODE.MANUAL;

  return { enabledModes: enabled, defaultMode };
};
