import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';
import { parseGraiArrayFromRawInput } from '../utils/grai';
import { traceLogWarn } from '../utils/ui_trace';
import { useKeyboardBarcodeReader } from '../hooks/useKeyboardBarcodeReader';
import BarcodeScannerComponent from './BarcodeScannerComponent';
import ButtonWithIndicator from './buttons/ButtonWithIndicator';
import YesNoDialog from './dialogs/YesNoDialog';

import '../assets/GRAIScreen.scss';

/**
 * Unified GRAI mass-capture panel shared by the in-picking inline capture and the HU-Manager
 * ScanGRAI action. Renders the barcode scanner (the operator scans a GRAI, or types one via the
 * scanner component's own manual-entry mode — never a bespoke input, per the mobile-webui
 * manual-entry rule), the scanned/expected count, an optional "Clear all" with confirm, and the
 * deduped chip list (assigned first, then any extras).
 *
 * The panel is fully controlled: the caller owns the GRAI list state and supplies the primary
 * action button(s) (Save / Send / Undo) via `children`. i18n keys for the count line and the
 * clear-all control are passed in so each surface keeps its own namespace.
 */
const GraiCapturePanel = ({
  assignedGrais,
  extraGrais,
  graiCodes,
  expectedCount,
  loading,
  countKey,
  countExtraKey,
  clearAllButtonKey,
  clearAllConfirmKey,
  onAddGrais,
  onRemoveGrai,
  onClearAll,
  children,
}) => {
  const [showClearAllDialog, setShowClearAllDialog] = useState(false);

  const onBarcodeString = useCallback(
    (barcodeString) => {
      const parsed = parseGraiArrayFromRawInput(barcodeString);
      if (parsed.length > 0) {
        onAddGrais(parsed);
      } else {
        traceLogWarn('Scanned barcode is not a valid GRAI', { scannedBarcode: barcodeString });
      }
    },
    [onAddGrais]
  );

  const onResolvedResult = useCallback(
    (resolvedResult) => onBarcodeString(resolvedResult.scannedBarcode),
    [onBarcodeString]
  );

  // Capture rapid RFID bursts (a whole batch of GRAIs scanned back-to-back) reliably, mirroring the
  // HU-Manager GRAIScreen. The reader inside BarcodeScannerComponent rebuilds its onReadDone every
  // render, so its window-keydown effect re-subscribes during a fast burst and drops codes after the
  // first; this direct reader keeps a STABLE onReadDone (onBarcodeString, memoised on the stable
  // onAddGrais), so it stays subscribed for the whole burst and every code lands. Codes that both
  // readers happen to deliver are deduped by onAddGrais (mergeGraiArrays), so there is no double-count.
  useKeyboardBarcodeReader({ onReadDone: onBarcodeString, disabled: loading });

  return (
    <div className="grai-screen">
      {!loading && <BarcodeScannerComponent onResolvedResult={onResolvedResult} />}

      <div className="grai-count" data-testid="grai-count">
        {trl(countKey, { scanned: assignedGrais.length, total: expectedCount })}
        {extraGrais.length > 0 && (
          <span className="grai-count-extra" data-testid="grai-count-extra">
            {' '}
            {trl(countExtraKey, { extra: extraGrais.length })}
          </span>
        )}
      </div>

      <div className="pt-3 section">
        {children}
        <ButtonWithIndicator
          captionKey={clearAllButtonKey}
          testId="grai-clear-all-button"
          disabled={loading || !graiCodes.length}
          onClick={() => setShowClearAllDialog(true)}
        />
      </div>

      <div className="grai-chip-list">
        {assignedGrais.map((grai) => (
          <GraiChip key={grai} grai={grai} onRemove={() => onRemoveGrai(grai)} />
        ))}
        {extraGrais.map((grai) => (
          <GraiChip key={grai} grai={grai} extra onRemove={() => onRemoveGrai(grai)} />
        ))}
      </div>

      {showClearAllDialog && (
        <YesNoDialog
          promptQuestion={trl(clearAllConfirmKey, { count: graiCodes.length })}
          onYes={() => {
            onClearAll();
            setShowClearAllDialog(false);
          }}
          onNo={() => setShowClearAllDialog(false)}
        />
      )}
    </div>
  );
};

GraiCapturePanel.propTypes = {
  assignedGrais: PropTypes.arrayOf(PropTypes.string).isRequired,
  extraGrais: PropTypes.arrayOf(PropTypes.string).isRequired,
  graiCodes: PropTypes.arrayOf(PropTypes.string).isRequired,
  expectedCount: PropTypes.number,
  loading: PropTypes.bool,
  countKey: PropTypes.string.isRequired,
  countExtraKey: PropTypes.string.isRequired,
  clearAllButtonKey: PropTypes.string.isRequired,
  clearAllConfirmKey: PropTypes.string.isRequired,
  onAddGrais: PropTypes.func.isRequired,
  onRemoveGrai: PropTypes.func.isRequired,
  onClearAll: PropTypes.func.isRequired,
  children: PropTypes.node,
};

const GraiChip = ({ grai, extra = false, onRemove }) => {
  return (
    <div
      className={extra ? 'grai-chip grai-chip--extra' : 'grai-chip'}
      data-testid={extra ? 'grai-chip-extra' : 'grai-chip'}
    >
      <span className="grai-chip-text" title={grai}>
        {grai}
      </span>
      <button
        type="button"
        className="grai-chip-remove"
        data-testid={extra ? 'grai-chip-extra-remove' : 'grai-chip-remove'}
        onClick={onRemove}
        aria-label="Remove"
      >
        &times;
      </button>
    </div>
  );
};

GraiChip.propTypes = {
  grai: PropTypes.string.isRequired,
  extra: PropTypes.bool,
  onRemove: PropTypes.func.isRequired,
};

export default GraiCapturePanel;
