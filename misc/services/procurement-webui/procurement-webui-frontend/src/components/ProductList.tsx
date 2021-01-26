import React, { ReactElement } from 'react';
import { observer, inject } from 'mobx-react';
import { getSnapshot } from 'mobx-state-tree';

import Product from './Product';
import { RootInstance } from '../models/Store';

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
        {products
          ? products.map((product) => {
              // otherwise normal product rendering
              return (
                <Product
                  key={product.productId}
                  id={product.productId}
                  productName={product.productName}
                  packingInfo={product.packingInfo}
                  qty={product.qty}
                  isEdited={product.isEdited}
                  editedItemsNo={0}
                  confirmedByUser={product.confirmedByUser}
                />
              );
            })
          : null}
      </div>
    );
  }
}

export default ProductList;
