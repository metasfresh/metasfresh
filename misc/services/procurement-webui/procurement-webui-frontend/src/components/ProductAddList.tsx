import React, { FunctionComponent, ReactElement } from 'react';
import ProductAddItem from './ProductAddItem';
import ProductsNotContracted from './ProductsNotContracted';

const ProductAddList: FunctionComponent = (): ReactElement => {
  return (
    <div className="mt-1">
      <ProductAddItem id={`prodId1`} name={`Karotten Bund`} packType={`G2 x 6Stk`} />
      <ProductAddItem id={`prodId2`} name={`Auberiginen`} packType={`BLL6423 x 5 kg`} />

      <ProductsNotContracted />
    </div>
  );
};

export default ProductAddList;
