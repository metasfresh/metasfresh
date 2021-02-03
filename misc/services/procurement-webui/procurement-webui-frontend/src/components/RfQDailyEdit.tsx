import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { useParams } from 'react-router-dom';
import { translate } from '../utils/translate';
import { formDate, prettyDate } from '../utils/date';
import View from './View';
import { RootStoreContext } from '../models/Store';

interface RouteParams {
  rfqId?: string;
  targetDate: string;
}

const RfQDailyEdit: React.FunctionComponent = observer(() => {
  const { rfqId, targetDate } = useParams<RouteParams>();
  const store = useContext(RootStoreContext);
  const rfQs = getSnapshot(store.rfqs);
  const { quotations } = rfQs;
  const rfq = quotations.find((rfqItem) => rfqItem.rfqId === rfqId);
  const quantity = rfq.quantities.find((qItem) => qItem.date === targetDate);
  const currentDay = targetDate ? targetDate : store.app.currentDay;
  const qtyInput = React.createRef<HTMLInputElement>();
  const { lang } = store.i18n;

  const selectAndFocus = () => {
    if (qtyInput.current) {
      qtyInput.current.focus();
      qtyInput.current.select();
    }
  };

  useEffect(() => {
    const qtyEditorCaption = translate('RfQView.QtyEditor.caption');
    store.navigation.setViewNames(
      qtyEditorCaption.replace('{0}', prettyDate({ lang, date: formDate({ currentDay: new Date(currentDay) }) }))
    );
    selectAndFocus();
  }, [store]);

  const saveQty = (newQty: number) => {
    store.rfqs.updateRfQ({
      quantities: [{ date: currentDay, qtyPromised: newQty }],
      rfqId,
    });
  };

  const qtyPromised = quantity.qtyPromised.toString();

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
                value={qtyPromised.length > 1 ? qtyPromised.replace(/^0+/, '') : qtyPromised}
                onChange={(e) => {
                  let updatedQty = parseInt(e.target.value, 10);
                  updatedQty = isNaN(updatedQty) ? 0 : updatedQty;
                  store.rfqs.updateRfQ({
                    quantities: [{ date: currentDay, qtyPromised: updatedQty }],
                    rfqId,
                  });
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

export default RfQDailyEdit;
