import React, { Component, ReactElement } from 'react';
import { observer, inject } from 'mobx-react';

import { fetchDailyReport } from '../api';

import { RootInstance } from '../models/Store';
import DailyNav from './DailyNav';
import View from './View';

interface Props {
  store?: RootInstance;
}
@inject('store')
@observer
export default class DailyView extends Component<Props> {
  componentDidMount(): void {
    const { store } = this.props;

    fetchDailyReport('12-01-2020').then((resp) => {
      store.navigation.setViewName(resp.data.dayCaption);
    });
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
