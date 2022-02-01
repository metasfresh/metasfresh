import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../utils/translations';

import Button from './Button';
import YesNoDialog from '../dialogs/YesNoDialog';

const ConfirmButton = ({ caption, promptQuestion, isUserEditable, isDangerousAction, onUserConfirmed }) => {
  const [isDialogDisplayed, setDialogDisplayed] = useState(false);

  const onDialogYes = () => {
    setDialogDisplayed(false);
    onUserConfirmed();
  };

  const onDialogNo = () => {
    setDialogDisplayed(false);
  };

  const captionEffective = caption ? caption : trl('activities.confirmButton.default.caption');

  return (
    <>
      {isDialogDisplayed && <YesNoDialog promptQuestion={promptQuestion} onYes={onDialogYes} onNo={onDialogNo} />}
      <Button
        caption={captionEffective}
        disabled={!isUserEditable || isDialogDisplayed}
        isDanger={isDangerousAction}
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
  isDangerousAction: PropTypes.bool,
  //
  onUserConfirmed: PropTypes.func.isRequired,
};

export default ConfirmButton;
