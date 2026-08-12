import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import { trl } from '../../utils/translations';
import ButtonWithIndicator from '../buttons/ButtonWithIndicator';
import { MODE, PRIORITY } from './useBarcodeScannerModes';

// "Back to scanner" target resolution — moved here from BarcodeScannerFooter because in
// MANUAL mode the footer is empty (so the keyboard doesn't overlap any actionable button)
// and this button needs to sit inside the panel, under Submit. HARDWARE wins over CAMERA.
const SCANNER_MODES = PRIORITY.filter((m) => m === MODE.HARDWARE || m === MODE.CAMERA);
const BACK_TO_SCANNER_CAPTION_KEY = {
  [MODE.HARDWARE]: 'components.BarcodeScannerComponent.useHardwareScanner',
  [MODE.CAMERA]: 'components.BarcodeScannerComponent.scanWithCamera',
};
const BACK_TO_SCANNER_ICON = {
  [MODE.HARDWARE]: 'fa-barcode',
  [MODE.CAMERA]: 'fa-camera',
};

/*
 * Focus policy — DELIBERATE NON-DECISION on blur-refocus.
 *
 * We focus the visible manual <input> in exactly TWO cases:
 *   (1) Mount — when MANUAL becomes the active mode, autofocus so the user can
 *       type immediately without an extra tap.
 *   (2) After every submission (success OR error) — once isProcessing flips
 *       back false, refocus (.focus() on success, .select() on error so the
 *       rejected text is highlighted for re-edit). Implemented via
 *       prevProcessingRef below: .focus() inside onSuccess/onError would be a
 *       no-op because the input is still `disabled` at that moment.
 *
 * We DO NOT auto-refocus on every `onBlur`. Reasons:
 *   - Fights the mobile virtual keyboard — if the user intentionally dismisses
 *     it (back-button, tap-outside), immediate refocus would reopen it.
 *   - Fights modals / dialogs / toasts (e.g. GetQuantityDialog, YesNo,
 *     ErrorToast) — those legitimately steal focus and must keep it; an
 *     auto-refocus loop would yank focus back and break expectErrorToast tests.
 *   - The realistic "lost focus" cases on a mobile scanner are already covered:
 *     Submit (case 2 above), mode switch (panel unmounts), screen change.
 *
 * If we ever need stray-tap recovery (e.g. CT60 with gloves accidentally
 * tapping empty regions), the right pattern is a DELAYED + GATED refocus,
 * mirroring HardwareModePanel's blur handler: setTimeout 1-2s, then refocus
 * only if `document.activeElement` is body/null (i.e. focus is genuinely
 * nowhere — not on a dialog/button/toast). Do NOT add a synchronous refocus
 * inside onBlur — see reasons above.
 */
const ManualModePanel = ({ isProcessing, onBarcodeScanned, enabledModes, onModeSelected }) => {
  // Resolve the back-to-scanner target: the highest-priority enabled scanner mode
  // (HARDWARE > CAMERA). Undefined when no scanner mode is enabled → button hidden.
  const backToScannerTarget = SCANNER_MODES.find((m) => enabledModes?.[m]);
  const manualInputRef = useRef();
  // Tracks whether the most recent submission failed (so the post-submit focus effect can
  // .select() the rejected text for re-edit instead of just .focus()ing an empty field).
  const lastSubmitErroredRef = useRef(false);
  // Tracks the previous render's isProcessing so we can detect the true→false transition
  // (= submission just finished, success or error) and return focus to the input.
  const prevProcessingRef = useRef(false);

  // Case (1) from the focus policy above: autofocus on mount (mode just became MANUAL).
  useEffect(() => {
    manualInputRef?.current?.focus();
  }, []);

  // Case (2) from the focus policy above: re-focus after every submission, regardless of
  // outcome. The input is `disabled` while isProcessing=true, so .focus() inside
  // onSuccess/onError would be a no-op. Instead we wait for the disabled→enabled flip via
  // the prev-processing tracker.
  useEffect(() => {
    if (prevProcessingRef.current && !isProcessing) {
      if (lastSubmitErroredRef.current) {
        manualInputRef?.current?.select();
        lastSubmitErroredRef.current = false;
      } else {
        manualInputRef?.current?.focus();
      }
    }
    prevProcessingRef.current = isProcessing;
  });

  const handleManualSubmit = () => {
    if (isProcessing) return;

    const scannedBarcode = manualInputRef?.current?.value?.trim();
    if (!scannedBarcode) return;

    onBarcodeScanned({
      scannedBarcode,
      onSuccess: () => {
        if (manualInputRef?.current) {
          manualInputRef.current.value = '';
        }
      },
      onError: () => {
        lastSubmitErroredRef.current = true;
      },
    });
  };

  const handleManualKeyUp = (e) => {
    if (isProcessing) return;
    if (e.key === 'Enter') {
      handleManualSubmit();
    }
  };

  return (
    <div className="manual-mode-panel">
      <input
        disabled={isProcessing}
        ref={manualInputRef}
        className="input-text manual-input"
        type="text"
        inputMode="text"
        autoComplete="off"
        autoCorrect="off"
        autoCapitalize="none"
        spellCheck="false"
        placeholder={trl('components.BarcodeScannerComponent.manualInputPlaceholder')}
        onKeyUp={handleManualKeyUp}
        data-testid="manual-entry-input"
      />
      <ButtonWithIndicator
        captionKey="components.BarcodeScannerComponent.manualInputSubmit"
        typeFASIconName="fa-check"
        additionalCssClass="barcode-scanner-btn"
        disabled={isProcessing}
        onClick={handleManualSubmit}
        testId="manual-entry-submit"
      />
      {backToScannerTarget && (
        <ButtonWithIndicator
          captionKey={BACK_TO_SCANNER_CAPTION_KEY[backToScannerTarget]}
          typeFASIconName={BACK_TO_SCANNER_ICON[backToScannerTarget]}
          additionalCssClass="barcode-scanner-btn"
          disabled={isProcessing}
          onClick={() => onModeSelected(backToScannerTarget)}
          testId="barcode-scanner-back-to-scanner"
        />
      )}
    </div>
  );
};
ManualModePanel.propTypes = {
  isProcessing: PropTypes.bool,
  onBarcodeScanned: PropTypes.func.isRequired,
  enabledModes: PropTypes.shape({
    hardware: PropTypes.bool,
    camera: PropTypes.bool,
    manual: PropTypes.bool,
  }).isRequired,
  onModeSelected: PropTypes.func.isRequired,
};

export default ManualModePanel;
