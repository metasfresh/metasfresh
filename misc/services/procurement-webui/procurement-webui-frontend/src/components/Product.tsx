import React, { FunctionComponent, ReactElement } from 'react';

interface Props {
  id: string;
  name: string;
  packType: string;
  itemsNo: number;
  isEdited: boolean;
  editedItemsNo: number;
}

const Product: FunctionComponent<Props> = ({ name, itemsNo, packType }: Props): ReactElement => {
  return (
    <div className="product">
      <div className="box">
        <div className="columns is-mobile">
          <div className="column is-9">
            <div className="columns">
              <div className="column is-size-4-mobile no-p">{name}</div>
              <div className="column is-size-7 no-p">{packType}</div>
            </div>
          </div>
          <div className="column is-3 no-p">
            <div className="is-size-2-mobile no-p">{itemsNo}</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Product;
