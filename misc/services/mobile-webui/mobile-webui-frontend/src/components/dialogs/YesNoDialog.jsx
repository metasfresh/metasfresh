import PropTypes from 'prop-types';
import React from 'react';

import { trl } from '../../utils/translations';

const YesNoDialog = ({ promptQuestion, onYes, onNo }) => {
  const promptQuestionEffective = promptQuestion
    ? promptQuestion
    : trl('activities.confirmButton.default.promptQuestion');

  return (
    <div className="prompt-dialog">
      <article className="message is-dark">
        <div className="message-body">
          <strong>{promptQuestionEffective}</strong>

          <div className="buttons is-centered">
            <button className="button is-danger" onClick={onYes}>
              {trl('activities.confirmButton.default.yes')}
            </button>
            <button className="button is-success" onClick={onNo}>
              {trl('activities.confirmButton.default.no')}
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
