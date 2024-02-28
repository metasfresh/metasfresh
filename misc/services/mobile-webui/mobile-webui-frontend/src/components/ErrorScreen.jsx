import React from 'react';
import PropTypes from 'prop-types';

const ErrorScreen = ({ error, resetErrorBoundary }) => {
  return (
    <div className="error-screen">
      <div className="message">{error}</div>
      <button className="button" onClick={() => resetErrorBoundary()}>
        Retry...
      </button>
    </div>
  );
};
ErrorScreen.propTypes = {
  error: PropTypes.string,
  resetErrorBoundary: PropTypes.func,
};

export default ErrorScreen;
