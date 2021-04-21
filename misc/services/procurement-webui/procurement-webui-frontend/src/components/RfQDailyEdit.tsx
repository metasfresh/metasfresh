import React, { ReactElement } from 'react';
import { getSnapshot } from 'mobx-state-tree';
import { observer, inject } from 'mobx-react';
import { withRouter, RouteComponentProps } from 'react-router';

import { translate } from '../utils/translate';
import { formDate, prettyDate } from '../utils/date';
import View from './View';

import { RootInstance } from '../models/Store';

interface MatchParams {
  rfqId?: string;
  targetDate: string;
}

interface Props extends RouteComponentProps<MatchParams> {
  store?: RootInstance;
}

@inject('store')
@observer
class RfQDailyEdit extends React.Component<Props> {
  private qtyInput = React.createRef<HTMLInputElement>();
  private isUnmounting = false;

  componentDidMount(): void {
    document.addEventListener('focusout', this.handleFocusOut);

    const { store, match } = this.props;
    const { targetDate } = match.params;
    const { lang } = store.i18n;
    const currentDay = targetDate ? targetDate : store.app.currentDay;
    const qtyEditorCaption = translate('RfQView.QtyEditor.caption');

    store.navigation.setViewNames(
      qtyEditorCaption.replace('{0}', prettyDate({ lang, date: formDate({ currentDay: new Date(currentDay) }) }))
    );
  }

  componentWillUnmount(): void {
    document.removeEventListener('focusout', this.handleFocusOut);
  }

  saveQty = (newQty: number): void => {
    const { store, match } = this.props;
    const { rfqId, targetDate } = match.params;
    const currentDay = targetDate ? targetDate : store.app.currentDay;

    store.rfqs.updateRfQ({
      quantities: [{ date: currentDay, qtyPromised: newQty }],
      rfqId,
    });
  };

  handleFocusOut = (): void => {
    const { store, history } = this.props;
    const { navigation } = store;

    if (!this.isUnmounting) {
      this.isUnmounting = true;

      this.qtyInput.current.blur();
      navigation.removeViewFromHistory();
      history.goBack();
    }
  };

  render(): ReactElement {
    const { store, match } = this.props;
    const { rfqId, targetDate } = match.params;
    const rfQs = getSnapshot(store.rfqs);
    const { quotations } = rfQs;
    const rfq = quotations.find((rfqItem) => rfqItem.rfqId === rfqId);
    const quantity = rfq.quantities.find((qItem) => qItem.date === targetDate);
    const qtyPromised = quantity.qtyPromised.toString();
    const currentDay = targetDate ? targetDate : store.app.currentDay;

    return (
      <View>
        <div>
          <div className="mt-5 p-4">
            <div className="columns is-mobile">
              <div className="column is-11">
                <input
                  className="product-input"
                  type="number"
                  ref={this.qtyInput}
                  step="1"
                  onKeyUp={(e) => {
                    if (e.key === 'Enter') {
                      this.handleFocusOut();
                    }
                  }}
                  onBlur={(e) => {
                    this.saveQty(parseFloat(e.target.value));
                  }}
                  onTouchStart={() => {
                    this.qtyInput.current.focus();
                    this.qtyInput.current.select();
                  }}
                  value={qtyPromised.length > 1 ? qtyPromised.replace(/^0+/, '') : qtyPromised}
                  onChange={(e) => {
                    let updatedQty = parseInt(e.target.value, 10);
                    updatedQty = isNaN(updatedQty) ? 0 : updatedQty;
                    store.rfqs.updateRfQ({
                      quantities: [{ date: currentDay, qtyPromised: updatedQty }],
                      rfqId,
                    });
                  }}
                />
              </div>
              {/* The arrows */}
              <div className="columns pt-4 green-color">
                <div
                  className="column is-6"
                  onClick={() => {
                    this.saveQty(parseInt(this.qtyInput.current.value, 10) + 1);
                  }}
                >
                  <i className="fas fa-2x fa-arrow-up"></i>
                </div>
                <div
                  className="column is-6"
                  onClick={() => {
                    const currentQty = parseInt(this.qtyInput.current.value, 10);
                    currentQty > 0 && this.saveQty(currentQty - 1);
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
  }
}

export default withRouter(RfQDailyEdit);
