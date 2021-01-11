import React, { FunctionComponent, ReactElement } from 'react';
import DailyNav from './DailyNav';
import View from './View';

const DailyView: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <h1 className="title p-4">Daily view</h1>
        <DailyNav headerText={`Dienstag`} />
        <br />
        <p className="subtitle p-4">Some content</p>
      </div>
    </View>
  );
};

export default DailyView;
