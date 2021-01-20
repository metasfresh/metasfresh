/**
 * ProductAdd - main component for listing the products that follow to be added
 */

import React, { FunctionComponent, ReactElement } from 'react';
import { translate } from '../utils/translate';
import View from './View';
import ProductAddList from './ProductAddList';

const ProductAdd: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <h1 className="title p-4 pl-5">{translate('SelectProductView.caption')}</h1>
        <section className="section">
          <ProductAddList />
        </section>
      </div>
    </View>
  );
};

export default ProductAdd;
