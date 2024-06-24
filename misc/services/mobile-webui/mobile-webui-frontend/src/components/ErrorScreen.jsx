import React from 'react';
import PropTypes from 'prop-types';
import { trl } from '../utils/translations';

const ErrorScreen = ({ /*error,*/ resetErrorBoundary }) => {
  const userFriendlyError = trl('error.PleaseTryAgain');

  return (
    <div className="error-screen">
      <div className="message">{userFriendlyError}</div>
      <button className="button" onClick={() => resetErrorBoundary()}>
        {trl('errorScreen.retryButton')}
      </button>
    </div>
  );
};
ErrorScreen.propTypes = {
  error: PropTypes.object,
  resetErrorBoundary: PropTypes.func,
};

export default ErrorScreen;
