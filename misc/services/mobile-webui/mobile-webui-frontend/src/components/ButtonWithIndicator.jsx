import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import Indicator from './Indicator';

class ButtonWithIndicator extends PureComponent {
  render() {
    const { caption, completeStatus, children } = this.props;

    return (
      <div className="full-size-btn">
        <div className="left-btn-side" />
        <div className="caption-btn">
          <div className="rows">
            <div className="row is-full pl-5">{caption}</div>
            {children}
          </div>
        </div>
        <div className={cx('right-btn-side', { 'pt-4': children })}>
          <Indicator completeStatus={completeStatus} />
        </div>
      </div>
    );
  }
}

ButtonWithIndicator.propTypes = {
  caption: PropTypes.string.isRequired,
  children: PropTypes.node,
  completeStatus: PropTypes.string,
};

export default ButtonWithIndicator;
