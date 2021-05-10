import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { detect } from 'detect-browser';

import LoginForm from '../components/app/LoginForm';

const BROWSER = detect();

/**
 * @file Class based component.
 * @module Login
 * @extends Component
 */
class Login extends Component {
  /**
   * @method browserSupport
   * @summary ToDo: Describe the method.
   */
  browserSupport = (...supportedBrowsers) => {
    const userBrowser = BROWSER !== null ? BROWSER.name : 'chrome';
    let isSupported = false;

    supportedBrowsers.map((browser) => {
      if (userBrowser === browser) {
        isSupported = true;
      }
    });
    return isSupported;
  };

  render() {
    const { redirect, auth, splat, token } = this.props;
    const isYourBrowserSupported = this.browserSupport('chrome');
    const component = <LoginForm {...{ redirect, auth, token }} path={splat} />;

    return (
      <div className="fullscreen">
        <div className="login-container">
          {component}
          {!isYourBrowserSupported && (
            <div className="browser-warning">
              <p>Your browser might be not fully supported.</p>
              <p>Please try Chrome in case of any errors.</p>
            </div>
          )}
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {string} redirect
 * @prop {string} splat
 * @prop {string} token
 */
Login.propTypes = {
  redirect: PropTypes.string,
  splat: PropTypes.string,
  token: PropTypes.string,
  auth: PropTypes.object,
};

export default Login;
