import React from 'react';
import PropTypes from 'prop-types';

export const ChatButton = ({ onClick }) => {
  return (
    <button className="chat-button" onClick={onClick}>
      CHAT
    </button>
  );
};

ChatButton.propTypes = {
  onClick: PropTypes.func.isRequired,
};
