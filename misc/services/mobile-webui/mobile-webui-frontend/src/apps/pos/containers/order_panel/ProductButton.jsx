import React from 'react';
import PropTypes from 'prop-types';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';

import './ProductButton.scss';

const ProductButton = ({ name, price, currencySymbol, uomSymbol, disabled, onClick }) => {
  const priceStr = formatAmountToHumanReadableStr({ amount: price, currency: currencySymbol }) + '/' + uomSymbol;

  return (
    <button className="product-button" disabled={disabled} onClick={onClick}>
      <div className="line1">
        <span>{name}</span>
      </div>
      <div className="line2">{priceStr}</div>
    </button>
  );
};

ProductButton.propTypes = {
  name: PropTypes.string.isRequired,
  price: PropTypes.number.isRequired,
  currencySymbol: PropTypes.string.isRequired,
  uomSymbol: PropTypes.string.isRequired,
  disabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

export default ProductButton;
