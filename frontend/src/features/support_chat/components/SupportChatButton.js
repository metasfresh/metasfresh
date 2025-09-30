import React from 'react';
import PropTypes from 'prop-types';

export const SupportChatButton = ({ onClick }) => {
  return (
    <button className="chat-button" onClick={onClick}>
      CHAT
    </button>
  );
};

SupportChatButton.propTypes = {
  onClick: PropTypes.func.isRequired,
};
