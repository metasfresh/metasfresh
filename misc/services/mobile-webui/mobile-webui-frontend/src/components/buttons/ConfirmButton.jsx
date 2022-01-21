import React, { useState } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import Button from './Button';

const ConfirmButton = ({ caption, promptQuestion, isUserEditable, isCancelMode, onUserConfirmed }) => {
  const [isDialogDisplayed, setDialogDisplayed] = useState(false);

  const onDialogYes = () => {
    setDialogDisplayed(false);
    onUserConfirmed();
  };

  const onDialogNo = () => {
    setDialogDisplayed(false);
  };

  const captionEffective = caption ? caption : counterpart.translate('activities.confirmButton.default.caption');

  return (
    <>
      {isDialogDisplayed && (
        <Dialog caption={captionEffective} promptQuestion={promptQuestion} onYes={onDialogYes} onNo={onDialogNo} />
      )}
      <Button
        caption={captionEffective}
        disabled={!isUserEditable || isDialogDisplayed}
        isDanger={isCancelMode}
        onClick={() => setDialogDisplayed(true)}
      />
    </>
  );
};

/**
 * @typedef {object} Props Component props
 * @prop {string} caption
 * @prop {string} promptQuestion
 * @prop {bool} isUserEditable
 * @prop {bool} isCancelMode - controls if button is of rejection type (red instead of green)
 * @prop {func} onUserConfirmed
 */
ConfirmButton.propTypes = {
  caption: PropTypes.string,
  promptQuestion: PropTypes.string,
  isUserEditable: PropTypes.bool,
  isCancelMode: PropTypes.bool,
  //
  onUserConfirmed: PropTypes.func.isRequired,
};

const Dialog = ({ caption, promptQuestion, onYes, onNo }) => {
  const promptQuestionEffective = promptQuestion
    ? promptQuestion
    : counterpart.translate('activities.confirmButton.default.promptQuestion');

  return (
    <div className="prompt-dialog-screen">
      <article className="message confirm-box is-dark">
        <div className="message-header">
          <p>{caption}</p>
        </div>
        <div className="message-body">
          <strong>{promptQuestionEffective}</strong>
          <div>&nbsp;</div>
          <div className="buttons is-centered">
            <button className="button is-success confirm-button" onClick={onYes}>
              {counterpart.translate('activities.confirmButton.default.yes')}
            </button>
            <button className="button is-danger confirm-button" onClick={onNo}>
              {counterpart.translate('activities.confirmButton.default.no')}
            </button>
          </div>
        </div>
      </article>
    </div>
  );
};

Dialog.propTypes = {
  caption: PropTypes.string.isRequired,
  promptQuestion: PropTypes.string,
  onYes: PropTypes.func.isRequired,
  onNo: PropTypes.func.isRequired,
};

export default ConfirmButton;
