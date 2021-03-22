import React from 'react';
import { CSSTransition } from 'react-transition-group';
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
        <CSSTransition className="rotate" timeout={{ enter: 1000, exit: 1000 }}>
          <div>
            <div className="rotate icon-rotate">
              <i className="meta-icon-settings" />
            </div>
          </div>
        </CSSTransition>
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
