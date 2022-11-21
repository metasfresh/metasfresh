import React from 'react';
import PropTypes from 'prop-types';

const AllergenIcon = ({ allergens, size = 40 }) => {
  if (!allergens || allergens.length === 0) {
    return null;
  }

  const step = Math.round(360 / allergens.length);

  let title = '';
  let cssConicGradient = '';
  let position = 0;
  allergens.forEach((allergen, index) => {
    //
    // Title
    if (allergen.name) {
      if (title) {
        title += ', ';
      }
      title += allergen.name;
    }

    //
    // pie slice color:
    {
      const color = allergen.color ?? 'black';

      // first
      if (index === 0) {
        cssConicGradient = color;
        if (step < 360) {
          cssConicGradient += ' ' + step + 'deg';
        } else {
          cssConicGradient += ' 0deg 360deg';
        }
      }
      // last
      else if (index === allergens.length - 1) {
        cssConicGradient += ', ' + color + ' ' + position + 'deg';
      } else {
        cssConicGradient += ', ' + color + ' ' + position + 'deg ' + (position + step) + 'deg';
      }

      position += step;
    }
  });

  const style = {
    display: 'inline-block',
    width: size + 'px',
    height: size + 'px',
    borderRadius: '100%',
    border: '1px solid gray', // needed for contract in case one of the colors are white
    aspectRatio: '1',
    background: 'conic-gradient(' + cssConicGradient + ')',
  };

  return <span style={style} title={title} />;
};

AllergenIcon.propTypes = {
  allergens: PropTypes.array,
  size: PropTypes.number.isRequired,
};

export default AllergenIcon;
