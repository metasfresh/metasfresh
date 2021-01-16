import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';

import { translate } from '../utils/translate';
import { formDate, prettyDate, slashSeparatedYYYYmmdd } from '../utils/date';
import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
}

@inject('store')
@observer
class DailyNav extends React.Component<Props> {
  componentDidMount(): void {
    const { store } = this.props;

    store.fetchDailyReport(store.app.currentDay);
    store.navigation.setViewName(translate('DailyReportingView.caption'));
  }

  updateCurrentDay = (to: string): void => {
    const { store } = this.props;
    const { day } = formDate({ currentDay: new Date(store.app.currentDay), to });

    const newDay = slashSeparatedYYYYmmdd(day);

    store.fetchDailyReport(newDay);
  };

  previousDay = (): void => this.updateCurrentDay('prev');
  nextDay = (): void => this.updateCurrentDay('next');

  render(): ReactElement {
    const { store } = this.props;

    // TODO: Is this really needed ? Can we even have no store if it's created on app init ?
    if (!store) {
      return null;
    }
    const { lang } = store.i18n;
    const { day } = formDate({ currentDay: new Date(store.app.currentDay) });

    return (
      <div className="daily-nav">
        <div className="columns is-mobile">
          <div className="column is-3 arrow-navigation is-vcentered p-4" onClick={this.previousDay}>
            <i className="fas fa-arrow-left fa-3x"></i>
          </div>
          <div className="column is-6">
            <div className="rows">
              <div className="row is-full"> {store.app.dayCaption} </div>
              <div className="row is-full"> {prettyDate({ lang, date: day })} </div>
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
