import React from 'react';
import PropTypes from 'prop-types';

const Button = ({ caption, onClick, disabled = false }) => {
  return (
    <button className="button is-outlined complete-btn is-fullwidth" onClick={onClick} disabled={!!disabled}>
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

Button.propTypes = {
  caption: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
};

export default Button;
