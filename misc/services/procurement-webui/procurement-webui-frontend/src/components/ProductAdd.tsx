/**
 * ProductAdd - main component for listing the products that follow to be added
 */

import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';

const ProductAdd: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <h1 className="title p-4">Products</h1>
        <div className="custom-top-offset p-4">
          <p className="subtitle">products listing</p>
        </div>
      </div>
    </View>
  );
};

export default ProductAdd;
