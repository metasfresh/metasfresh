import React, { FunctionComponent, ReactElement } from 'react';
import View from './View';
import { translate } from '../utils/translate';

const RfQ: FunctionComponent = (): ReactElement => {
  return (
    <View>
      <div>
        <h1 className="title p-4">{translate('RfQView.caption')}</h1>
        <div className="mt-1 p-4">
          <p className="subtitle">Some content</p>
        </div>
      </div>
    </View>
  );
};

export default RfQ;
