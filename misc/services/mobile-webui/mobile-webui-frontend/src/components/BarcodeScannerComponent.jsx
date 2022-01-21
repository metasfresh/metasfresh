import PropTypes from 'prop-types';
import React, { useEffect, useRef } from 'react';
import { BrowserMultiFormatReader, BarcodeFormat } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';

const READER_HINTS = new Map().set(DecodeHintType.POSSIBLE_FORMATS, [
  BarcodeFormat.QR_CODE,
  BarcodeFormat.CODE_128,
  BarcodeFormat.ITF,
]);

const READER_OPTIONS = {
  delayBetweenScanSuccess: 2000,
  delayBetweenScanAttempts: 600,
};

const BarcodeScannerComponent = ({ validateScannedBarcode, onBarcodeScanned }) => {
  const video = useRef();
  const mountedRef = useRef(true);

  useEffect(() => {
    mountedRef.current = true;

    const codeReader = new BrowserMultiFormatReader(READER_HINTS, READER_OPTIONS);
    codeReader.decodeFromVideoDevice(undefined, video.current, (result, error, controls) => {
      if (mountedRef.current === false) {
        controls.stop();
      } else if (typeof result !== 'undefined') {
        const scannedBarcode = result.text;
        if (!validateScannedBarcode || validateScannedBarcode({ scannedBarcode })) {
          controls.stop();
          onBarcodeScanned({ scannedBarcode });
        }
      }
    });

    return function cleanup() {
      mountedRef.current = false;
    };
  });

  return <video ref={video} width="100%" height="100%" />;
};

BarcodeScannerComponent.propTypes = {
  //
  // Props:
  onBarcodeScanned: PropTypes.func.isRequired,
  validateScannedBarcode: PropTypes.func,
};

export default BarcodeScannerComponent;
