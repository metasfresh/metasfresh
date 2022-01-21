import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import Indicator from './Indicator';

const ButtonWithIndicator = ({ caption, showWarningSign, completeStatus, disabled, onClick, children }) => (
  <button className="button is-outlined complete-btn" disabled={!!disabled} onClick={onClick}>
    <div className="full-size-btn">
      <div className="left-btn-side">{showWarningSign && <span className="warning-sign">⚠</span>}</div>
      <div className="caption-btn">
        <div className="rows">
          <div className="row is-full pl-5">{caption}</div>
          {children}
        </div>
      </div>
      {completeStatus && (
        <div className={cx('right-btn-side', { 'pt-4': children })}>
          <Indicator completeStatus={completeStatus} />
        </div>
      )}
    </div>
  </button>
);

ButtonWithIndicator.propTypes = {
  caption: PropTypes.string.isRequired,
  showWarningSign: PropTypes.bool,
  completeStatus: PropTypes.string,
  disabled: PropTypes.bool,
  children: PropTypes.node,
  onClick: PropTypes.func.isRequired,
};

export default ButtonWithIndicator;
