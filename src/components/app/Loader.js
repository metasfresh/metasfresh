import React from 'react';
import { CSSTransition } from 'react-transition-group';

/**
 * @file Class based component.
 * @module Loader
 * @extends Component
 */
const Loader = () => (
  <div className="order-list-loader text-center">
    <CSSTransition
      className="rotate icon-rotate"
      timeout={{ exit: 1000, enter: 1000 }}
    >
      <div>
        <i className="meta-icon-settings" />
      </div>
    </CSSTransition>
  </div>
);

export default Loader;
