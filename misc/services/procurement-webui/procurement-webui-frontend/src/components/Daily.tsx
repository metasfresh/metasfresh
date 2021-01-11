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
        <div className="p-4">
          <DailyNav headerText={`Dienstag`} />
          <p>Something something</p>
        </div>
      </View>
    );
  }
}
