import React from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';
import { addOrderLineAction } from '../actions';

const ProductButton = ({ productId, name }) => {
  const dispatch = useDispatch();
  const onClick = () => {
    dispatch(addOrderLineAction({ productId, productName: name }));
  };
  return (
    <button className="product-button" onClick={onClick}>
      {name}
    </button>
  );
};

ProductButton.propTypes = {
  productId: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
};

export default ProductButton;
