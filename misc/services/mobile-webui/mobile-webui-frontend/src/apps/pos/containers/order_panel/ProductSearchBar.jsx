import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import BarcodeReader from './BarcodeReader';

const ProductSearchBar = ({ queryString, onQueryStringChanged, isEnabled }) => {
  const [isBarcodeScannerDisplayed, setBarcodeScannerDisplayed] = useState(false);
  const queryStringRef = useRef();

  useEffect(() => {
    if (!isEnabled) return;
    queryStringRef?.current?.focus();
  }, [isEnabled]);

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
    <div className="search-container">
      <div className="search-line">
        <input
          ref={queryStringRef}
          type="text"
          value={queryString}
          placeholder="search products..."
          disabled={!isEnabled}
          onFocus={handleQueryStringFocus}
          onBlur={handleQueryStringBlur}
          onChange={handleQueryStringChanged}
        />
        <button className="button" disabled={!isEnabled} onClick={handleScannerButtonClicked}>
          <i className="fa-solid fa-barcode"></i>
        </button>
      </div>
      {isBarcodeScannerDisplayed && <BarcodeReader onBarcodeScanned={handleBarcodeScanned} />}
    </div>
  );
};

ProductSearchBar.propTypes = {
  queryString: PropTypes.string,
  onQueryStringChanged: PropTypes.func.isRequired,
  isEnabled: PropTypes.bool.isRequired,
};

export default ProductSearchBar;
