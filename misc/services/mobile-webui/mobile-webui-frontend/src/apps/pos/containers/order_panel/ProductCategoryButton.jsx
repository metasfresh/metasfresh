import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { getProductCategoryImage } from '../../api/products';

const ProductCategoryButton = ({ id, name, hasImage, selected, disabled, onClick }) => {
  const [imageData, setImageData] = useState();

  useEffect(() => {
    if (hasImage) {
      getProductCategoryImage({ categoryId: id, maxWidth: 150, maxHeight: 150 }).then(setImageData);
    }
  }, [id, hasImage]);

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
      <div className="product-category-button-image">{imageData && <img src={imageData} alt="image" />}</div>
      <div className="product-category-button-caption">
        <span>{name}</span>
      </div>
    </button>
  );
};

ProductCategoryButton.propTypes = {
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
  name: PropTypes.string.isRequired,
  hasImage: PropTypes.bool,
  selected: PropTypes.bool.isRequired,
  disabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

export default ProductCategoryButton;
