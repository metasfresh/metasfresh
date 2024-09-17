import React from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';
import { addOrderLine } from '../actions';
import { formatAmountToHumanReadableStr } from '../../../utils/money';

const ProductButton = ({ productId, name, price, currencySymbol, uomId, uomSymbol, order_uuid }) => {
  const dispatch = useDispatch();

  const isEnabled = !!order_uuid;
  const priceStr = formatAmountToHumanReadableStr({ amount: price, currency: currencySymbol }) + '/' + uomSymbol;

  const onClick = () => {
    dispatch(addOrderLine({ order_uuid, productId, productName: name, price, qty: 1, uomId, uomSymbol }));
  };
  return (
    <button className="product-button" onClick={onClick} disabled={!isEnabled}>
      <div className="name">{name}</div>
      <div className="price">{priceStr}</div>
    </button>
  );
};

ProductButton.propTypes = {
  productId: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
  price: PropTypes.number.isRequired,
  currencySymbol: PropTypes.string.isRequired,
  uomId: PropTypes.number.isRequired,
  uomSymbol: PropTypes.string.isRequired,
  order_uuid: PropTypes.string,
};

export default ProductButton;
