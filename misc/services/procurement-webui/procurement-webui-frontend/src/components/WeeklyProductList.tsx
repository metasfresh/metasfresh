import React, { ReactElement, useContext } from 'react';
import { RootStoreContext } from '../models/Store';
import WeeklyProduct from './WeeklyProduct';
import { observer } from 'mobx-react';

interface Props {
  headerText: string;
}

const WeeklyProductList: React.FunctionComponent<Props> = observer(
  ({}: Props): ReactElement => {
    const store = useContext(RootStoreContext);
    const products = store.weeklyProducts.getProducts;

    const handleClick = (productId: string) => {
      console.log('Clicked: ', productId);
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
