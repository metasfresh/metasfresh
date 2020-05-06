import React from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import PropTypes from 'prop-types';

/**
 * @file Fnct component.
 * @module Loader
 */
const Loader = (props) => {
  const { loaderType } = props;

  return (
    <div
      className={
        loaderType
          ? 'd-flex justify-content-center'
          : 'order-list-loader text-center'
      }
    >
      {!loaderType && (
        <ReactCSSTransitionGroup
          transitionName="rotate"
          transitionEnterTimeout={1000}
          transitionLeaveTimeout={1000}
        >
          <div className="rotate icon-rotate">
            <i className="meta-icon-settings" />
          </div>
        </ReactCSSTransitionGroup>
      )}
      {loaderType && loaderType === 'bootstrap' && (
        <div className="spinner-border text-success" role="status">
          <span className="sr-only">Loading...</span>
        </div>
      )}
    </div>
  );
};

export default Loader;

Loader.propTypes = {
  loaderType: PropTypes.string,
};

Loader.defaultProps = {
  loaderType: null,
};
