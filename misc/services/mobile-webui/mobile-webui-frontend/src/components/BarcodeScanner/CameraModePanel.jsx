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
        const controls = await codeReader.decodeFromVideoDevice(undefined, videoRef.current, (result, error, ctrl) => {
          if (cancelled) {
            ctrl.stop();
            return;
          }
          if (typeof result !== 'undefined') {
            onBarcodeScanned({ scannedBarcode: result.text });
          }
        });
        if (cancelled) {
          controls.stop();
          return;
        }
        cameraControlsRef.current = controls;
      } catch (err) {
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
  }, []); // intentional: only restart camera when activeMode changes

  useEffect(
    () => {
      videoRef?.current?.scrollIntoView({ behaviour: 'smooth', block: 'center', inline: 'end' });
    } /* no deps, call it on each render */
  );

  return !isProcessing && <video key="video" ref={videoRef} width="100%" height="100%" />;
};
CameraModePanel.propTypes = {
  isProcessing: PropTypes.bool,
  onBarcodeScanned: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default CameraModePanel;
