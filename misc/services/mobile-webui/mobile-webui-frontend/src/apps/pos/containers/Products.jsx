import React from 'react';
import { useProducts } from '../api/products';
import ProductButton from './ProductButton';
import Spinner from '../../../components/Spinner';

const Products = () => {
  const { isProductsLoading, products } = useProducts();

  return (
    <div className="products">
      {isProductsLoading && <Spinner />}
      {products.map((product) => (
        <ProductButton key={product.id} productId={product.id} name={product.name} />
      ))}
    </div>
  );
};

export default Products;
