import React, { FunctionComponent, ReactElement } from 'react';
import DailyNav from './DailyNav';
import View from './View';

const DailyView: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div className="container">
        <h1 className="title">Daily view</h1>
        <DailyNav headerText={`Dienstag`} />
        <p className="subtitle">Some content</p>
      </div>
    </View>
  );
};

export default DailyView;
