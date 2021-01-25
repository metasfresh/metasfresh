import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { useParams } from 'react-router-dom';
import { translate } from '../utils/translate';
import View from './View';
import { RootStoreContext } from '../models/Store';

interface RouteParams {
  rfqId?: string;
}

const ProductWeeklyEdit: React.FunctionComponent = observer(() => {
  const { rfqId } = useParams<RouteParams>();
  const store = useContext(RootStoreContext);
  const rfQs = getSnapshot(store.rfqs);
  const { quotations } = rfQs;
  const rfq = quotations.find((rfqItem) => rfqItem.rfqId === rfqId);

  //   const currentDay = targetDay ? targetDay : store.app.currentDay;
  //   const currentCaption = targetDayCaption ? targetDayCaption : store.app.dayCaption;

  useEffect(() => {
    store.navigation.setViewNames(translate('RfQView.Price'));
  }, [store]);

  const saveQty = (newQty: number) => {
    console.log('Saving: ', newQty);
    // store
    //   .postDailyReport({
    //     items: [
    //       {
    //         date: currentDay,
    //         productId: product.productId,
    //         qty: newQty,
    //       },
    //     ],
    //   })
    //   .then(() => {
    //     store.fetchDailyReport(currentDay);
    //     store.fetchWeeklyReport(store.app.week);
    //     store.app.getUserSession();
    //   });
  };

  //

  const qtyInput = React.createRef<HTMLInputElement>();

  return (
    <View>
      <div>
        <div className="mt-5 p-4">
          <div className="columns is-mobile">
            <div className="column is-11">
              <input
                className="product-input"
                type="number"
                ref={qtyInput}
                step="1"
                value={rfq.price}
                onChange={(e) => {
                  // store.weeklyProducts.updateProductQty(product.productId, e.target.value, currentDay);
                }}
                onBlur={(e) => saveQty(parseInt(e.target.value))}
              />
            </div>
            {/* The arrows */}
            <div className="columns pt-4 green-color">
              <div
                className="column is-6"
                onClick={() => {
                  saveQty(parseInt(qtyInput.current.value) + 1);
                }}
              >
                <i className="fas fa-2x fa-arrow-up"></i>
              </div>
              <div
                className="column is-6"
                onClick={() => {
                  const currentQty = parseInt(qtyInput.current.value);
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
