import React from 'react';
import PropTypes from 'prop-types';

const ButtonDetails = ({ caption1, value1, caption2, value2 }) => {
  return (
    <div className="row is-full is-size-7">
      <div
        className="button-details"
        data-caption1={caption1}
        data-value1={value1}
        data-caption2={caption2}
        data-value2={value2}
      >
        <div className="caption1">{formatCaptionLabel(caption1)}</div>
        <div className="value1">{formatValueLabel(value1)}</div>
        <div className="caption2">{formatCaptionLabel(caption2)}</div>
        <div className="value2">{formatValueLabel(value2)}</div>
      </div>
    </div>
  );
};

ButtonDetails.propTypes = {
  caption1: PropTypes.string,
  value1: PropTypes.any,
  caption2: PropTypes.string,
  value2: PropTypes.any,
};

export default ButtonDetails;

//
//
//

const formatCaptionLabel = (caption) => {
  const captionNorm = caption?.trim();
  if (!captionNorm) return <>&nbsp;</>;
  return captionNorm + ' : ';
};

const formatValueLabel = (value) => {
  if (!value) return <>&nbsp;</>;
  return value;
};
