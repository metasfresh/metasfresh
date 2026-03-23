import React, { useCallback, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import * as api from '../api';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';

import { HUInfoComponent } from '../components/HUInfoComponent';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huManagerLocation } from '../routes';
import { useNfcReader } from '../../../hooks/useNfcReader';
import { useKeyboardBarcodeReader } from '../../../hooks/useKeyboardBarcodeReader';
import { parseGraiFromNfc, parseGraiFromGs1Barcode, parseGraiFromRawInput } from '../../../utils/grai';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';

import '../../../assets/GRAIScreen.scss';

const GRAIScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'GRAIScreen',
    captionKey: 'huManager.action.scanGRAI.windowName',
    back: huManagerLocation,
  });

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));
  const [graiCodes, setGraiCodes] = useState([]);
  const [tuCount, setTuCount] = useState(0);
  const [loading, setLoading] = useState(true);

  // Redirect if no HU loaded
  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }

    api
      .getPackingMaterials(handlingUnitInfo.id)
      .then((response) => {
        setGraiCodes(response.graiCodes || []);
        setTuCount(response.tuCount || 0);
        setLoading(false);
      })
      .catch((axiosError) => {
        toastError({ axiosError });
        setLoading(false);
      });
  }, []);

  // NFC scanning
  const { isNfcSupported, isScanning, lastRead } = useNfcReader();

  // Process NFC reads
  useEffect(() => {
    if (lastRead && handlingUnitInfo) {
      const grai = parseGraiFromNfc(lastRead);
      if (grai) {
        addGrai(grai);
      }
    }
  }, [lastRead]);

  const addGrai = useCallback(
    (grai) => {
      setGraiCodes((prev) => {
        if (prev.includes(grai)) return prev; // duplicate — ignore
        const updated = [...prev, grai];
        // Fire-and-forget save to backend
        if (handlingUnitInfo) {
          api.setPackingMaterials(handlingUnitInfo.id, updated).catch((axiosError) => toastError({ axiosError }));
        }
        return updated;
      });
    },
    [handlingUnitInfo]
  );

  const removeGrai = useCallback(
    (graiToRemove) => {
      setGraiCodes((prev) => {
        const updated = prev.filter((g) => g !== graiToRemove);
        if (handlingUnitInfo) {
          api.setPackingMaterials(handlingUnitInfo.id, updated).catch((axiosError) => toastError({ axiosError }));
        }
        return updated;
      });
    },
    [handlingUnitInfo]
  );

  const onBarcodeString = useCallback(
    (barcodeString) => {
      const grai = parseGraiFromGs1Barcode(barcodeString) || parseGraiFromRawInput(barcodeString);
      if (grai) {
        addGrai(grai);
      }
    },
    [addGrai]
  );

  // Handler for BarcodeScannerComponent (receives object with { scannedBarcode })
  const onResolvedResult = useCallback(
    (resolvedResult) => {
      onBarcodeString(resolvedResult.scannedBarcode);
    },
    [onBarcodeString]
  );

  // Keyboard barcode reader (hardware scanner fallback, receives raw string)
  useKeyboardBarcodeReader({
    onReadDone: onBarcodeString,
    disabled: !handlingUnitInfo || loading,
  });

  if (!handlingUnitInfo) return null;

  return (
    <div className="grai-screen">
      <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />

      {/* NFC indicator */}
      <NfcIndicator isSupported={isNfcSupported} isScanning={isScanning} />

      {/* Count */}
      <div className="grai-count" data-testid="grai-count">
        {trl('huManager.action.scanGRAI.count', { scanned: graiCodes.length, total: tuCount })}
      </div>

      {/* GRAI chip list */}
      <div className="grai-chip-list">
        {graiCodes.map((grai) => (
          <GraiChip key={grai} grai={grai} onRemove={() => removeGrai(grai)} />
        ))}
      </div>

      {/* Camera barcode scanner fallback */}
      {!loading && <BarcodeScannerComponent onResolvedResult={onResolvedResult} continuousRunning={true} />}
    </div>
  );
};

const NfcIndicator = ({ isSupported, isScanning }) => {
  if (isSupported && isScanning) {
    return (
      <div className="nfc-indicator active">
        <span className="nfc-dot" />
        {trl('huManager.action.scanGRAI.nfcActive')}
      </div>
    );
  }

  return <div className="nfc-indicator inactive">{trl('huManager.action.scanGRAI.nfcNotAvailable')}</div>;
};

const GraiChip = ({ grai, onRemove }) => {
  // Show last segment (serial) as the main display
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

export default GRAIScreen;
