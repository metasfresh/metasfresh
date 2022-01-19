import React from 'react';
import PropTypes from 'prop-types';

const OptionButton = ({ optionIndex, caption, onClick }) => {
  return (
    <button className="button is-outlined complete-btn" disabled={false} onClick={() => onClick(optionIndex)}>
      <div className="full-size-btn">
        <div className="left-btn-side" />
        <div className="caption-btn">
          <div className="rows">
            <div className="row is-full pl-5">{caption}</div>
          </div>
        </div>
      </div>
    </button>
  );
};

OptionButton.propTypes = {
  optionIndex: PropTypes.number.isRequired,
  caption: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default OptionButton;
