import React, { ReactElement } from 'react';
import View from './View';

export default function Home(): ReactElement {
  return (
    <View>
      <div className="container">
        <h1 className="title">Hello World</h1>
        <p className="subtitle">Some content</p>
      </div>
    </View>
  );
}
