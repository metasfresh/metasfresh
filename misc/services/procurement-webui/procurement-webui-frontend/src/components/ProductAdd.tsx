/**
 * ProductAdd - main component for listing the products that follow to be added
 */
import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';
import ProductAddList from './ProductAddList';

const ProductAdd: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <section className="section">
          <ProductAddList />
        </section>
      </div>
    </View>
  );
};

export default ProductAdd;
