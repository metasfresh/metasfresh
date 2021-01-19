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

  const currentDay = store.app.currentDay;
  const currentCaption = store.app.dayCaption;

  useEffect(() => {
    store.navigation.setTopViewName(product.productName);
  }, [store]);

  return (
    <View>
      <div>
        <DailyNav isStatic={true} staticDay={currentDay} staticCaption={currentCaption} />
        <div className="mt-5 p-4">
          <input
            className="product-input"
            type="number"
            value={product.qty}
            onChange={(e) => {
              store.dailyProducts.updateProductQty(product.productId, e.target.value);
            }}
          />
        </div>
      </div>
    </View>
  );
});

export default ProductScreen;
