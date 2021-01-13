import React, { FunctionComponent, ReactElement } from 'react';
import ProductAddItem from './ProductAddItem';

const ProductAddList: FunctionComponent = (): ReactElement => {
  return (
    <div className="custom-top-offset">
      <ProductAddItem id={`prodId1`} name={`Karotten Bund`} packType={`G2 x 6Stk`} />
      <ProductAddItem id={`prodId2`} name={`Auberiginen`} packType={`BLL6423 x 5 kg`} />
    </div>
  );
};

export default ProductAddList;
