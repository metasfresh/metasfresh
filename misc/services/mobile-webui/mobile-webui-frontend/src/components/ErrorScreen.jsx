import React from 'react';
import PropTypes from 'prop-types';
import { trl } from '../utils/translations';

const ErrorScreen = ({ /*error,*/ resetErrorBoundary }) => {
  const userFriendlyError = trl('error.PleaseTryAgain');

  return (
    <div className="error-screen">
      <div className="message">{userFriendlyError}</div>
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
