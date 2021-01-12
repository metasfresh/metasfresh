import React, { FunctionComponent, ReactElement } from 'react';
import Product from './Product';

const ProductItems: FunctionComponent = (): ReactElement => {
  return (
    <div className="custom-top-offset">
      <Product />
      <Product />
      <Product />
    </div>
  );
};

export default ProductItems;
