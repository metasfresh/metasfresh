import React, { FunctionComponent, ReactElement } from 'react';

interface Props {
  id: string;
  name: string;
  packType: string;
  handleClick?: (productId: string) => void;
}

const ProductAddItem: FunctionComponent<Props> = ({ id, name, packType, handleClick }: Props): ReactElement => {
  return (
    <div className="product" onClick={() => handleClick(id)}>
      <div className="box">
        <div className="columns is-mobile">
          <div className="column is-12">
            <div className="columns">
              <div className="column is-size-4-mobile no-p">{name}</div>
              <div className="column is-size-7 no-p">{packType}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductAddItem;
