import React, { useEffect, useRef, useState } from 'react';
import { useProducts } from '../../api/products';
import ProductButton from './ProductButton';
import Spinner from '../../../../components/Spinner';
import { useCurrentOrder } from '../../actions';
import './Products.scss';
import { BarcodeFormat, BrowserMultiFormatReader } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';
import PropTypes from 'prop-types';

const Products = () => {
  const currentOrder = useCurrentOrder();
  const queryStringRef = useRef();
  const products = useProducts({
    onBarcodeResult: (product) => currentOrder.addOrderLine(product),
  });
  const [isBarcodeScannerDisplayed, setBarcodeScannerDisplayed] = useState(false);

  const order_uuid = !currentOrder.isLoading ? currentOrder.uuid : null;
  const isEnabled = !!order_uuid && !currentOrder.isProcessing;

  useEffect(() => {
    queryStringRef?.current?.focus();
  });

  const handleQueryStringFocus = () => {
    queryStringRef?.current?.select();
  };

  const handleQueryStringBlur = () => {
    // NOTE: timeout shall be small enough to make sure we are not losing the focus,
    // but big enough to allow things like button press to take the focus and accomplish the action
    setTimeout(() => {
      queryStringRef?.current?.focus();
    }, 1000);
  };

  const onQueryStringChanged = (e) => {
    if (!isEnabled) return;
    products.setQueryString(e.target.value);
  };

  const onButtonClick = (product) => {
    if (!isEnabled) return;
    currentOrder.addOrderLine(product);
  };

  const onBarcodeScanned = ({ scannedBarcode }) => {
    if (!isEnabled) return;
    products.setQueryString(scannedBarcode);
  };

  return (
    <div className="products-container">
      <div className="search-container">
        <div className="search-line">
          <input
            ref={queryStringRef}
            type="text"
            value={products.queryString}
            placeholder="search products..."
            disabled={!isEnabled}
            onFocus={handleQueryStringFocus}
            onBlur={handleQueryStringBlur}
            onChange={onQueryStringChanged}
          />
          <div className="button" onClick={() => setBarcodeScannerDisplayed(!isBarcodeScannerDisplayed)}>
            <i className="fa-solid fa-barcode"></i>
          </div>
        </div>
        {isBarcodeScannerDisplayed && <BarcodeReader onBarcodeScanned={onBarcodeScanned} />}
      </div>
      <div className="products">
        {products.list.map((product) => (
          <ProductButton
            key={product.id}
            name={product.name}
            price={product.price}
            currencySymbol={product.currencySymbol}
            uomSymbol={product.uomSymbol}
            disabled={!isEnabled}
            onClick={() => onButtonClick(product)}
          />
        ))}
        {products.isLoading && <Spinner />}
      </div>
    </div>
  );
};

//
//
//
//
//

const READER_HINTS = new Map().set(DecodeHintType.POSSIBLE_FORMATS, [
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

//
//
//
//
//

export default Products;
