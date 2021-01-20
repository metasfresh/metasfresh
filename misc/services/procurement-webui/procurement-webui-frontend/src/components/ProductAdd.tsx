/**
 * ProductAdd - main component for listing the products that follow to be added
 */
import React, { useEffect, useContext } from 'react';
import { translate } from '../utils/translate';
import View from './View';
import ProductAddList from './ProductAddList';
import { observer } from 'mobx-react';
import { RootStoreContext } from '../models/Store';

const ProductAdd: React.FunctionComponent = observer(() => {
  const store = useContext(RootStoreContext);

  useEffect(() => {
    store.productSelection.fetchSelectionProducts();
  }, [store]);

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
});

export default ProductAdd;
