import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';

import { translate } from '../utils/translate';
import { RootInstance } from '../models/Store';

import View from './View';
import RFQ from './RfQ';

interface Props {
  store?: RootInstance;
}

@inject('store')
@observer
class RfQList extends React.Component<Props> {
  componentDidMount(): void {
    const { store } = this.props;

    store.navigation.setViewNames(translate('RfQsListView.caption'));
    store.rfqs.fetchRFQs();
  }

  render(): ReactElement {
    const { store } = this.props;
    const items = getSnapshot(store.rfqs.quotations);

    return (
      <View>
        <section className="section">
          <div className="mt-4">
            {items &&
              items.map((rfq) => {
                return (
                  <RFQ
                    key={rfq.rfqId}
                    id={rfq.rfqId}
                    quantityPromised={rfq.qtyPromised}
                    dateStart={rfq.dateStart}
                    dateEnd={rfq.dateEnd}
                    name={rfq.productName}
                  />
                );
              })}
          </div>
        </section>
      </View>
    );
  }
}

export default RfQList;
