import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../utils/translations';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { useKeyboardBarcodeReader } from '../../../hooks/useKeyboardBarcodeReader';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { parseGraiArrayFromRawInput } from '../../../utils/grai';
import { traceLogWarn } from '../../../utils/ui_trace';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import YesNoDialog from '../../../components/dialogs/YesNoDialog';
import { useCurrentPickingTargetInfo } from '../../../reducers/wfProcesses/picking/useCurrentPickTarget';
import { usePickingGrais } from '../../../apps/picking/hooks/usePickingGrais';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';

import '../../../assets/GRAIScreen.scss';

/**
 * In-picking GRAI mass-capture screen. Available once the LU picking target is selected
 * (`luPickingTarget.luId` present) when GRAI scanning is required for the job's customer; the
 * backend completion guard remains the authoritative gate on whether GRAIs may actually be saved.
 * Binds to the LU target and captures one GRAI per picked TU via the picking-scoped GRAI endpoints.
 * GRAIs can be captured either by scanning or by typing them on the on-screen keyboard (both paths
 * add to the same deduped list). The save button ("Speichern") is enabled only when exactly
 * N (=tuCount) GRAIs are captured; on save it returns to the picking job screen.
 */
const PickGraiScanScreen = () => {
  const { history, applicationId, wfProcessId, activityId, lineId } = useScreenDefinition({
    screenId: 'PickGraiScanScreen',
    captionKey: 'activities.picking.graiScan.windowName',
    back: getWFProcessScreenLocation,
  });

  const navigation = useMobileNavigation();

  const { luPickingTarget } = useCurrentPickingTargetInfo({ wfProcessId, activityId, lineId });
  const huId = luPickingTarget?.luId;

  const goBackToJob = useCallback(() => {
    navigation.goTo(getWFProcessScreenLocation({ applicationId, wfProcessId }));
  }, [navigation, applicationId, wfProcessId]);

  const { graiCodes, assignedGrais, extraGrais, tuCount, loading, canSave, addGrais, removeGrai, clearAllGrais, save } =
    usePickingGrais({ wfProcessId, huId, onSaved: goBackToJob });

  const [showClearAllDialog, setShowClearAllDialog] = useState(false);
  const [manualInput, setManualInput] = useState('');

  const onBarcodeString = useCallback(
    (barcodeString) => {
      const parsed = parseGraiArrayFromRawInput(barcodeString);
      if (parsed.length > 0) {
        addGrais(parsed);
      } else {
        traceLogWarn('Scanned barcode is not a valid GRAI', { barcodeString });
      }
    },
    [addGrais]
  );

  const onResolvedResult = useCallback(
    (resolvedResult) => {
      onBarcodeString(resolvedResult.scannedBarcode);
    },
    [onBarcodeString]
  );

  // Manual keyboard entry: lets the operator TYPE a GRAI and confirm, in addition to scanning.
  // Feeds the exact same onBarcodeString path as the hardware scanner, so both inputs add to the
  // same deduped list. Scoped to this screen only — the shared BarcodeScannerComponent (hardware
  // scan path) and its DataWedge/inputMode/readOnly logic are left untouched.
  const submitManualInput = useCallback(() => {
    const trimmed = manualInput.trim();
    if (!trimmed) return;
    onBarcodeString(trimmed);
    setManualInput('');
  }, [manualInput, onBarcodeString]);

  useEffect(() => {
    if (!huId) {
      history.goBack();
    }
  }, [huId, history]);

  useKeyboardBarcodeReader({
    onReadDone: onBarcodeString,
    disabled: !huId || loading,
  });

  if (!huId) return null;

  return (
    <div className="grai-screen">
      {!loading && <BarcodeScannerComponent onResolvedResult={onResolvedResult} continuousRunning={true} />}

      <div className="grai-manual-entry field has-addons">
        <div className="control is-expanded">
          <input
            className="input grai-manual-input"
            type="text"
            value={manualInput}
            placeholder={trl('activities.picking.graiScan.manualEntry.placeholder')}
            onChange={(e) => setManualInput(e.target.value)}
            onKeyUp={(e) => {
              if (e.key === 'Enter') submitManualInput();
            }}
            disabled={loading}
            autoComplete="off"
            autoCorrect="off"
            autoCapitalize="none"
            spellCheck="false"
            data-testid="grai-manual-input"
          />
        </div>
        <div className="control">
          <ButtonWithIndicator
            captionKey="activities.picking.graiScan.manualEntry.submit.buttonCaption"
            testId="grai-manual-submit"
            disabled={loading || !manualInput.trim()}
            onClick={submitManualInput}
          />
        </div>
      </div>

      <div className="grai-count" data-testid="grai-count">
        {trl('activities.picking.graiScan.count', { scanned: assignedGrais.length, total: tuCount })}
        {extraGrais.length > 0 && (
          <span className="grai-count-extra" data-testid="grai-count-extra">
            {' '}
            {trl('activities.picking.graiScan.countExtra', { extra: extraGrais.length })}
          </span>
        )}
      </div>

      <div className="pt-3 section">
        <ButtonWithIndicator
          captionKey="activities.picking.graiScan.save.buttonCaption"
          testId="grai-save-button"
          disabled={!canSave}
          onClick={save}
          additionalCssClass="action-button"
        />
        <ButtonWithIndicator
          captionKey="activities.picking.graiScan.clearAll.buttonCaption"
          testId="grai-clear-all-button"
          disabled={!graiCodes.length}
          onClick={() => setShowClearAllDialog(true)}
        />
      </div>

      <div className="grai-chip-list">
        {assignedGrais.map((grai) => (
          <GraiChip key={grai} grai={grai} onRemove={() => removeGrai(grai)} />
        ))}
        {extraGrais.map((grai) => (
          <GraiChip key={grai} grai={grai} extra onRemove={() => removeGrai(grai)} />
        ))}
      </div>

      {showClearAllDialog && (
        <YesNoDialog
          promptQuestion={trl('activities.picking.graiScan.clearAll.confirmQuestion', {
            count: graiCodes.length,
          })}
          onYes={() => {
            clearAllGrais();
            setShowClearAllDialog(false);
          }}
          onNo={() => setShowClearAllDialog(false)}
        />
      )}
    </div>
  );
};

const GraiChip = ({ grai, extra, onRemove }) => {
  return (
    <div
      className={extra ? 'grai-chip grai-chip--extra' : 'grai-chip'}
      data-testid={extra ? 'grai-chip-extra' : 'grai-chip'}
    >
      <span className="grai-chip-text" title={grai}>
        {grai}
      </span>
      <button
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

export default PickGraiScanScreen;
