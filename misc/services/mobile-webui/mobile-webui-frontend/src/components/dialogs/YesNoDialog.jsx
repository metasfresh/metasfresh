import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React from 'react';

const YesNoDialog = ({ caption, promptQuestion, onYes, onNo }) => {
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

YesNoDialog.propTypes = {
  caption: PropTypes.string.isRequired,
  promptQuestion: PropTypes.string,
  onYes: PropTypes.func.isRequired,
  onNo: PropTypes.func.isRequired,
};

export default YesNoDialog;
