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

  const fireOnClick = uiTrace.traceFunction(onClick, { eventName: 'dialogButtonClick', captionKey, caption });

  return (
    <button id={id} data-testid={testId} className={cx('button', className)} disabled={disabled} onClick={fireOnClick}>
      {caption}
    </button>
  );
};

DialogButton.propTypes = {
  id: PropTypes.string,
  testId: PropTypes.string,
  captionKey: PropTypes.string.isRequired,
  caption: PropTypes.string,
  className: PropTypes.string,
  disabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

export default DialogButton;
