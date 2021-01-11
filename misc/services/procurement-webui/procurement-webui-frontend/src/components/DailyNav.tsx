import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';

import { RootInstance } from '../models/Store';
import { formDate, prettyDate } from '../utils/date';

interface Props {
  headerText: string;
  store?: RootInstance;
}

@inject('store')
@observer
class DailyNav extends React.Component<Props> {
  updateCurrentDay = (to: string): void => {
    const { store } = this.props;
    const { caption, day } = formDate({ lang: 'de_DE', currentDay: new Date(store.day.currentDay), to });

    store.day.changeCaption(caption);
    store.day.changeCurrentDay(day);
  };

  previousDay = (): void => this.updateCurrentDay('prev');
  nextDay = (): void => this.updateCurrentDay('next');

  render(): ReactElement {
    const { store } = this.props;

    if (!store) {
      return null;
    }
    const { day } = store;

    return (
      <div className="daily-nav">
        <div className="columns is-mobile">
          <div className="column is-3 arrow-navigation is-vcentered p-4" onClick={this.previousDay}>
            <i className="fas fa-arrow-left fa-3x"></i>
          </div>
          <div className="column is-6">
            <div className="rows">
              <div className="row is-full"> {day.caption} </div>
              <div className="row is-full"> {prettyDate({ lang: 'de_DE', date: day.currentDay })} </div>
            </div>
          </div>
          <div className="column is-3 has-text-right arrow-navigation is-vcentered p-4" onClick={this.nextDay}>
            <i className="fas fa-arrow-right fa-3x"></i>
          </div>
        </div>
      </div>
    );
  }
}

export default DailyNav;
