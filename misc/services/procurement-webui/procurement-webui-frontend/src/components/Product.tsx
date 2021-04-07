import React, { ReactElement, useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { useSwipeable } from 'react-swipeable';

import { RootStoreContext } from '../models/Store';

interface Props {
  id: string;
  productName: string;
  packingInfo: string;
  qty: number;
  isEdited: boolean;
  editedItemsNo: number;
  confirmedByUser?: boolean;
}

const Product: React.FunctionComponent<Props> = ({
  id,
  productName,
  qty,
  packingInfo,
  confirmedByUser,
}: Props): ReactElement => {
  const history = useHistory();
  const store = useContext(RootStoreContext);
  const removeProduct = (productId: string) => {
    store.productSelection.favoriteRemove([productId]).then(() => {
      store.fetchDailyReport(store.app.currentDay);
    });
  };

  const handlers = useSwipeable({
    onSwipedLeft: () => removeProduct(id),
    onSwipedRight: () => removeProduct(id),
    preventDefaultTouchmoveEvent: true,
    trackMouse: true,
  });

  return (
    <div {...handlers} className="product">
      <div
        className="box"
        onClick={() => history.push({ pathname: `/products/${id}/${store.app.currentDay}/${store.app.dayCaption}` })}
      >
        <div className="columns is-mobile">
          <div className="column is-8">
            <div className="columns">
              <div className="column is-size-4-mobile no-p">{productName}</div>
              <div className="column is-size-7 no-p">{packingInfo}</div>
            </div>
          </div>
          <div className="column is-4 no-p">
            <div className="columns is-mobile">
              <div className="column mt-2 is-size-2-mobile no-p has-text-right">{qty}</div>
              <div className="column green-check is-hidden-mobile">
                <i className="fas fa-check"></i>
              </div>
              {!confirmedByUser && <div className="column mt-4 is-hidden-desktop is-hidden-tablet is-4">&nbsp;</div>}
              {confirmedByUser && (
                <div className="column mt-4 green-check is-hidden-desktop is-hidden-tablet is-4">
                  <span>
                    <i className="fas fa-check"></i>
                  </span>
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
