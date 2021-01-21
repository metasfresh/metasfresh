import React, { useEffect, useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { observer } from 'mobx-react';
import ProductAddItem from './ProductAddItem';
import { RootStoreContext } from '../models/Store';
import { translate } from '../utils/translate';

const ProductAddList: React.FunctionComponent = observer(() => {
  const store = useContext(RootStoreContext);
  const history = useHistory();
  useEffect(() => {
    store.navigation.setViewNames(translate('SelectProductView.caption'));
    store.productSelection.setShowMoreVisibility(true);
    store.productSelection.fetchSelectionProducts();
  }, [store]);

  const { products, moreProducts, showMoreBtnVisible } = store.productSelection;

  const handleClick = (productId: string) => {
    store.productSelection.favoriteAdd([productId]).then(() => {
      history.push({
        pathname: `/`,
        state: { path: `/`, text: translate('DailyReportingView.caption') },
      });
    });
  };

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
              handleClick={handleClick}
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
              handleClick={handleClick}
            />
          );
        })}
    </div>
  );
});

export default ProductAddList;
