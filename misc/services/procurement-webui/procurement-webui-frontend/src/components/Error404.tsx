import React, { ReactElement } from 'react';

import { translate } from '../utils/translate';
import View from './View';

export default function Error404(): ReactElement {
  return (
    <View>
      <p>{translate('error.default')}</p>
    </View>
  );
}
