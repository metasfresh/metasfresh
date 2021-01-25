import React, { useContext, useEffect } from 'react';
import { observer } from 'mobx-react';
import { useParams } from 'react-router-dom';
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
  const quotations = getSnapshot(store.rfqs.quotations);
  const quotation = quotations.find((qItem) => qItem.rfqId === quotationId);

  useEffect(() => {
    store.navigation.setBottomViewName(translate('RfQsListView.caption'));
  }, [store]);

  return (
    <View>
      <section className="container pl-4 pr-4">
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">Product</div>
          <div className="column pt-1 pb-1">{quotation.productName}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">Packing</div>
          <div className="column pt-1 pb-1">{quotation.packingInfo}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">From</div>
          <div className="column pt-1 pb-1">{quotation.dateStart}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">To</div>
          <div className="column pt-1 pb-1">{quotation.dateEnd}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">Close</div>
          <div className="column pt-1 pb-1">{quotation.dateClose}</div>
        </div>
        <div className="columns is-mobile bt-1">
          <div className="column pt-1 pb-1">Qty requested</div>
          <div className="column pt-1 pb-1">{quotation.qtyRequested}</div>
        </div>
        <div className="columns is-mobile bt-1 bb-1">
          <div className="column pt-1 pb-1">Qty promissed</div>
          <div className="column pt-1 pb-1">{quotation.qtyPromised}</div>
        </div>
      </section>
    </View>
  );
});

export default RfQDetails;
