import React, { ReactElement, useContext } from 'react';
import { RootStoreContext } from '../models/Store';
import WeeklyProduct from './WeeklyProduct';
import { observer } from 'mobx-react';
import { useHistory } from 'react-router-dom';
import { translate } from '../utils/translate';

interface Props {
  headerText: string;
}

const WeeklyProductList: React.FunctionComponent<Props> = observer(
  ({}: Props): ReactElement => {
    const store = useContext(RootStoreContext);
    const history = useHistory();
    const products = store.weeklyProducts.getProducts;

    const handleClick = (productId: string) => {
      const weeklyNavCaption = translate('WeeklyReportingView.caption');
      history.push({
        pathname: `/weekly/${productId}`,
        state: { path: '/weekly', text: weeklyNavCaption },
      });
    };

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
                handleClick={handleClick}
              />
            );
          })}
      </div>
    );
  }
);

export default WeeklyProductList;
