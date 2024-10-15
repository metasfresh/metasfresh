import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../utils/translations';

import ButtonWithIndicator from './ButtonWithIndicator';
import YesNoDialog from '../dialogs/YesNoDialog';

const ConfirmButton = ({
  caption,
  promptQuestion,
  userInstructions,
  isUserEditable,
  isDangerousAction,
  isProcessing,
  completeStatus,
  onUserConfirmed,
}) => {
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
      {isDialogDisplayed && (
        <YesNoDialog
          promptQuestion={promptQuestion}
          userInstructions={userInstructions}
          onYes={onDialogYes}
          onNo={onDialogNo}
        />
      )}
      <ButtonWithIndicator
        caption={captionEffective}
        completeStatus={completeStatus}
        disabled={!isUserEditable || isDialogDisplayed}
        isDanger={isDangerousAction}
        typeFASIconName={isProcessing ? 'fa-spinner fa-spin' : null}
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
  userInstructions: PropTypes.string,
  isUserEditable: PropTypes.bool,
  isDangerousAction: PropTypes.bool,
  isProcessing: PropTypes.bool,
  completeStatus: PropTypes.string,
  //
  onUserConfirmed: PropTypes.func.isRequired,
};

export default ConfirmButton;
