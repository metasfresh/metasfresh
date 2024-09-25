import React, { useEffect, useState } from 'react';
import { useProducts } from '../../api/products';
import ProductButton from './ProductButton';
import Spinner from '../../../../components/Spinner';
import { useCurrentOrder } from '../../actions';
import ProductSearchBar from './ProductSearchBar';
import GetCatchWeightModal from './GetCatchWeightModal';
import './Products.scss';

const Products = () => {
  const currentOrder = useCurrentOrder();
  const products = useProducts({
    onBarcodeResult: (product) => addOrderLine(product),
  });
  const [productToAdd, setProductToAdd] = useState(null);

  const order_uuid = !currentOrder.isLoading ? currentOrder.uuid : null;
  const isEnabled = !!order_uuid && !currentOrder.isProcessing;
  const isDisplayGetWeightModal = isWeightRequiredButNotSet(productToAdd);

  useEffect(() => {
    if (!productToAdd) return;
    const isValid = !isWeightRequiredButNotSet(productToAdd);
    if (!isValid) return;

    currentOrder.addOrderLine(productToAdd);
    setProductToAdd(null);
  }, [productToAdd]);

  const onProductButtonClick = (product) => {
    if (!isEnabled) return;
    addOrderLine(product);
  };

  const addOrderLine = (product) => {
    setProductToAdd({ ...product });
  };
  const setProductToAddWeight = ({ catchWeight }) => {
    setProductToAdd((productToAdd) => ({ ...productToAdd, catchWeight }));
  };

  return (
    <div className="products-container">
      {isDisplayGetWeightModal && (
        <GetCatchWeightModal
          catchWeightUomSymbol={productToAdd?.catchWeightUomSymbol}
          onOk={setProductToAddWeight}
          onCancel={() => setProductToAdd(null)}
        />
      )}
      <ProductSearchBar
        queryString={products.queryString}
        onQueryStringChanged={products.setQueryString}
        isEnabled={isEnabled && !isDisplayGetWeightModal}
      />
      <div className="products">
        {products.list.map((product) => (
          <ProductButton
            key={product.id}
            name={product.name}
            price={product.price}
            currencySymbol={product.currencySymbol}
            uomSymbol={extractProductPriceUom(product)}
            disabled={!isEnabled}
            onClick={() => onProductButtonClick(product)}
          />
        ))}
        {products.isLoading && <Spinner />}
      </div>
    </div>
  );
};

const isWeightRequiredButNotSet = (product) => {
  return product && product.catchWeightUomId != null && !product.catchWeight;
};

const extractProductPriceUom = (product) => {
  return product.catchWeightUomSymbol ? product.catchWeightUomSymbol : product.uomSymbol;
};

export default Products;
