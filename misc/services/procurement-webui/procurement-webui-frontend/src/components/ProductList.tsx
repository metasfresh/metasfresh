import React, { ReactElement } from 'react';
import Product from './Product';
import { observer, inject } from 'mobx-react';
import { RootInstance } from '../models/Store';
import { getSnapshot } from 'mobx-state-tree';
interface Props {
  store?: RootInstance;
}
@inject('store')
@observer
class ProductList extends React.Component<Props> {
  render(): ReactElement {
    const { store } = this.props;
    const products = getSnapshot(store.dailyProducts.getProducts);

    return (
      <div className="mt-4">
        {products &&
          products.map((product) => (
            <Product
              key={product.productId}
              id={product.productId}
              productName={product.productName}
              packingInfo={product.packingInfo}
              qty={product.qty}
              isEdited={product.isEdited}
              editedItemsNo={0}
            />
          ))}
      </div>
    );
  }
}

export default ProductList;
