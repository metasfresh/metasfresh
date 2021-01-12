import React, { Component, ReactElement } from 'react';

import DailyNav from './DailyNav';
import View from './View';

import { observer, inject } from 'mobx-react';
import { RootInstance } from '../models/Store';

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
        <div>
          <h1 className="title p-4">Daily view</h1>
          <DailyNav />
          <br />
          <p className="subtitle p-4">Some content</p>
        </div>
      </View>
    );
  }
}
