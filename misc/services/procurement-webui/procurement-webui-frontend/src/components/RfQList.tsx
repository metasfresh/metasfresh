import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';
import { formDate, prettyDate } from '../utils/date';
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
    const { lang } = store.i18n;
    return (
      <View>
        <section className="section">
          <div className="mt-4">
            {items
              ? items.map((rfq) => {
                  return (
                    <RFQ
                      key={rfq.rfqId}
                      id={rfq.rfqId}
                      quantityPromised={rfq.qtyPromised}
                      dateStart={prettyDate({ lang, date: formDate({ currentDay: new Date(rfq.dateStart) }) })}
                      dateEnd={prettyDate({ lang, date: formDate({ currentDay: new Date(rfq.dateEnd) }) })}
                      name={rfq.productName}
                    />
                  );
                })
              : null}
          </div>
        </section>
      </View>
    );
  }
}

export default RfQList;
