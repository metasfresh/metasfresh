import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import BarcodeReader from './BarcodeReader';
import { trl } from '../../../../utils/translations';
import { useBooleanSetting } from '../../../../reducers/settings';
import './ProductSearchBar.scss';

const _ = (key) => trl(`pos.products.searchBar.${key}`);

const ProductSearchBar = ({ queryString, onQueryStringChanged, isEnabled }) => {
  const [isBarcodeScannerDisplayed, setBarcodeScannerDisplayed] = useState(false);
  // Camera scanning is hidden when the device camera is disabled (e.g. handheld hardware-scanner
  // deployments). Shares the global barcodeScanner.mode.camera.enabled switch.
  const isCameraEnabled = useBooleanSetting('barcodeScanner.mode.camera.enabled', true);
  const queryStringRef = useRef();

  useEffect(() => {
    if (!isEnabled) return;
    queryStringRef?.current?.focus();
  }, [isEnabled]);

  // If the camera gets disabled while the scanner overlay is open, close it — otherwise it would
  // stay visible with no button to dismiss it (the toggle button is hidden when the camera is off).
  useEffect(() => {
    if (!isCameraEnabled && isBarcodeScannerDisplayed) {
      setBarcodeScannerDisplayed(false);
    }
  }, [isCameraEnabled, isBarcodeScannerDisplayed]);

  const handleQueryStringFocus = () => {
    queryStringRef?.current?.select();
  };

  const handleQueryStringBlur = () => {
    if (!isEnabled) return;

    // NOTE: timeout shall be small enough to make sure we are not losing the focus,
    // but big enough to allow things like button press to take the focus and accomplish the action
    setTimeout(() => {
      queryStringRef?.current?.focus();
    }, 1000);
  };

  const handleQueryStringChanged = (e) => {
    if (!isEnabled) return;
    onQueryStringChanged(e.target.value);
  };

  const handleScannerButtonClicked = () => {
    setBarcodeScannerDisplayed(!isBarcodeScannerDisplayed);
  };

  const handleBarcodeScanned = ({ scannedBarcode }) => {
    if (!isEnabled) return;
    onQueryStringChanged(scannedBarcode);
  };

  return (
    <div className="searchbar-container">
      <div className="searchbar-line">
        <input
          ref={queryStringRef}
          type="text"
          value={queryString}
          placeholder={_('placeholder')}
          disabled={!isEnabled}
          onFocus={handleQueryStringFocus}
          onBlur={handleQueryStringBlur}
          onChange={handleQueryStringChanged}
        />
        {isCameraEnabled && (
          <button className="button" disabled={!isEnabled} onClick={handleScannerButtonClicked}>
            <i className="fa-solid fa-barcode"></i>
          </button>
        )}
      </div>
      {isCameraEnabled && isBarcodeScannerDisplayed && <BarcodeReader onBarcodeScanned={handleBarcodeScanned} />}
    </div>
  );
};

ProductSearchBar.propTypes = {
  queryString: PropTypes.string,
  onQueryStringChanged: PropTypes.func.isRequired,
  isEnabled: PropTypes.bool.isRequired,
};

export default ProductSearchBar;
