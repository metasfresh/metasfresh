import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import { trl } from '../../utils/translations';
import { BarcodeFormat, BrowserMultiFormatReader } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';
import { toastError } from '../../utils/toast';

const READER_HINTS = new Map().set(DecodeHintType.POSSIBLE_FORMATS, [
  BarcodeFormat.QR_CODE,
  BarcodeFormat.CODE_128,
  BarcodeFormat.ITF,
]);

const READER_OPTIONS = {
  delayBetweenScanSuccess: 2000,
  delayBetweenScanAttempts: 600,
};

const CameraModePanel = ({ isProcessing, onBarcodeScanned, onCancel }) => {
  const videoRef = useRef();
  const cameraControlsRef = useRef(null);

  useEffect(() => {
    let cancelled = false;

    const startCamera = async () => {
      const codeReader = new BrowserMultiFormatReader(READER_HINTS, READER_OPTIONS);
      try {
        // TEMP DIAGNOSTIC (flaky case 3 — DO NOT MERGE): the catch below swallows the real
        // camera-start exception into a generic toast, hiding why the panel unmounts in CI.
        // eslint-disable-next-line no-console
        console.log('[case3-diag] startCamera begin; videoRef present =', !!videoRef.current);
        const controls = await codeReader.decodeFromVideoDevice(undefined, videoRef.current, (result, error, ctrl) => {
          if (cancelled) {
            ctrl.stop();
            return;
          }
          if (typeof result !== 'undefined') {
            onBarcodeScanned({ scannedBarcode: result.text });
          }
        });
        // eslint-disable-next-line no-console
        console.log('[case3-diag] decodeFromVideoDevice RESOLVED; cancelled =', cancelled);
        if (cancelled) {
          controls.stop();
          return;
        }
        cameraControlsRef.current = controls;
      } catch (err) {
        // TEMP DIAGNOSTIC (flaky case 3 — DO NOT MERGE): surface the real throw before it is swallowed.
        // eslint-disable-next-line no-console
        console.error('[case3-diag] decodeFromVideoDevice THREW name=', err && err.name,
          '| message=', err && err.message, '| stack=', err && err.stack);
        if (cancelled) return;
        toastError({
          plainMessage: trl('components.BarcodeScannerComponent.cameraError'),
        });
        onCancel();
      }
    };

    startCamera();

    return () => {
      cancelled = true;
      if (cameraControlsRef.current) {
        cameraControlsRef.current.stop();
        cameraControlsRef.current = null;
      }
      // Also stop any lingering MediaStream tracks on the video element.
      if (videoRef.current && videoRef.current.srcObject) {
        const stream = videoRef.current.srcObject;
        stream.getTracks().forEach((track) => track.stop());
        videoRef.current.srcObject = null;
      }
    };
  }, []);

  useEffect(
    () => {
      videoRef?.current?.scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'end' });
    } /* no deps, call it on each render */
  );

  // Wrapper is always present so the panel reserves the same vertical slot whether or not the
  // <video> is currently mounted (during isProcessing). Keeps the footer anchored — no jump.
  return (
    <div className="camera-mode-panel">
      {!isProcessing && <video key="video" ref={videoRef} width="100%" height="100%" />}
    </div>
  );
};

CameraModePanel.propTypes = {
  isProcessing: PropTypes.bool,
  onBarcodeScanned: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default CameraModePanel;
