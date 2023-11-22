import PropTypes from 'prop-types';
import React from 'react';
import cx from 'classnames';

/**
 * @file Indicator is a component that shows the save status to user in form of a save progress
 * line beneath the Header.
 * @module Indicator
 */
const Indicator = ({ indicator, isDocumentNotSaved, error, stackTrace }) => {
  const copyErrorToClipboard = () => {
    if (!error) return;

    let message = error;
    message += '\n';
    message += '\nLocation: ' + window.location.href;

    if (stackTrace) {
      message += '\nStacktrace: ' + stackTrace;
    }

    navigator.clipboard.writeText(message);
  };

  const indicatorEffective = isDocumentNotSaved ? 'error' : indicator;

  return (
    <div className="window-indicator-container">
      <div className={cx('bar', indicatorEffective)} />
      {error ? (
        <div className="container-fluid message-bar" title={error}>
          <span className="text">{error}</span>
          <button
            className="copy-button btn btn-meta-outline-secondary"
            onClick={copyErrorToClipboard}
          >
            <i className="meta-icon-duplicate" />
          </button>
        </div>
      ) : (
        <div className="container-fluid message-bar">&nbsp;</div>
      )}
    </div>
  );
};

/**
 * @typedef {object} Props Component props
 * @prop {string} indicator
 * @prop {bool} [isDocumentNotSaved]
 */
Indicator.propTypes = {
  indicator: PropTypes.string.isRequired,
  isDocumentNotSaved: PropTypes.bool,
  error: PropTypes.string,
  stackTrace: PropTypes.string,
};

export default Indicator;
