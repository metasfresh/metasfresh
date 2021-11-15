import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import Indicator from './Indicator';

const ButtonWithIndicator = ({ caption, showWarningSign, completeStatus, children }) => (
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
);

ButtonWithIndicator.propTypes = {
  caption: PropTypes.string.isRequired,
  children: PropTypes.node,
  completeStatus: PropTypes.string,
  showWarningSign: PropTypes.bool,
};

export default ButtonWithIndicator;
