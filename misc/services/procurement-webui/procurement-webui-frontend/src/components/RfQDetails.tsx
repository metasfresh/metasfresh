import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { useParams, useHistory } from 'react-router-dom';
import { getSnapshot } from 'mobx-state-tree';
import { RootStoreContext } from '../models/Store';
import { translate } from '../utils/translate';
import View from './View';

interface RouteParams {
  quotationId?: string;
}

const RfQDetails: React.FunctionComponent = observer(() => {
  const { quotationId } = useParams<RouteParams>();
  const store = useContext(RootStoreContext);
  const history = useHistory();
  const quotations = getSnapshot(store.rfqs.quotations);
  const quotation = quotations.find((qItem) => qItem.rfqId === quotationId);

  useEffect(() => {
    store.navigation.setBottomViewName(translate('RfQsListView.caption'));
  }, [store]);

  return (
    <View>
      <section className="container pl-4 pr-4">
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">{translate('RfQView.ProductName')}</div>
          <div className="column pt-1 pb-1">{quotation.productName}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">{translate('RfQView.ProductPackingInfo')}</div>
          <div className="column pt-1 pb-1">{quotation.packingInfo}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">{translate('RfQView.DateStart')}</div>
          <div className="column pt-1 pb-1">{quotation.dateStart}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">{translate('RfQView.DateEnd')}</div>
          <div className="column pt-1 pb-1">{quotation.dateEnd}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">{translate('RfQView.DateClose')}</div>
          <div className="column pt-1 pb-1">{quotation.dateClose}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">{translate('RfQView.QtyRequested')}</div>
          <div className="column pt-1 pb-1">{quotation.qtyRequested}</div>
        </div>
        <div className="columns is-mobile bt-1 bb-1">
          <div className="column pt-1 pb-1">{translate('RfQView.QtyPromised')}</div>
          <div className="column pt-1 pb-1">{quotation.qtyPromised}</div>
        </div>
      </section>
      {/* Price section */}
      <section className="mt-4 pl-4 pr-4">
        <div className="columns is-mobile">
          <div className="column is-12 pb-1 has-text-weight-bold">{translate('RfQView.Price')}</div>
        </div>
        <div
          className="columns is-mobile box p-1"
          onClick={() => history.push({ pathname: `/rfq/price/${quotation.rfqId}` })}
        >
          <div className="column is-6">{translate('RfQView.Price')}</div>
          <div className="column is-3">{quotation.priceRendered}</div>
          <div className="column is-3 green-color">{quotation.confirmedByUser && <i className="fas fa-check"></i>}</div>
        </div>
      </section>
      {/* Dialy quantities section */}
      <section className="mt-4 pl-4 pr-4">
        <div className="columns is-mobile">
          <div className="column is-12 pb-1 has-text-weight-bold">{translate('RfQView.DailyQuantities')}</div>
        </div>
        {quotation.quantities &&
          quotation.quantities.map((qItem) => (
            <div key={qItem.date} className="columns is-mobile box p-1">
              <div className="column is-6">{qItem.date}</div>
              <div className="column is-3">{qItem.qtyPromised}</div>
              <div className="column is-3 green-color">{qItem.confirmedByUser && <i className="fas fa-check"></i>}</div>
            </div>
          ))}
      </section>
    </View>
  );
});

export default RfQDetails;
