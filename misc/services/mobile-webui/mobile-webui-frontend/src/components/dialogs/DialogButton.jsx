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
  icon,
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
      className={cx('button', className, { 'has-dialog-icon': !!icon })}
      disabled={disabled}
      type={type}
      onClick={fireOnClick}
    >
      {icon ? (
        <>
          {/* span = stable React-owned parent: FontAwesome swaps the <i> for an <svg> in place (see HardwareModePanel) */}
          <span className="dialog-button-icon">
            <i className={cx('fas', icon)} aria-hidden="true" />
          </span>
          {caption}
        </>
      ) : (
        caption
      )}
    </button>
  );
};

DialogButton.propTypes = {
  id: PropTypes.string,
  testId: PropTypes.string,
  captionKey: PropTypes.string,
  caption: PropTypes.string,
  /** FontAwesome icon class (e.g. `fa-barcode`) shown left of the caption; none by default (the markup is then the plain caption) */
  icon: PropTypes.string,
  className: PropTypes.string,
  disabled: PropTypes.bool,
  type: PropTypes.oneOf(['button', 'submit']),
  onClick: PropTypes.func, // optional for a type="submit" button: the form's onSubmit handles it
};

export default DialogButton;
