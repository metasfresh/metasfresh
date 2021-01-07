import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';

const Home: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div className="container">
        <h1 className="title">Hello World</h1>
        <p className="subtitle">Some content</p>
      </div>
    </View>
  );
};

export default Home;
