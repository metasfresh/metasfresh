import PropTypes from 'prop-types';
import React, { useEffect, useRef } from 'react';
import { BarcodeFormat, BrowserMultiFormatReader } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';
import { toastError, toastErrorFromObj } from '../utils/toast';
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
  const scanningStatusRef = useRef({ running: false, done: false });

  const validateScannedBarcodeAndForward = async ({ scannedBarcode, controls = null }) => {
    const scanningStatus = scanningStatusRef.current;
    if (scanningStatus.running || scanningStatus.done) {
      console.log('Ignore scanned barcode because we are already running or done', { scannedBarcode, scanningStatus });
      return;
    }
    scanningStatus.running = true;

    console.log('Resolving scanned barcode', {
      scannedBarcode,
      resolveScannedBarcode,
      onResolvedResult,
      scanningStatus: { ...scanningStatus },
    });

    try {
      let resolvedResult;
      if (resolveScannedBarcode) {
        resolvedResult = await resolveScannedBarcode({ scannedBarcode });
      } else {
        resolvedResult = { scannedBarcode, error: null };
      }
      console.log('Got resolvedResult', resolvedResult);

      if (resolvedResult.error) {
        toastError({ plainMessage: resolvedResult.error });
        scanningStatus.done = false; // not done yet
      } else {
        await onResolvedResult(resolvedResult);

        scanningStatus.done = true;
        controls?.stop();
      }
    } catch (error) {
      toastErrorFromObj(error);
    } finally {
      scanningStatus.running = false;
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
      <video key="video" ref={videoRef} width="100%" height="100%" />
      {isShowInputText && (
        <input
          key="input-text"
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
