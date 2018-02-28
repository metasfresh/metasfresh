import React, { Component } from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

class Loader extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="order-list-loader text-xs-center">
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
