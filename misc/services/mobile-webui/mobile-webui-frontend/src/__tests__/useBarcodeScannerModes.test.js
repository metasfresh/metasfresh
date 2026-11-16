import React from 'react';
import { render } from '@testing-library/react';
import { useBarcodeScannerModes, MODE } from '../components/BarcodeScanner/useBarcodeScannerModes';
import { useBooleanSetting, useSetting } from '../reducers/settings';

// Mock the settings hooks
jest.mock('../reducers/settings', () => ({
  useBooleanSetting: jest.fn(),
  useSetting: jest.fn(),
}));

/**
 * Helper: configure the mocked settings hooks for a given scenario.
 */
function setupMocks({ hardware = true, camera = true, manual = false, defaultMode = undefined } = {}) {
  useBooleanSetting.mockImplementation((key, defaultValue) => {
    if (key === 'barcodeScanner.mode.hardware.enabled') return hardware;
    if (key === 'barcodeScanner.mode.camera.enabled') return camera;
    if (key === 'barcodeScanner.mode.manual.enabled') return manual;
    return defaultValue;
  });
  useSetting.mockReturnValue(defaultMode);
}

/**
 * Renders the hook by calling it inside a component and capturing the result via a ref.
 */
function renderHookResult(options) {
  const result = { current: null };
  function TestComponent() {
    result.current = useBarcodeScannerModes(options);
    return null;
  }
  render(<TestComponent />);
  return result;
}

describe('useBarcodeScannerModes', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('(a) configured defaultMode is honoured when that mode is enabled', () => {
    setupMocks({ hardware: true, camera: true, manual: true, defaultMode: 'camera' });
    const result = renderHookResult();
    expect(result.current.defaultMode).toBe(MODE.CAMERA);
    expect(result.current.enabledModes.camera).toBe(true);
  });

  it('(b) configured defaultMode is DISABLED → falls back to first enabled by priority hardware→camera→manual', () => {
    // defaultMode=hardware but hardware is disabled; camera is enabled → should fall back to camera
    setupMocks({ hardware: false, camera: true, manual: false, defaultMode: 'hardware' });
    const result = renderHookResult();
    expect(result.current.defaultMode).toBe(MODE.CAMERA);
    expect(result.current.enabledModes.hardware).toBe(false);
  });

  it('(c) none enabled → manual is force-enabled and defaultMode=manual (operator never locked out)', () => {
    setupMocks({ hardware: false, camera: false, manual: false, defaultMode: undefined });
    const result = renderHookResult();
    expect(result.current.enabledModes.manual).toBe(true);
    expect(result.current.defaultMode).toBe(MODE.MANUAL);
  });

  it('(d) invisible:true → only hardware enabled, no camera/manual, defaultMode=hardware', () => {
    setupMocks({ hardware: true, camera: true, manual: true, defaultMode: 'camera' });
    const result = renderHookResult({ invisible: true });
    expect(result.current.enabledModes.hardware).toBe(true);
    expect(result.current.enabledModes.camera).toBe(false);
    expect(result.current.enabledModes.manual).toBe(false);
    expect(result.current.defaultMode).toBe(MODE.HARDWARE);
  });

  it('MODE constants are exported', () => {
    expect(MODE.HARDWARE).toBe('hardware');
    expect(MODE.CAMERA).toBe('camera');
    expect(MODE.MANUAL).toBe('manual');
  });
});
