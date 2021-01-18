import React, { FunctionComponent, ReactElement } from 'react';
import DailyNav from './DailyNav';
import View from './View';
// import { translate } from '../utils/translate';

const ProductScreen: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <DailyNav isStatic={true} />
        <div className="mt-1 p-4">
          <p className="subtitle">Some content</p>
        </div>
      </div>
    </View>
  );
};

export default ProductScreen;
