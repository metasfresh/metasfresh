import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';
import { withRouter, RouteComponentProps } from 'react-router';
import { getSnapshot } from 'mobx-state-tree';
import { formDate, prettyDate } from '../utils/date';
import DailyNav from './DailyNav';
import View from './View';
import { RootInstance } from '../models/Store';

interface MatchParams {
  name: string;
  productId: string;
  targetDay: string;
  targetDayCaption: string;
}

interface Props extends RouteComponentProps<MatchParams> {
  store?: RootInstance;
}

@inject('store')
@observer
class ProductScreen extends React.Component<Props> {
  private qtyInput = React.createRef<HTMLInputElement>();
  private isUnmounting = false;

  componentDidMount(): void {
    document.addEventListener('focusout', this.handleFocusOut);

    const { store, match } = this.props;
    const { productId } = match.params;
    const products = getSnapshot(store.dailyProducts.products);
    const product = products.find((prod) => prod.productId === productId);

    store.navigation.setViewNames(product.productName);
  }

  componentWillUnmount(): void {
    document.removeEventListener('focusout', this.handleFocusOut);
  }

  saveQty = (newQty: number): void => {
    const { store, match } = this.props;
    const { productId, targetDay } = match.params;
    const products = getSnapshot(store.dailyProducts.products);
    const product = products.find((prod) => prod.productId === productId);
    const currentDay = targetDay ? targetDay : store.app.currentDay;

    store.postDailyReport({
      items: [
        {
          date: currentDay,
          productId: product.productId,
          qty: newQty ? newQty : 0,
        },
      ],
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
    const { lang } = store.i18n;
    const { productId, targetDay, targetDayCaption } = match.params;
    const products = getSnapshot(store.dailyProducts.products);
    const product = products.find((prod) => prod.productId === productId);
    const currentDay = targetDay ? targetDay : store.app.currentDay;
    const currentCaption = targetDayCaption ? targetDayCaption : store.app.dayCaption;
    const productQty = product.qty.toString();

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
                  onBlur={(e) => {
                    this.saveQty(parseInt(e.target.value, 10));
                  }}
                  onTouchStart={() => {
                    this.qtyInput.current.focus();
                    this.qtyInput.current.select();
                  }}
                  ref={this.qtyInput}
                  step="1"
                  value={productQty.length > 1 ? productQty.replace(/^0+/, '') : productQty}
                  onKeyUp={(e) => {
                    if (e.key === 'Enter') {
                      this.handleFocusOut();
                    }
                  }}
                  onChange={(e) => {
                    let updatedQty = parseInt(e.target.value, 10);
                    updatedQty = isNaN(updatedQty) ? 0 : updatedQty;
                    store.dailyProducts.updateProductQty(product.productId, updatedQty);
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
                  <span>
                    <i className="fas fa-2x fa-arrow-down"></i>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </View>
    );
  }
}

export default withRouter(ProductScreen);
