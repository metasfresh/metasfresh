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
      <div className="columns is-centered">
        <div className="column is-three-fifths">&#8592;</div>
        <div className="column is-three-fifths">
          Daily Nav
          {day.currentDay}
        </div>
        <div className="column is-three-fifths">&#8594;</div>
      </div>
    );
  }
}

export default DailyNav;
