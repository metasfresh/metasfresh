import PropTypes from 'prop-types';
import React from 'react';
import { useSelector } from 'react-redux';
import LoginForm from '../components/login/LoginForm';
import ErrorScreen from '../components/app/ErrorScreen';
import { isBrowserSupported } from '../utils/browser';
import { useAuth } from '../hooks/useAuth';
import { Redirect } from 'react-router-dom';

const Login = () => {
  const auth = useAuth();
  const { connectionErrorType } = useSelector((state) => ({
    connectionErrorType: state.appHandler?.connectionErrorType ?? '',
  }));

  if (auth.isLoggedIn) {
    console.log('Already logged in, redirecting to /');
    return <Redirect to="/" />;
  }

  const isYourBrowserSupported = isBrowserSupported();
  return (
    <div className="fullscreen">
      <div className="login-container">
        <LoginForm auth={auth} />
        {!isYourBrowserSupported && (
          <div className="browser-warning">
            <p>Your browser might be not fully supported.</p>
            <p>Please try Chrome in case of any errors.</p>
          </div>
        )}
      </div>
      {connectionErrorType && <ErrorScreen errorType={connectionErrorType} />}
    </div>
  );
};

Login.propTypes = {
  auth: PropTypes.object,
};

export default Login;
