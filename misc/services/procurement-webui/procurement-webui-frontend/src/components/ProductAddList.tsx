import React, { useEffect, useContext } from 'react';
import { observer } from 'mobx-react';
import ProductAddItem from './ProductAddItem';
import { RootStoreContext } from '../models/Store';
import { translate } from '../utils/translate';

const ProductAddList: React.FunctionComponent = observer(() => {
  const store = useContext(RootStoreContext);

  useEffect(() => {
    store.productSelection.setShowMoreVisibility(true);
    store.productSelection.fetchSelectionProducts();
  }, [store]);

  const { products, moreProducts, showMoreBtnVisible } = store.productSelection;

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

      <div className="mt-4">
        {showMoreBtnVisible && (
          <div className="box" onClick={() => store.productSelection.setShowMoreVisibility(false)}>
            {translate('SelectProductView.showMeNotContractedButton')}
          </div>
        )}
      </div>
      {moreProducts.length &&
        !showMoreBtnVisible &&
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
    </div>
  );
});

export default ProductAddList;
