import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

const ProductCategoryButton = ({ id, name, selected, disabled, onClick }) => {
  const handleClick = () => {
    if (disabled) return;
    onClick({ categoryId: id });
  };

  return (
    <button
      className={cx('product-category-button', { 'product-category-button-selected': selected })}
      disabled={disabled}
      onClick={handleClick}
    >
      <div className="line1">
        <span>{name}</span>
      </div>
    </button>
  );
};

ProductCategoryButton.propTypes = {
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
  name: PropTypes.string.isRequired,
  selected: PropTypes.bool.isRequired,
  disabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

export default ProductCategoryButton;
