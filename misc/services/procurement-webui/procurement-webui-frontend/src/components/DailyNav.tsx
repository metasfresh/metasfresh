import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';
import { RootInstance } from '../models/Store';

interface Props {
  headerText: string;
  store?: RootInstance;
}

@inject('store')
@observer
class DailyNav extends React.Component<Props> {
  render(): ReactElement {
    const { store } = this.props;
    if (!store) return null;
    const { day } = store;
    return (
      <div className="daily-nav">
        <div className="columns is-mobile">
          <div className="column is-3 arrow-navigation">
            <i className="fas fa-arrow-left fa-3x"></i>
          </div>
          <div className="column is-6">
            <div className="rows">
              <div className="row is-full"> Freitag </div>
              <div className="row is-full"> {day.currentDay} </div>
            </div>
          </div>
          <div className="column is-3 has-text-right arrow-navigation">
            <i className="fas fa-arrow-right fa-3x"></i>
          </div>
        </div>
      </div>
    );
  }
}

export default DailyNav;
