import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';

import { translate } from '../utils/translate';
import { formDate, prettyDate, slashSeparatedYYYYmmdd } from '../utils/date';
import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
  isStatic?: boolean;
  staticDay?: string;
  staticCaption?: string;
}

@inject('store')
@observer
class DailyNav extends React.Component<Props> {
  componentDidMount(): void {
    const { store, isStatic } = this.props;

    if (!isStatic) {
      // there's nothing `before` the start screen so we can safely remove anything that might've
      // ended up there (like `login`)
      store.navigation.clearViewsHistory();
      store.navigation.setViewNames(translate('DailyReportingView.caption'));

      store.fetchDailyReport(store.app.currentDay);
    }
  }

  updateCurrentDay = (to: string): void => {
    const { store } = this.props;
    const date = formDate({ currentDay: new Date(store.app.currentDay), to });
    const formattedDate = slashSeparatedYYYYmmdd(date);

    store.fetchDailyReport(formattedDate);
  };

  previousDay = (): void => this.updateCurrentDay('prev');
  nextDay = (): void => this.updateCurrentDay('next');

  render(): ReactElement {
    const { store, isStatic, staticDay, staticCaption } = this.props;

    // TODO: Is this really needed ? Can we even have no store if it's created on app init ?
    if (!store) {
      return null;
    }
    const { lang } = store.i18n;
    const date = formDate({ currentDay: new Date(store.app.currentDay) });

    const renderedCaption = isStatic ? staticDay : store.app.dayCaption;
    const renderedDay = isStatic ? staticCaption : prettyDate({ lang, date });

    return (
      <div className="daily-nav">
        <div className="columns is-mobile">
          {!isStatic && (
            <div className="column is-3 arrow-navigation is-vcentered p-4" onClick={this.previousDay}>
              <i className="fas fa-arrow-left fa-3x"></i>
            </div>
          )}
          <div className={`column is-${isStatic ? '12' : '6'}`}>
            <div className="rows">
              <div className="row is-full"> {renderedCaption} </div>
              <div className="row is-full"> {renderedDay} </div>
            </div>
          </div>
          {!isStatic && (
            <div className="column is-3 has-text-right arrow-navigation is-vcentered p-4" onClick={this.nextDay}>
              <i className="fas fa-arrow-right fa-3x"></i>
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default DailyNav;
