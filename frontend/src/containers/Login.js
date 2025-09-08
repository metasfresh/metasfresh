import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { detect } from 'detect-browser';
import { connect } from 'react-redux';
import LoginForm from '../components/login/LoginForm';
import ErrorScreen from '../components/app/ErrorScreen';

const BROWSER = detect();

/**
 * @file Class based component.
 * @module Login
 * @extends Component
 */
class Login extends PureComponent {
  /**
   * @method browserSupport
   * @summary Method checking if user is using a supported browser
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
    const { auth, splat, token, connectionErrorType } = this.props;
    const isYourBrowserSupported = this.browserSupport('chrome');
    const component = <LoginForm {...{ auth, token }} path={splat} />;

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
        {connectionErrorType && <ErrorScreen errorType={connectionErrorType} />}
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  connectionErrorType: state.appHandler.connectionErrorType || '',
});

/**
 * @typedef {object} Props Component props
 * @prop {string} splat
 * @prop {string} token
 */
Login.propTypes = {
  splat: PropTypes.string,
  token: PropTypes.string,
  auth: PropTypes.object,
  connectionErrorType: PropTypes.string,
};

export default connect(mapStateToProps, null)(Login);
