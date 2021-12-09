import counterpart from 'counterpart';
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { checkLoginRequest } from '../../api/login';
import { CONNECTION_ERROR_RETRY_INTERVAL_MILLIS } from '../../constants/Constants';
import { getCurrentActiveLanguage } from '../../utils/locale';
class ErrorScreen extends Component {
  constructor(props) {
    super(props);
    this.intervalId = null;
    this.activeLang = getCurrentActiveLanguage();
  }

  pingServer = async () => {
    checkLoginRequest()
      .then((response) => {
        if (response && response.status === 200) {
          window.location.reload();
        }
      })
      .catch((e) => console.error('checkLoginRequest error:', e));
  };

  componentDidMount() {
    const { errorType } = this.props;
    if (errorType) {
      this.intervalId = setInterval(
        this.pingServer,
        CONNECTION_ERROR_RETRY_INTERVAL_MILLIS
      );
    }
  }

  componentWillUnmount() {
    this.intervalId && clearInterval(this.intervalId);
  }

  render() {
    const { errorType } = this.props;
    const title = counterpart.translate(`window.error.${errorType}.title`);
    const description = counterpart.translate(
      `window.error.${errorType}.description`
    );

    return (
      <div className="screen-freeze">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    );
  }
}

ErrorScreen.propTypes = {
  errorType: PropTypes.string,
};

export default ErrorScreen;
