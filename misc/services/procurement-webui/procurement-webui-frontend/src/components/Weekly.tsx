import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';

const Weekly: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <h1 className="title">Weekly view</h1>
        <p className="subtitle">Some content</p>
      </div>
    </View>
  );
};

export default Weekly;
