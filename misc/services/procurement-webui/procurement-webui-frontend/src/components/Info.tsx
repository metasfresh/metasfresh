import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';

const Info: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <h1 className="title p-4">Info/News</h1>
        <div className="custom-top-offset p-4">
          <p className="subtitle">Some content</p>
        </div>
      </div>
    </View>
  );
};

export default Info;
