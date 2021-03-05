import React, { ReactElement } from 'react';
import { getSnapshot } from 'mobx-state-tree';
import { observer, inject } from 'mobx-react';
import { withRouter, RouteComponentProps } from 'react-router';

import { translate } from '../utils/translate';
import { RootInstance } from '../models/Store';

import View from './View';

interface MatchParams {
  rfqId?: string;
}

interface Props extends RouteComponentProps<MatchParams> {
  store?: RootInstance;
}

@inject('store')
@observer
class RFQPriceEdit extends React.Component<Props> {
  private qtyInput = React.createRef<HTMLInputElement>();
  private isUnmounting = false;

  componentDidMount(): void {
    document.addEventListener('focusout', this.handleFocusOut);

    const { store } = this.props;

    store.navigation.setViewNames(translate('RfQView.Price'));
  }

  componentWillUnmount(): void {
    document.removeEventListener('focusout', this.handleFocusOut);
  }

  savePrice = (newPrice: number): void => {
    const { store, match } = this.props;
    const { rfqId } = match.params;

    store.rfqs.updateRfQ({ price: newPrice, rfqId });
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
    const { rfqId } = match.params;
    const rfQs = getSnapshot(store.rfqs);
    const { quotations } = rfQs;
    const rfq = quotations.find((rfqItem) => rfqItem.rfqId === rfqId);
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
                  ref={this.qtyInput}
                  defaultValue={rfqPrice}
                  onKeyUp={(e) => {
                    if (e.key === 'Enter') {
                      this.handleFocusOut();
                    }
                  }}
                  onBlur={(e) => {
                    this.savePrice(parseFloat(e.target.value));
                  }}
                  onTouchStart={() => {
                    this.qtyInput.current.focus();
                    this.qtyInput.current.select();
                  }}
                  onChange={(e) => {
                    let updatedPrice = parseFloat(e.target.value);
                    updatedPrice = isNaN(updatedPrice) ? 0 : updatedPrice;
                    store.rfqs.updateRfQ({ price: updatedPrice, rfqId });
                  }}
                />
              </div>
              {/* The arrows */}
              <div className="columns pt-4 green-color">
                <div
                  className="column is-6"
                  onClick={() => {
                    const targetEl =
                      this.qtyInput.current.value !== this.qtyInput.current.defaultValue
                        ? this.qtyInput.current.value
                        : this.qtyInput.current.defaultValue;
                    const newValue = parseFloat(targetEl) + 1;
                    this.qtyInput.current.defaultValue = newValue.toFixed(2).toString();
                    this.qtyInput.current.value = this.qtyInput.current.defaultValue;
                    this.savePrice(parseFloat(this.qtyInput.current.defaultValue));
                  }}
                >
                  <i className="fas fa-2x fa-arrow-up"></i>
                </div>
                <div
                  className="column is-6"
                  onClick={() => {
                    const targetEl =
                      this.qtyInput.current.value !== this.qtyInput.current.defaultValue
                        ? this.qtyInput.current.value
                        : this.qtyInput.current.defaultValue;
                    const newValue = parseFloat(targetEl) - 1;
                    if (newValue > 0) {
                      this.qtyInput.current.defaultValue = newValue.toFixed(2).toString();
                      this.qtyInput.current.value = this.qtyInput.current.defaultValue;
                      const currentQty = parseFloat(this.qtyInput.current.defaultValue);
                      this.savePrice(currentQty);
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
  }
}

export default withRouter(RFQPriceEdit);
