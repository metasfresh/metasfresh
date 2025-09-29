import React, { useState } from 'react';
import { ChatPopup } from './ChatPopup';
import { ChatButton } from './ChatButton';

export const Chat = () => {
  const [isPopupDisplayed, setIsPopupDisplayed] = useState(true);

  const toggleChatPopup = () => {
    console.log('toggleChatPopup', { isPopupDisplayed });
    setIsPopupDisplayed(!isPopupDisplayed);
  };

  return (
    <div className="chat">
      <div className="space" />
      {isPopupDisplayed && <ChatPopup />}
      <ChatButton onClick={toggleChatPopup} />
    </div>
  );
};
