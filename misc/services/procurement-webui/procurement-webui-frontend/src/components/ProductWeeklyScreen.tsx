import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { useParams } from 'react-router-dom';
import View from './View';
import { RootStoreContext } from '../models/Store';
interface RouteParams {
  productId?: string;
}

const ProductWeeklyScreen: React.FunctionComponent = observer(() => {
  const { productId } = useParams<RouteParams>();
  const store = useContext(RootStoreContext);
  const products = getSnapshot(store.dailyProducts.products);
  const product = products.find((prod) => prod.productId === productId);

  const currentDay = store.app.currentDay;
  //const currentCaption = store.app.dayCaption;

  useEffect(() => {
    store.navigation.setTopViewName(product.productName);
    store.navigation.setBottomViewName(product.productName);
  }, [store]);

  //   const saveQty = (newQty: number) => {
  //     store
  //       .postDailyReport({
  //         items: [
  //           {
  //             date: currentDay,
  //             productId: product.productId,
  //             qty: newQty,
  //           },
  //         ],
  //       })
  //       .then(() => {
  //         store.fetchDailyReport(store.app.currentDay);
  //         store.app.getUserSession();
  //       });
  //   };

  //const qtyInput = React.createRef<HTMLInputElement>();

  return (
    <View>
      <div>Products</div>
    </View>
  );
});

export default ProductWeeklyScreen;
