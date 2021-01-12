import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';
import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
}

@inject('store')
@observer
class WeeklyNav extends React.Component<Props> {
  updateCurrentWeek = (to: string): void => {
    const { store } = this.props;
    console.log(store);
  };

  previousWeek = (): void => this.updateCurrentWeek('prev');
  nextWeek = (): void => this.updateCurrentWeek('next');

  render(): ReactElement {
    const { store } = this.props;

    if (!store) {
      return null;
    }
    const { week } = store;

    return (
      <div className="daily-nav">
        <div className="columns is-mobile">
          <div className="column is-3 arrow-navigation is-vcentered p-4" onClick={this.previousWeek}>
            <i className="fas fa-arrow-left fa-3x"></i>
          </div>
          <div className="column is-6">
            <div className="rows">
              <div className="row is-full"> {week.caption} </div>
              <div className="row is-full"> 05.2011 </div>
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
