import React, { ReactElement } from 'react';
import View from './View';

export default function About(): ReactElement {
  return (
    <View>
      <div className="container">
        <h1 className="title">Page 1</h1>
        <p className="subtitle">Some content</p>
      </div>
    </View>
  );
}
