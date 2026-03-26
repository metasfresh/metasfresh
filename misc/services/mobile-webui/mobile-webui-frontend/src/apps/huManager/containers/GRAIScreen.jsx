import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';

import { HUInfoComponent } from '../components/HUInfoComponent';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huManagerLocation } from '../routes';
import { useKeyboardBarcodeReader } from '../../../hooks/useKeyboardBarcodeReader';
import { parseGraiArrayFromRawInput } from '../../../utils/grai';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import YesNoDialog from '../../../components/dialogs/YesNoDialog';
import { useGrais } from '../hooks/useGrais';
import { traceLogWarn } from '../../../utils/ui_trace';

import '../../../assets/GRAIScreen.scss';

const GRAIScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'GRAIScreen',
    captionKey: 'huManager.action.scanGRAI.windowName',
    back: huManagerLocation,
  });

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));
  const {
    graiCodes,
    assignedGrais,
    extraGrais,
    tuCount,
    loading,
    dirty,
    sending,
    addGrais,
    removeGrai,
    clearAllGrais,
    sendToBackend,
    loadFromBackend,
  } = useGrais({ huId: handlingUnitInfo?.id });
  const [showClearAllDialog, setShowClearAllDialog] = useState(false);

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

  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
    }
  }, []);

  useKeyboardBarcodeReader({
    onReadDone: onBarcodeString,
    disabled: !handlingUnitInfo || loading,
  });

  if (!handlingUnitInfo) return null;

  return (
    <div className="grai-screen">
      <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />

      <div className="grai-count" data-testid="grai-count">
        {trl('huManager.action.scanGRAI.count', { scanned: assignedGrais.length, total: tuCount })}
        {extraGrais.length > 0 && (
          <span className="grai-count-extra" data-testid="grai-count-extra">
            {' '}
            {trl('huManager.action.scanGRAI.countExtra', { extra: extraGrais.length })}
          </span>
        )}
      </div>

      <div className="pt-3 section">
        <ButtonWithIndicator
          captionKey="huManager.action.scanGRAI.send.buttonCaption"
          testId="grai-send-button"
          disabled={!dirty || sending || extraGrais.length > 0}
          onClick={sendToBackend}
          additionalCssClass="action-button"
        />
        <ButtonWithIndicator
          captionKey="huManager.action.scanGRAI.clearAll.buttonCaption"
          testId="grai-clear-all-button"
          disabled={!graiCodes.length}
          onClick={() => setShowClearAllDialog(true)}
        />
        <ButtonWithIndicator
          captionKey="huManager.action.scanGRAI.undo.buttonCaption"
          testId="grai-undo-button"
          disabled={!dirty}
          onClick={loadFromBackend}
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
          promptQuestion={trl('huManager.action.scanGRAI.clearAll.confirmQuestion', {
            count: graiCodes.length,
          })}
          onYes={() => {
            clearAllGrais();
            setShowClearAllDialog(false);
          }}
          onNo={() => setShowClearAllDialog(false)}
        />
      )}

      {!loading && <BarcodeScannerComponent onResolvedResult={onResolvedResult} continuousRunning={true} />}
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

export default GRAIScreen;
