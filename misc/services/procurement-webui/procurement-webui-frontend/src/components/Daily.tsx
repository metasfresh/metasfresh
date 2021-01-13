import React, { Component, ReactElement } from 'react';
import DailyNav from './DailyNav';
import View from './View';
import { observer, inject } from 'mobx-react';
import { RootInstance } from '../models/Store';
import ProductList from './ProductList';

interface Props {
  store?: RootInstance;
}

@inject('store')
@observer
export default class DailyView extends Component<Props> {
  componentDidMount(): void {
    const { store } = this.props;

    // TODO: Use current date as it's only fired on init
    store.fetchDailyReport('12-01-2020');
  }

  render(): ReactElement {
    return (
      <View>
        <DailyNav />
        <section className="section">
          <ProductList />
        </section>
      </View>
    );
  }
}
