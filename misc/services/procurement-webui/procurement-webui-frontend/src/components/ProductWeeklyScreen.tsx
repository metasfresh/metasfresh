import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { useParams } from 'react-router-dom';
import View from './View';
import { RootStoreContext } from '../models/Store';
import { useHistory } from 'react-router-dom';
import Prognose from './Prognose';
import { formDate, prettyDate } from '../utils/date';
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

  const prognoseChange = (data: { productId: string; trend: string; week: string }) => {
    store.weeklyProducts.postNextWeekTrend(data).then(() => {
      store.fetchWeeklyReport(data.week);
    });
  };
  const { lang } = store.i18n;
  const history = useHistory();
  const handleClick = (productId: string, date: string, dayCaption: string): void => {
    history.push({
      pathname: `/weekly/edit/${productId}/${date}/${dayCaption}`,
    });
  };

  return (
    <View>
      <Prognose
        nextWeek={store.weeklyProducts.nextWeek}
        nextWeekCaption={store.weeklyProducts.nextWeekCaption}
        trend={product.nextWeekTrend}
        prognoseChange={prognoseChange}
        productId={product.getId}
        currentWeek={store.app.week}
      />
      <div className="week-days">
        <section className="section pt-0">
          {dailyQuantities.length &&
            dailyQuantities.map((dItem) => {
              return (
                <div
                  key={dItem.dayCaption}
                  className="product"
                  onClick={() => handleClick(product.getId, dItem.date, dItem.dayCaption)}
                >
                  <div className="box">
                    <div className="columns is-mobile">
                      <div className="column is-8">
                        <div className="columns">
                          <div className="column is-size-4-mobile no-p">{dItem.dayCaption}</div>
                          <div className="column is-size-7 no-p">
                            {prettyDate({ lang, date: formDate({ currentDay: new Date(dItem.date) }) })}
                          </div>
                        </div>
                      </div>
                      <div className="column is-4 no-p">
                        <div className="columns is-mobile">
                          <div className="column mt-2 is-size-2-mobile no-p has-text-right">{dItem.qty}</div>
                          <div className="column mt-4 green-check is-hidden-desktop is-hidden-tablet is-4">
                            {/* Trend listing in here */}
                            {dItem.confirmedByUser && (
                              <span>
                                <i className="fas fa-check"></i>
                              </span>
                            )}
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
