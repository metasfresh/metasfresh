import counterpart from 'counterpart';
import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import FileInput from './FileInput';

const Attachments = ({ attachments, onFileAttached }) => {
  const prevAttachments = useRef();
  useEffect(() => {
    if (prevAttachments.current !== attachments) {
      prevAttachments.current = attachments;
      clearFile();
    }
  }, [attachments]);

  const clearFile = () => {
    document.getElementsByClassName('attachment-input')[1].value = '';
  };

  return (
    <div className="email-attachments-wrapper">
      {attachments &&
        attachments.map((item, index) => {
          return (
            <div className="attachment" key={index}>
              <div className="attachment-text">{item.caption}</div>
              <div className="input-icon input-icon-lg" />
            </div>
          );
        })}
      <div>
        <span className="add-attachment">
          <form>
            <i className="meta-icon-attachments" />
            <FileInput
              name="myImage"
              placeholder={counterpart.translate('window.email.addattachment')}
              className="attachment-input"
              onChange={onFileAttached}
            />
          </form>
        </span>
      </div>
    </div>
  );
};

Attachments.propTypes = {
  attachments: PropTypes.array,
  onFileAttached: PropTypes.func.isRequired,
};

export default Attachments;
