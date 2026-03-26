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
  const { graiCodes, tuCount, loading, addGrais, removeGrai, clearAllGrais } = useGrais({ huId: handlingUnitInfo?.id });
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
        {trl('huManager.action.scanGRAI.count', { scanned: graiCodes.length, total: tuCount })}
      </div>

      <div className="grai-chip-list">
        {graiCodes.map((grai) => (
          <GraiChip key={grai} grai={grai} onRemove={() => removeGrai(grai)} />
        ))}
      </div>

      {graiCodes.length > 0 && (
        <div className="grai-clear-all">
          <button
            className="grai-clear-all-button"
            data-testid="grai-clear-all-button"
            onClick={() => setShowClearAllDialog(true)}
          >
            {trl('huManager.action.scanGRAI.clearAll.buttonCaption')}
          </button>
        </div>
      )}

      {showClearAllDialog && (
        <YesNoDialog
          promptQuestion={trl('huManager.action.scanGRAI.clearAll.confirmQuestion', { count: graiCodes.length })}
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

const GraiChip = ({ grai, onRemove }) => {
  const parts = grai.split('.');
  const displayText = parts.length === 3 ? `...${parts[2]}` : grai;

  return (
    <div className="grai-chip" data-testid="grai-chip">
      <span className="grai-chip-text" title={grai}>
        {displayText}
      </span>
      <button className="grai-chip-remove" data-testid="grai-chip-remove" onClick={onRemove} aria-label="Remove">
        &times;
      </button>
    </div>
  );
};

GraiChip.propTypes = {
  grai: PropTypes.string.isRequired,
  onRemove: PropTypes.func.isRequired,
};

export default GRAIScreen;
