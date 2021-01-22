import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { useParams } from 'react-router-dom';
import View from './View';
import { RootStoreContext } from '../models/Store';
import Prognose from './Prognose';
interface RouteParams {
  productId?: string;
}

const ProductWeeklyScreen: React.FunctionComponent = observer(() => {
  const { productId } = useParams<RouteParams>();
  const store = useContext(RootStoreContext);
  const product = store.weeklyProducts.findProductById(productId);
  const { dailyQuantities } = getSnapshot(product);

  useEffect(() => {
    store.navigation.setViewNames(`${product.productName} (${store.app.weekCaption})`);
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
      <Prognose nextWeek={store.weeklyProducts.nextWeekCaption} trend={product.nextWeekTrend} />
      <div>
        <section className="section pt-0">
          {dailyQuantities.length &&
            dailyQuantities.map((dItem) => {
              return (
                <div key={dItem.dayCaption} className="product">
                  <div className="box">
                    <div className="columns is-mobile">
                      <div className="column is-8">
                        <div className="columns">
                          <div className="column is-size-4-mobile no-p">{dItem.dayCaption}</div>
                          <div className="column is-size-7 no-p">{dItem.date}</div>
                        </div>
                      </div>
                      <div className="column is-4 no-p">
                        <div className="columns is-mobile">
                          <div className="column mt-2 is-size-2-mobile no-p has-text-right">{dItem.qty}</div>
                          <div className="column green-check is-hidden-mobile">
                            <i className="fas fa-check"></i>
                          </div>
                          <div className="column mt-4 green-check is-hidden-desktop is-hidden-tablet is-4">
                            {/* Trend listing in here */}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
        </section>
      </div>
    </View>
  );
});

export default ProductWeeklyScreen;
