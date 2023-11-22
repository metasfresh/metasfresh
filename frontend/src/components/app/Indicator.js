import PropTypes from 'prop-types';
import React from 'react';

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

  return (
    <div className="window-indicator-container">
      <div
        className={`bar ${isDocumentNotSaved ? 'error' : ''} ${
          !isDocumentNotSaved && indicator ? `${indicator}` : ''
        }`}
      />
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
        <div className="container-fluid indicator-error-message">&nbsp;</div>
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
