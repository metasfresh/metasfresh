import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { useParams } from 'react-router-dom';

import DailyNav from './DailyNav';
import View from './View';
import { RootStoreContext } from '../models/Store';

interface RouteParams {
  productId?: string;
}

const ProductScreen: React.FunctionComponent = observer(() => {
  const { productId } = useParams<RouteParams>();
  const store = useContext(RootStoreContext);
  const products = getSnapshot(store.dailyProducts.products);
  const product = products.find((prod) => prod.productId === productId);

  useEffect(() => {
    store.navigation.setTopViewName('Actual Product');
  }, [store]);

  return (
    <View>
      <div>
        <DailyNav isStatic={true} />
        <div className="mt-1 p-4">
          <p className="subtitle">{product.productName}</p>
        </div>
      </div>
    </View>
  );
});

export default ProductScreen;
