import React, { ReactElement, useContext, useEffect } from 'react';
import { observer } from 'mobx-react';

import { translate } from '../utils/translate';
import { RootStoreContext } from '../models/Store';
import WeeklyProduct from './WeeklyProduct';

interface Props {
  headerText: string;
}

const WeeklyProductList: React.FunctionComponent<Props> = observer(
  ({}: Props): ReactElement => {
    const store = useContext(RootStoreContext);
    const products = store.weeklyProducts.getProducts;
    const weeklyNavCaption = translate('WeeklyReportingView.caption');

    useEffect(() => {
      store.navigation.setViewNames(weeklyNavCaption);
    }, []);

    return (
      <div className="mt-1">
        {products.length &&
          products.map((product) => {
            return (
              <WeeklyProduct
                key={product.getName}
                productId={product.getId}
                name={product.getName}
                packType={product.getPack}
                qty={product.getQty}
                nextWeekTrend={product.getTrend}
              />
            );
          })}
      </div>
    );
  }
);

export default WeeklyProductList;
