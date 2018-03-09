import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

export default class RawListDropdown extends PureComponent {
  static propTypes = {
    isListEmpty: PropTypes.bool,
    offsetWidth: PropTypes.number,
    loading: PropTypes.bool,
    childRef: PropTypes.func,
    children: PropTypes.any,
  };

  render() {
    const { isListEmpty, offsetWidth, loading, children } = this.props;

    return (
      <div
        className="input-dropdown-list"
        style={{ width: `${offsetWidth}px` }}
        ref={this.props.childRef}
      >
        {isListEmpty &&
          loading === false && (
            <div className="input-dropdown-list-header">
              There is no choice available
            </div>
          )}
        {loading &&
          isListEmpty && (
            <div className="input-dropdown-list-header">
              <ReactCSSTransitionGroup
                transitionName="rotate"
                transitionEnterTimeout={1000}
                transitionLeaveTimeout={1000}
              >
                <div className="rotate icon-rotate">
                  <i className="meta-icon-settings" />
                </div>
              </ReactCSSTransitionGroup>
            </div>
          )}
        {children}
      </div>
    );
  }
}
