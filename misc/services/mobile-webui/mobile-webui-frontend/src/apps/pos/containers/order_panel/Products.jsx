import React, { useEffect, useRef } from 'react';
import { useProducts } from '../../api/products';
import ProductButton from './ProductButton';
import Spinner from '../../../../components/Spinner';
import { useCurrentOrder } from '../../actions';
import './Products.scss';

const Products = () => {
  const currentOrder = useCurrentOrder();
  const queryStringRef = useRef();
  const products = useProducts({
    onBarcodeResult: (product) => currentOrder.addOrderLine(product),
  });

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

  return (
    <div className="products-container">
      <div className="search-container">
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
        <div className="button">
          <i className="fa-solid fa-barcode"></i>
        </div>
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

export default Products;
