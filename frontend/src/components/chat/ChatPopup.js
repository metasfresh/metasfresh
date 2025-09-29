import React, { useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { postChatMessage } from '../../api/chat';
import { useWebsocketTopic } from '../../hooks/useWebsocketTopic';
import { useUserSession } from '../../hooks/useUserSession';

export const ChatPopup = () => {
  const [inputText, setInputText] = useState('');
  const [chatLines, setChatLines] = useState([]);
  const inputTextRef = useRef(null);

  const { userProfileId } = useUserSession();

  useWebsocketTopic({
    disabled: !userProfileId,
    topic: `/chat/${userProfileId}`,
    onMessage: ({ message }) => {
      console.log(`Got message`, { message });
      setChatLines((chatLines) => [...chatLines, message]);
    },
  });

  const onInputTextChanged = (e) => {
    setInputText(e.target.value);
  };

  const sendMessage = () => {
    if (!inputText) return;

    setChatLines([...chatLines, inputText]);
    setInputText('');

    postChatMessage({ message: inputText });
  };

  return (
    <div className="chat-popup">
      <div className="chat-content">
        {chatLines.map((chatLine, index) => (
          <ChatLine key={index} text={chatLine} />
        ))}
      </div>
      <div className="chat-input-container">
        <textarea
          ref={inputTextRef}
          autoComplete="off"
          className="input-field js-input-field chat-popup-input"
          onKeyPress={(e) => onInputTextChanged(e)}
          onChange={(e) => onInputTextChanged(e)}
          tabIndex="0"
          value={inputText}
        />
        <button
          className="btn btn-sm btn-block btn-meta-success chat-button-send"
          onClick={(e) => {
            e.preventDefault();
            inputTextRef?.current?.focus();
            if (inputText) {
              sendMessage();
            }
          }}
          tabIndex="1"
        >
          Send
        </button>
      </div>
    </div>
  );
};

export const ChatLine = ({ text }) => {
  return <div className="chat-line">{text}</div>;
};
ChatLine.propTypes = {
  text: PropTypes.string,
};
