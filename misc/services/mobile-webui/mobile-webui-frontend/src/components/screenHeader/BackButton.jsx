import React from 'react';
import PropTypes from 'prop-types';

const BackButton = ({ onClick }) => (
  <span className="btn-icon" onClick={onClick}>
    <i className="icon-chevron-left-solid" />
  </span>
);

BackButton.propTypes = {
  onClick: PropTypes.func.isRequired,
};

export default BackButton;
