import React, { FunctionComponent, ReactElement } from 'react';
import { useHistory } from 'react-router-dom';
import { translate } from '../utils/translate';

interface Props {
  id: string;
  productName: string;
  packingInfo: string;
  qty: number;
  isEdited: boolean;
  editedItemsNo: number;
  confirmedByUser?: boolean;
}

const Product: FunctionComponent<Props> = ({
  id,
  productName,
  qty,
  packingInfo,
  confirmedByUser,
}: Props): ReactElement => {
  const history = useHistory();

  return (
    <div className="product">
      <div
        className="box"
        onClick={() =>
          history.push({
            pathname: `/products/${id}`,
            state: { path: '/', text: translate('DailyReportingView.caption') },
          })
        }
      >
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
              {confirmedByUser && <div className="column mt-4 is-hidden-desktop is-hidden-tablet">&nbsp;</div>}
              {!confirmedByUser && (
                <div className="column mt-4 green-check is-hidden-desktop is-hidden-tablet">
                  <i className="fas fa-check"></i>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Product;
