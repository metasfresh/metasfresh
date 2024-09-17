import React from 'react';
import { useProducts } from '../api/products';
import ProductButton from './ProductButton';
import Spinner from '../../../components/Spinner';
import { useCurrentOrder } from '../actions';

const Products = () => {
  const { isCurrentOrderLoading, currentOrder } = useCurrentOrder();
  const { isProductsLoading, products } = useProducts();

  const order_uuid = !isCurrentOrderLoading ? currentOrder?.uuid : null;

  return (
    <div className="products-container">
      <div className="products">
        {isProductsLoading && <Spinner />}
        {products.map((product) => (
          <ProductButton
            key={product.id}
            productId={product.id}
            name={product.name}
            price={product.price}
            currencySymbol={product.currencySymbol}
            uomId={product.uomId}
            uomSymbol={product.uomSymbol}
            order_uuid={order_uuid}
          />
        ))}
      </div>
    </div>
  );
};

export default Products;
