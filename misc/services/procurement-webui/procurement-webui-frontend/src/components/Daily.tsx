import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';

const DailyView: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div className="container">
        <h1 className="title">Daily view</h1>
        <p className="subtitle">Some content</p>
      </div>
    </View>
  );
};

export default DailyView;
