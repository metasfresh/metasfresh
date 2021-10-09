import React, { FunctionComponent, ReactElement } from 'react';

import DailyNav from './DailyNav';
import View from './View';
import ProductList from './ProductList';

const DailyView: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <DailyNav />
      <section className="section">
        <ProductList />
      </section>
    </View>
  );
};

export default DailyView;
