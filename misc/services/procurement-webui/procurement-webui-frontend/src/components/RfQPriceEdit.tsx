import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { useParams, useHistory } from 'react-router-dom';
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
    store.navigation.setViewNames(translate('RfQView.Price'));
    selectAndFocus();
  }, [store]);

  const saveQty = (newPrice: number) => {
    store.rfqs.updateRfQ({ price: newPrice, rfqId }).then(() => selectAndFocus());
  };

  const rfqPrice = rfq.price;

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
                defaultValue={rfqPrice}
                onKeyUp={(e) => {
                  if (e.key === 'Enter') {
                    qtyInput.current.blur();
                    navigation.removeViewFromHistory();
                    history.goBack();
                  }
                }}
                onChange={(e) => {
                  let updatedPrice = parseFloat(e.target.value);
                  updatedPrice = isNaN(updatedPrice) ? 0 : updatedPrice;
                  store.rfqs.updateRfQ({ price: updatedPrice, rfqId });
                }}
                onBlur={(e) => saveQty(parseFloat(e.target.value))}
              />
            </div>
            {/* The arrows */}
            <div className="columns pt-4 green-color">
              <div
                className="column is-6"
                onClick={() => {
                  const targetEl =
                    qtyInput.current.value !== qtyInput.current.defaultValue
                      ? qtyInput.current.value
                      : qtyInput.current.defaultValue;
                  const newValue = parseFloat(targetEl) + 1;
                  qtyInput.current.defaultValue = newValue.toFixed(2).toString();
                  qtyInput.current.value = qtyInput.current.defaultValue;
                  saveQty(parseFloat(qtyInput.current.defaultValue));
                }}
              >
                <i className="fas fa-2x fa-arrow-up"></i>
              </div>
              <div
                className="column is-6"
                onClick={() => {
                  const targetEl =
                    qtyInput.current.value !== qtyInput.current.defaultValue
                      ? qtyInput.current.value
                      : qtyInput.current.defaultValue;
                  const newValue = parseFloat(targetEl) - 1;
                  if (newValue > 0) {
                    qtyInput.current.defaultValue = newValue.toFixed(2).toString();
                    qtyInput.current.value = qtyInput.current.defaultValue;
                    const currentQty = parseFloat(qtyInput.current.defaultValue);
                    saveQty(currentQty);
                  }
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
