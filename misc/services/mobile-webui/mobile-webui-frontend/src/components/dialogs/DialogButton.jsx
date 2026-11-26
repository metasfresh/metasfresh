import * as uiTrace from '../../utils/ui_trace';
import cx from 'classnames';
import { trl } from '../../utils/translations';
import React from 'react';
import PropTypes from 'prop-types';
import { computeId, computeTestId } from '../../utils/testing_support';

const DialogButton = ({
  id: idParam,
  testId: testIdParam,
  captionKey,
  caption: captionParam,
  className,
  disabled,
  type = 'button', // not the HTML default 'submit': only a button that opts in submits a surrounding form
  onClick,
}) => {
  const testId = computeTestId({ testIdParam, captionKey, suffix: 'button' });
  const id = computeId({ idParam, testIdParam, captionKey, suffix: 'button' });
  let caption = '';
  if (captionParam) {
    caption = captionParam;
  } else if (captionKey) {
    caption = trl(captionKey);
  }

  const fireOnClick = onClick
    ? uiTrace.traceFunction(onClick, { eventName: 'dialogButtonClick', captionKey, caption })
    : undefined;

  return (
    <button
      id={id}
      data-testid={testId}
      type={type}
      className={cx('button', className)}
      disabled={disabled}
      onClick={fireOnClick}
    >
      {caption}
    </button>
  );
};

DialogButton.propTypes = {
  id: PropTypes.string,
  testId: PropTypes.string,
  captionKey: PropTypes.string,
  caption: PropTypes.string,
  className: PropTypes.string,
  disabled: PropTypes.bool,
  type: PropTypes.oneOf(['button', 'submit']),
  onClick: PropTypes.func, // optional for a type="submit" button: the form's onSubmit handles it
};

export default DialogButton;
