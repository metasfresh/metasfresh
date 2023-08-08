import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import * as CompleteStatus from '../../constants/CompleteStatus';

const ButtonWithIndicator = ({ caption, showWarningSign, isDanger, completeStatus, disabled, onClick, children }) => {
  const indicatorClassName = getIndicatorClassName(completeStatus);

  return (
    <button
      className={cx('button is-outlined is-fullwidth complete-btn', { 'is-danger': isDanger })}
      disabled={!!disabled}
      onClick={onClick}
    >
      <div className="full-size-btn">
        <div className="left-btn-side">
          {showWarningSign && (
            <span className="icon">
              <i className="fas fa-exclamation-triangle warning-sign" />
            </span>
          )}
        </div>
        <div className="caption-btn">
          <div className="rows">
            <div className="row is-full pl-5">{caption}</div>
            {children}
          </div>
        </div>
        {indicatorClassName && (
          <div className={cx('right-btn-side', { 'pt-4': children })}>
            <span className={indicatorClassName} />
          </div>
        )}
      </div>
    </button>
  );
};

const getIndicatorClassName = (completeStatus) => {
  switch (completeStatus) {
    case CompleteStatus.NOT_STARTED:
      return 'indicator-red';
    case CompleteStatus.COMPLETED:
      return 'indicator-green';
    case CompleteStatus.IN_PROGRESS:
      return 'indicator-yellow';
    default:
      return '';
  }
};

ButtonWithIndicator.propTypes = {
  caption: PropTypes.string.isRequired,
  showWarningSign: PropTypes.bool,
  isDanger: PropTypes.bool,
  completeStatus: PropTypes.string,
  disabled: PropTypes.bool,
  children: PropTypes.node,
  onClick: PropTypes.func.isRequired,
};

export default ButtonWithIndicator;
