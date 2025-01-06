import React, { useEffect, useRef } from 'react';
import { BarcodeFormat, BrowserMultiFormatReader } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';
import PropTypes from 'prop-types';

const READER_HINTS = new Map().set(DecodeHintType.POSSIBLE_FORMATS, [
  BarcodeFormat.AZTEC,
  BarcodeFormat.QR_CODE,
  BarcodeFormat.CODE_128,
  BarcodeFormat.ITF,
]);

const READER_OPTIONS = {
  delayBetweenScanSuccess: 2000,
  delayBetweenScanAttempts: 600,
};

const BarcodeReader = ({ onBarcodeScanned }) => {
  const videoRef = useRef();
  const mountedRef = useRef(true);

  useEffect(() => {
    mountedRef.current = true;

    const codeReader = new BrowserMultiFormatReader(READER_HINTS, READER_OPTIONS);
    codeReader.decodeFromVideoDevice(undefined, videoRef.current, (result, error, controls) => {
      if (mountedRef.current === false) {
        controls.stop();
      } else if (typeof result !== 'undefined') {
        fireBarcodeScanned({ scannedBarcode: result.text });
      }
    });

    return function cleanup() {
      mountedRef.current = false;
    };
  }, []);

  useEffect(() => {
    videoRef?.current?.scrollIntoView({ behaviour: 'smooth', block: 'center', inline: 'end' });
  });

  const fireBarcodeScanned = ({ scannedBarcode }) => {
    onBarcodeScanned({ scannedBarcode });
  };

  return <video key="video" ref={videoRef} width="100%" height="100%" />;
};

BarcodeReader.propTypes = {
  onBarcodeScanned: PropTypes.func.isRequired,
};

export default BarcodeReader;
