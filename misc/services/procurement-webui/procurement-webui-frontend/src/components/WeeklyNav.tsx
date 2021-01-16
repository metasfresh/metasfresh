import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';
import { RootInstance } from '../models/Store';
// import { fetchWeeklyReport } from '../api';

interface Props {
  store?: RootInstance;
}

@inject('store')
@observer
class WeeklyNav extends React.Component<Props> {
  componentDidMount(): void {
    const { store } = this.props;
    const currWeek = store.app.week;
    this.updateWeekData(currWeek);
  }

  async updateWeekData(currWeek: string): Promise<void> {
    const { store } = this.props;
    // const currWeekReport = await fetchWeeklyReport(currWeek);
    // const { week, nextWeek, previousWeek, weekCaption } = currWeekReport.data;
    // // update the caption
    // store.week.changeCaption(weekCaption);
    // store.week.changeCurrentWeek(week);
    // // update the next and prev
    // store.week.changeNextWeek(nextWeek);
    // store.week.changePrevWeek(previousWeek);
  }

  updateCurrentWeek = (to: string): void => {
    const { store } = this.props;
    to === 'prev' && this.updateWeekData(store.app.previousWeek);
    to === 'next' && this.updateWeekData(store.app.nextWeek);
  };

  previousWeek = (): void => this.updateCurrentWeek('prev');
  nextWeek = (): void => this.updateCurrentWeek('next');

  render(): ReactElement {
    const { store } = this.props;

    if (!store) {
      return null;
    }
    const { app } = store;

    return (
      <div className="daily-nav">
        <div className="columns is-mobile">
          <div className="column is-3 arrow-navigation is-vcentered p-4" onClick={this.previousWeek}>
            <i className="fas fa-arrow-left fa-3x"></i>
          </div>
          <div className="column is-6">
            <div className="rows">
              <div className="row is-full"> {app.weekCaption} </div>
              <div className="row is-full"> {app.week} </div>
            </div>
          </div>
          <div className="column is-3 has-text-right arrow-navigation is-vcentered p-4" onClick={this.nextWeek}>
            <i className="fas fa-arrow-right fa-3x"></i>
          </div>
        </div>
      </div>
    );
  }
}

export default WeeklyNav;
