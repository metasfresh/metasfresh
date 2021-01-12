import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';
import WeeklyNav from './WeeklyNav';

const Weekly: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <h1 className="title p-4">Weekly view</h1>
        <WeeklyNav />
        <div className="custom-top-offset p-4">
          <p className="subtitle">Some content</p>
        </div>
      </div>
    </View>
  );
};

export default Weekly;
