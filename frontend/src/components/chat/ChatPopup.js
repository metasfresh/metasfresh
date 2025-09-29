import React, { useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { postChatMessage } from '../../api/chat';
import { useWebsocketTopic } from '../../hooks/useWebsocketTopic';
import { useUserSession } from '../../hooks/useUserSession';
import { v4 as uuidv4 } from 'uuid';

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

      const chatLine = {
        id: uuidv4(),
        author: 'support',
        message: message,
        timestamp: Date.now(),
      };

      setChatLines((chatLines) => [...chatLines, chatLine]);
    },
  });

  const onInputTextChanged = (e) => {
    setInputText(e.target.value);
  };

  const sendMessage = () => {
    if (!inputText) return;

    const newLine = {
      id: uuidv4(),
      author: 'me',
      message: inputText,
      timestamp: Date.now(),
      pending: true,
    };

    setChatLines((chatLines) => [...chatLines, newLine]);
    setInputText('');

    postChatMessage({ message: newLine.message }).then(() => {
      setChatLines((chatLines) =>
        markAsNotPending({ chatLines, id: newLine.id })
      );
    });
  };

  return (
    <div className="chat-popup">
      <div className="chat-content">
        {chatLines.map((chatLine) => (
          <ChatLine
            key={chatLine.id}
            id={chatLine.id}
            author={chatLine.author}
            message={chatLine.message}
            timestamp={chatLine.timestamp}
            pending={chatLine.pending}
          />
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

//
//
// ------------------------------------------------------------------------
//
//
//

const ChatLine = ({ id, author, message, timestamp, pending }) => {
  return (
    <div className="chat-line" title={`${timestamp} -- ${id}`}>
      {author}:{message}
      {pending && <span>(pending)</span>}
    </div>
  );
};
ChatLine.propTypes = {
  text: PropTypes.string,
};

//
//
// ------------------------------------------------------------------------
//
//
//

const markAsNotPending = ({ chatLines, id }) => {
  return chatLines.map((chatLine) =>
    chatLine.id === id ? { ...chatLine, pending: false } : chatLine
  );
};
