import React, { FunctionComponent, ReactElement } from 'react';

interface Props {
  id: string;
  productName: string;
  packingInfo: string;
  qty: number;
  isEdited: boolean;
  editedItemsNo: number;
}

const Product: FunctionComponent<Props> = ({ productName, qty, packingInfo }: Props): ReactElement => {
  return (
    <div className="product">
      <div className="box">
        <div className="columns is-mobile">
          <div className="column is-9">
            <div className="columns">
              <div className="column is-size-4-mobile no-p">{productName}</div>
              <div className="column is-size-7 no-p">{packingInfo}</div>
            </div>
          </div>
          <div className="column is-3 no-p">
            <div className="columns is-mobile">
              <div className="column mt-2 is-size-2-mobile no-p">{qty}</div>
              <div className="column green-check is-hidden-mobile">
                <i className="fas fa-check"></i>
              </div>
              <div className="column mt-4 green-check is-hidden-desktop is-hidden-tablet">
                <i className="fas fa-check"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Product;
