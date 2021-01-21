import React, { FunctionComponent, ReactElement } from 'react';

interface Props {
  name: string;
  packType: string;
  qty: number;
  handleClick?: (productId: string) => void;
}

const WeeklyProduct: FunctionComponent<Props> = ({ qty, name, packType, handleClick }: Props): ReactElement => {
  return (
    <div className="product" onClick={() => handleClick('test')}>
      <div className="box">
        <div className="columns is-mobile">
          <div className="column is-8">
            <div className="columns">
              <div className="column is-size-4-mobile no-p">{name}</div>
              <div className="column is-size-7 no-p">{packType}</div>
            </div>
          </div>
          <div className="column is-4 no-p">
            <div className="columns is-mobile">
              <div className="column mt-2 is-size-2-mobile no-p has-text-right">{qty}</div>
              <div className="column green-check is-hidden-mobile">
                <i className="fas fa-check"></i>
              </div>
              <div className="column mt-4 green-check is-hidden-desktop is-hidden-tablet is-4">
                {/* Trend listing in here */}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default WeeklyProduct;
