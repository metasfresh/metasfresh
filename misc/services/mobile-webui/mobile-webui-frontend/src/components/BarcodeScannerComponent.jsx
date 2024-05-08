import PropTypes from 'prop-types';
import React, { useEffect, useRef } from 'react';
import { BrowserMultiFormatReader, BarcodeFormat } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';
import { toastError } from '../utils/toast';
import { trl } from '../utils/translations';

const READER_HINTS = new Map().set(DecodeHintType.POSSIBLE_FORMATS, [
  BarcodeFormat.QR_CODE,
  BarcodeFormat.CODE_128,
  BarcodeFormat.ITF,
]);

const READER_OPTIONS = {
  delayBetweenScanSuccess: 2000,
  delayBetweenScanAttempts: 600,
};

const BarcodeScannerComponent = ({ resolveScannedBarcode, onResolvedResult }) => {
  const video = useRef();
  const mountedRef = useRef(true);

  const validateScannedBarcodeAndForward = ({ scannedBarcode, controls }) => {
    //console.log('Resolving scanned barcode', { scannedBarcode, resolveScannedBarcode });
    if (resolveScannedBarcode) {
      let resolvedResultPromise;
      try {
        resolvedResultPromise = resolveScannedBarcode({ scannedBarcode });
        //console.log('Got resolvedResultPromise', resolvedResultPromise);
      } catch (error) {
        console.error('Got unhandled error while trying to resolve the scanned barcode', error);
        handleResolvedResult({ error: trl('error.PleaseTryAgain') }, controls);
        return;
      }

      if (resolvedResultPromise) {
        Promise.resolve(resolvedResultPromise)
          .then((result) => handleResolvedResult(result, controls))
          .catch((axiosError) => toastError({ axiosError }));
      }
    } else {
      handleResolvedResult({ scannedBarcode, error: null }, controls);
    }
  };

  const handleResolvedResult = (resolvedResult, controls) => {
    // console.log('Got resolvedResult', resolvedResult);
    if (resolvedResult.error) {
      toastError({ plainMessage: resolvedResult.error });
    } else {
      controls.stop();
      onResolvedResult(resolvedResult);
    }
  };

  useEffect(() => {
    mountedRef.current = true;

    const codeReader = new BrowserMultiFormatReader(READER_HINTS, READER_OPTIONS);
    codeReader.decodeFromVideoDevice(undefined, video.current, (result, error, controls) => {
      if (mountedRef.current === false) {
        controls.stop();
      } else if (typeof result !== 'undefined') {
        validateScannedBarcodeAndForward({ scannedBarcode: result.text, controls });
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
  resolveScannedBarcode: PropTypes.func,
  onResolvedResult: PropTypes.func.isRequired,
};

export default BarcodeScannerComponent;
