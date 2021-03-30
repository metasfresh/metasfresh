import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';
import WeeklyNav from './WeeklyNav';
import WeeklyProductList from './WeeklyProductList';

const Weekly: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <WeeklyNav />
        <div className="mt-1 p-4">
          <WeeklyProductList headerText={``} />
        </div>
      </div>
    </View>
  );
};

export default Weekly;
