import React, { useEffect, useContext } from 'react';
import { observer } from 'mobx-react';
import ProductAddItem from './ProductAddItem';
import ProductsNotContracted from './ProductsNotContracted';
import { RootStoreContext } from '../models/Store';

const ProductAddList: React.FunctionComponent = observer(() => {
  const store = useContext(RootStoreContext);

  useEffect(() => {
    store.productSelection.fetchSelectionProducts();
  }, [store]);

  const { products, moreProducts } = store.productSelection;

  return (
    <div className="mt-1">
      {/* Listing the items from the products first */}
      {products.length &&
        products.map((product) => {
          return (
            <ProductAddItem
              key={product.getProductId}
              id={product.getProductId}
              name={product.getName}
              packType={product.getPackingInfo}
            />
          );
        })}
      {moreProducts.length &&
        moreProducts.map((productItem) => {
          return (
            <ProductAddItem
              key={productItem.getProductId}
              id={productItem.getProductId}
              name={productItem.getName}
              packType={productItem.getPackingInfo}
            />
          );
        })}
      <ProductsNotContracted />
    </div>
  );
});

export default ProductAddList;
