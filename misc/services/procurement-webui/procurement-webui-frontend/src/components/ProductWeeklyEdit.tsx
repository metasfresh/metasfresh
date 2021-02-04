import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { useParams, useHistory } from 'react-router-dom';
import DailyNav from './DailyNav';
import View from './View';
import { RootStoreContext } from '../models/Store';
import { formDate, prettyDate } from '../utils/date';
interface RouteParams {
  productId?: string;
  targetDay?: string;
  targetDayCaption?: string;
}

const ProductWeeklyEdit: React.FunctionComponent = observer(() => {
  const { productId, targetDay, targetDayCaption } = useParams<RouteParams>();
  const store = useContext(RootStoreContext);
  const products = getSnapshot(store.weeklyProducts.products);
  const product = products.find((prod) => prod.productId === productId);
  const dailyQuantities = product.dailyQuantities;
  const dailyQtyItem = dailyQuantities.find((item) => item.date === targetDay);

  const currentDay = targetDay ? targetDay : store.app.currentDay;
  const currentCaption = targetDayCaption ? targetDayCaption : store.app.dayCaption;
  const qtyInput = React.createRef<HTMLInputElement>();
  const history = useHistory();
  const { navigation } = store;

  const selectAndFocus = () => {
    if (qtyInput.current) {
      qtyInput.current.focus();
      qtyInput.current.select();
    }
  };

  useEffect(() => {
    store.navigation.setViewNames(product.productName);
    selectAndFocus();
  }, [store]);

  const saveQty = (newQty: number) => {
    store
      .postDailyReport({
        items: [
          {
            date: currentDay,
            productId: product.productId,
            qty: newQty,
          },
        ],
      })
      .then(() => {
        store.fetchDailyReport(currentDay);
        store.fetchWeeklyReport(store.app.week);
        store.app.getUserSession();
      });
  };

  const { lang } = store.i18n;
  const dailyQty = dailyQtyItem.qty.toString();

  return (
    <View>
      <div>
        <DailyNav
          isStatic={true}
          staticDay={prettyDate({ lang, date: formDate({ currentDay: new Date(currentDay) }) })}
          staticCaption={currentCaption}
        />
        <div className="mt-5 p-4">
          <div className="columns is-mobile">
            <div className="column is-11">
              <input
                className="product-input"
                type="number"
                ref={qtyInput}
                onKeyUp={(e) => {
                  if (e.key === 'Enter') {
                    qtyInput.current.blur();
                    navigation.removeViewFromHistory();
                    history.goBack();
                  }
                }}
                step="1"
                value={dailyQty.length > 1 ? dailyQty.replace(/^0+/, '') : dailyQty}
                onChange={(e) => {
                  let updatedQty = parseInt(e.target.value, 10);
                  updatedQty = isNaN(updatedQty) ? 0 : updatedQty;
                  store.weeklyProducts.updateProductQty(product.productId, `${updatedQty}`, currentDay);
                }}
                onBlur={(e) => saveQty(parseInt(e.target.value, 10))}
              />
            </div>
            {/* The arrows */}
            <div className="columns pt-4 green-color">
              <div
                className="column is-6"
                onClick={() => {
                  saveQty(parseInt(qtyInput.current.value, 10) + 1);
                }}
              >
                <i className="fas fa-2x fa-arrow-up"></i>
              </div>
              <div
                className="column is-6"
                onClick={() => {
                  const currentQty = parseInt(qtyInput.current.value, 10);
                  currentQty > 0 && saveQty(currentQty - 1);
                }}
              >
                <i className="fas fa-2x fa-arrow-down"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </View>
  );
});

export default ProductWeeklyEdit;
