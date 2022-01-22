import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React from 'react';

const YesNoDialog = ({ promptQuestion, onYes, onNo }) => {
  const promptQuestionEffective = promptQuestion
    ? promptQuestion
    : counterpart.translate('activities.confirmButton.default.promptQuestion');

  return (
    <div className="prompt-dialog-screen">
      <article className="message is-dark">
        <div className="message-body">
          <strong>{promptQuestionEffective}</strong>

          <div className="buttons is-centered">
            <button className="button is-danger" onClick={onYes}>
              {counterpart.translate('activities.confirmButton.default.yes')}
            </button>
            <button className="button is-success" onClick={onNo}>
              {counterpart.translate('activities.confirmButton.default.no')}
            </button>
          </div>
        </div>
      </article>
    </div>
  );
};

YesNoDialog.propTypes = {
  promptQuestion: PropTypes.string,
  onYes: PropTypes.func.isRequired,
  onNo: PropTypes.func.isRequired,
};

export default YesNoDialog;
