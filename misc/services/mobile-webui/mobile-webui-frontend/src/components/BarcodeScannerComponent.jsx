import PropTypes from 'prop-types';
import React, { useEffect, useRef } from 'react';
import { BrowserMultiFormatReader, BarcodeFormat } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';
import { toastError } from '../utils/toast';
import { trl } from '../utils/translations';
import { useBooleanSetting, useSetting } from '../reducers/settings';

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
  const videoRef = useRef();
  const inputTextRef = useRef();

  const validateScannedBarcodeAndForward = ({ scannedBarcode, controls = null }) => {
    //console.log('Resolving scanned barcode', { scannedBarcode, resolveScannedBarcode, handleResolvedResult });
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

  const handleResolvedResult = (resolvedResult, controls = null) => {
    // console.log('Got resolvedResult', resolvedResult);
    if (resolvedResult.error) {
      toastError({ plainMessage: resolvedResult.error });
    } else {
      controls?.stop();
      onResolvedResult(resolvedResult);
    }
  };

  const mountedRef = useRef(true);
  useEffect(() => {
    mountedRef.current = true;

    const codeReader = new BrowserMultiFormatReader(READER_HINTS, READER_OPTIONS);
    codeReader.decodeFromVideoDevice(undefined, videoRef.current, (result, error, controls) => {
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

  const triggerOnChangeIfLengthGreaterThan = useSetting('barcodeScanner.inputText.triggerOnChangeIfLengthGreaterThan');
  const handleInputTextChanged = (e) => {
    const inputElement = e.target;
    const scannedBarcode = inputElement.value;

    if (
      scannedBarcode &&
      triggerOnChangeIfLengthGreaterThan &&
      scannedBarcode.length >= triggerOnChangeIfLengthGreaterThan
    ) {
      inputElement.select();
      validateScannedBarcodeAndForward({ scannedBarcode });
    }
  };

  const handleInputTextKeyPress = (e) => {
    if (e.key === 'Enter') {
      const inputElement = e.target;
      const scannedBarcode = inputElement.value;

      inputElement.select();
      validateScannedBarcodeAndForward({ scannedBarcode });
    }
  };

  const handleInputTextFocus = () => {
    inputTextRef?.current?.select();
  };

  const handleInputTextBlur = () => {
    setTimeout(() => {
      inputTextRef?.current?.focus();
    }, 2000);
  };

  useEffect(() => {
    inputTextRef?.current?.focus();
  });

  const isShowInputText = useBooleanSetting('barcodeScanner.showInputText');
  return (
    <div className="barcode-scanner">
      <video ref={videoRef} width="100%" height="100%" />
      {isShowInputText && (
        <input
          ref={inputTextRef}
          className="input-text"
          type="text"
          placeholder={trl('components.BarcodeScannerComponent.scanTextPlaceholder')}
          onFocus={handleInputTextFocus}
          onBlur={handleInputTextBlur}
          onChange={handleInputTextChanged}
          onKeyPress={handleInputTextKeyPress}
        />
      )}
    </div>
  );
};

BarcodeScannerComponent.propTypes = {
  //
  // Props:
  resolveScannedBarcode: PropTypes.func,
  onResolvedResult: PropTypes.func.isRequired,
};

export default BarcodeScannerComponent;
