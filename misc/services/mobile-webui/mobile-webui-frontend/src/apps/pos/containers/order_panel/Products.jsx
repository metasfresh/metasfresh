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
  const newOrderLineDispatcher = useNewOrderLineDispatcher({ currentOrder });
  const products = useProducts({
    onBarcodeResult: (product) => newOrderLineDispatcher.initialize(product),
  });

  const order_uuid = !currentOrder.isLoading ? currentOrder.uuid : null;
  const isEnabled = !!order_uuid && !currentOrder.isProcessing;

  const onProductButtonClick = (product) => {
    if (!isEnabled) return;
    newOrderLineDispatcher.initialize(product);
  };

  const setProductToAddWeight = ({ catchWeight }) => {
    newOrderLineDispatcher.setCatchWeight(catchWeight);
  };

  return (
    <div className="products-container">
      {newOrderLineDispatcher.isCatchWeightRequiredButNotSet && (
        <GetCatchWeightModal
          catchWeightUomSymbol={newOrderLineDispatcher.catchWeightUomSymbol}
          onOk={setProductToAddWeight}
          onCancel={() => newOrderLineDispatcher.clear()}
        />
      )}
      <ProductSearchBar
        queryString={products.queryString}
        onQueryStringChanged={products.setQueryString}
        isEnabled={isEnabled && !newOrderLineDispatcher.isCatchWeightRequiredButNotSet}
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

const extractProductPriceUom = (product) => {
  return product.catchWeightUomSymbol ? product.catchWeightUomSymbol : product.uomSymbol;
};

const useNewOrderLineDispatcher = ({ currentOrder }) => {
  const [orderLineToAdd, setOrderLineToAdd] = useState(null);

  const isCatchWeightRequiredButNotSet =
    orderLineToAdd && orderLineToAdd.catchWeightUomId != null && !orderLineToAdd.catchWeight;

  useEffect(() => {
    if (!orderLineToAdd) return;

    const isValid = !isCatchWeightRequiredButNotSet;
    if (!isValid) return;

    currentOrder.addOrderLine(orderLineToAdd);
    setOrderLineToAdd(null);
  }, [orderLineToAdd]);

  return {
    clear: () => setOrderLineToAdd(null),
    initialize: (product) => {
      const { id: productId, name: productName, ...otherProductFields } = product;
      setOrderLineToAdd({
        productId,
        productName,
        ...otherProductFields,
      });
    },
    isCatchWeightRequiredButNotSet,
    catchWeightUomSymbol: orderLineToAdd?.catchWeightUomSymbol,
    setCatchWeight: (catchWeight) => setOrderLineToAdd((orderLine) => ({ ...orderLine, catchWeight })),
  };
};

export default Products;
