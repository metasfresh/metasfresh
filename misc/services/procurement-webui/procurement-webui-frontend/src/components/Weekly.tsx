import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';
import WeeklyNav from './WeeklyNav';

const Weekly: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <WeeklyNav />
        <div className="mt-1 p-4">
          <p className="subtitle">Some content</p>
        </div>
      </div>
    </View>
  );
};

export default Weekly;
