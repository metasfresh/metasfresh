import React, { Component } from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

/**
 * @file Class based component.
 * @module Loader
 * @extends Component
 */
class Loader extends Component {
  constructor(props) {
    super(props);
  }

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    return (
      <div className="order-list-loader text-center">
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
    );
  }
}

export default Loader;
