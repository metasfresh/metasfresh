import React, { useContext, useEffect } from 'react';
import DailyNav from './DailyNav';
import View from './View';
import { RootStoreContext } from '../models/Store';
import { observer } from 'mobx-react';
// import { translate } from '../utils/translate';

const ProductScreen: React.FC = observer(() => {
  const store = useContext(RootStoreContext);

  useEffect(() => {
    store.navigation.setViewName('Actual Product');
  }, [store]);

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
});

export default ProductScreen;
