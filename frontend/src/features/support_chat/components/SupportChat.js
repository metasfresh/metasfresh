import React, { useState } from 'react';
import { SupportChatPopup } from './SupportChatPopup';
import { SupportChatButton } from './SupportChatButton';

export const SupportChat = () => {
  const [isPopupDisplayed, setIsPopupDisplayed] = useState(true);

  const toggleChatPopup = () => {
    console.log('toggleChatPopup', { isPopupDisplayed });
    setIsPopupDisplayed(!isPopupDisplayed);
  };

  return (
    <div className="chat">
      <div className="space" />
      {isPopupDisplayed && <SupportChatPopup />}
      <SupportChatButton onClick={toggleChatPopup} />
    </div>
  );
};
