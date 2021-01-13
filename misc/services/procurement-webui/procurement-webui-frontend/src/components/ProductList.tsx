import React, { FunctionComponent, ReactElement } from 'react';
import Product from './Product';

const ProductList: FunctionComponent = (): ReactElement => {
  return (
    <div className="mt-4">
      <Product id={`prodId1`} name={`Batavia`} packType={`G2 x 6Stk`} itemsNo={0} isEdited={false} editedItemsNo={0} />
      <Product id={`prodId2`} name={`Broccoli`} packType={`G2 x 6 Kg`} itemsNo={0} isEdited={false} editedItemsNo={0} />
      <Product
        id={`prodId3`}
        name={`Lauch grun`}
        packType={`G2 x 7 Kg`}
        itemsNo={0}
        isEdited={false}
        editedItemsNo={0}
      />
    </div>
  );
};

export default ProductList;
