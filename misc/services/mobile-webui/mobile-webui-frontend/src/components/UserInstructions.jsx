import React from 'react';
import PropTypes from 'prop-types';
import '../assets/UserInstructions.scss';

const UserInstructions = ({ text }) => {
  if (!text) {
    return null;
  }

  return <div className="user-instructions">{text}</div>;
};

UserInstructions.propTypes = {
  text: PropTypes.string,
};

export default UserInstructions;
