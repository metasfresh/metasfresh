import React from 'react';
import PropTypes from 'prop-types';
import ProductCategoryButton from './ProductCategoryButton';
import './ProductCategories.scss';

const ProductCategories = ({ categories, disabled, onClick }) => {
  if (!categories?.length) return null;

  return (
    <div className="product-categories">
      {categories.map((category) => (
        <ProductCategoryButton
          key={category.id}
          id={category.id}
          name={category.name}
          hasImage={category.hasImage}
          selected={!!category.selected}
          disabled={disabled}
          onClick={onClick}
        />
      ))}
    </div>
  );
};

ProductCategories.propTypes = {
  disabled: PropTypes.bool,
  categories: PropTypes.array,
  onClick: PropTypes.func.isRequired,
};

export default ProductCategories;
