import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import * as uiTrace from '../../utils/ui_trace';

const Button = ({ testId, caption, onClick: onClickParam, disabled = false, isDanger = false }) => {
  const onClick = uiTrace.traceFunction(onClickParam, { eventName: 'buttonClick', caption, isDanger });

  return (
    <button
      data-testid={testId}
      className={cx('button is-outlined complete-btn is-fullwidth', { 'is-danger': isDanger })}
      onClick={onClick}
      disabled={!!disabled}
    >
      <div className="full-size-btn">
        <div className="left-btn-side" />
        <div className="caption-btn">
          <div className="rows">
            <div className="row">{caption}</div>
          </div>
        </div>
      </div>
    </button>
  );
};

Button.propTypes = {
  testId: PropTypes.string,
  caption: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
  isDanger: PropTypes.bool,
};

export default Button;
