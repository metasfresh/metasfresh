import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { detect } from 'detect-browser';

import LoginForm from '../components/app/LoginForm';

const BROWSER = detect();

class Login extends Component {
  constructor(props) {
    super(props);
  }

  UNSAFE_componentWillMount() {
    const { logged, dispatch } = this.props;
    if (logged) {
      dispatch(push('/'));
    }
  }

  browserSupport = (...supportedBrowsers) => {
    const userBrowser = BROWSER.name;
    let isSupported = false;

    supportedBrowsers.map(browser => {
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

Login.propTypes = {
  dispatch: PropTypes.func.isRequired,
  logged: PropTypes.bool,
  redirect: PropTypes.string,
  splat: PropTypes.string,
  token: PropTypes.string,
};

export default connect()(Login);
