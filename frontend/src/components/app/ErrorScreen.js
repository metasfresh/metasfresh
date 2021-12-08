import counterpart from 'counterpart';
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { checkLoginRequest } from '../../api/login';
import { CONNECTION_ERROR_RETRY_INTERVAL_MILLIS } from '../../constants/Constants';
class ErrorScreen extends Component {
  constructor(props) {
    super(props);
    this.intervalId = null;
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

  getLineFromCounterpart = (targetLine) => {
    const { errorType } = this.props;
    switch (errorType) {
      case 'badGateway':
        return counterpart.translate(`window.error.badGateway.${targetLine}`);
      default:
        return counterpart.translate(`window.error.noStatus.${targetLine}`);
    }
  };

  checkNavigatorLanguage = () => {
    const navigatorLanguage = navigator.language;
    if (['de', 'de-CH', 'de-AT', 'de-LU', 'de-LI'].includes(navigatorLanguage))
      return 'de_DE';
    if (
      [
        'en',
        'en-US',
        'en-EG',
        'en-AU',
        'en-GB',
        'en-CA',
        'en-NZ',
        'en-IE',
        'en-ZA',
        'en-JM',
        'en-BZ',
        'en-TT',
      ].includes(navigatorLanguage)
    )
      return 'en_US';
    return 'en_US';
  };

  getOfflineLineMsg = (targetLine) => {
    const { errorType } = this.props;
    let targetKey = '';
    switch (errorType) {
      case 'badGateway':
        if (targetLine === 'line1') targetKey = 'BAD_GATEWAY_LINE1';
        if (targetLine === 'line2') targetKey = 'BAD_GATEWAY_LINE2';
        break;
      default:
        if (targetLine === 'line1') targetKey = 'OFFLINE_MESSAGE_LINE1';
        if (targetLine === 'line2') targetKey = 'OFFLINE_MESSAGE_LINE1';
    }
    const navigatorLanguage = this.checkNavigatorLanguage();
    return counterpart.translate(`offline.${navigatorLanguage}.${targetKey}`);
  };

  render() {
    let line1 = this.getLineFromCounterpart('title');
    let line2 = this.getLineFromCounterpart('description');

    if (line1.includes('{lang.')) {
      line1 = this.getOfflineLineMsg('line1');
    }

    if (line2.includes('{lang.')) {
      line2 = this.getOfflineLineMsg('line2');
    }

    return (
      <div className="screen-freeze">
        <h3>{line1}</h3>
        <p>{line2}</p>
      </div>
    );
  }
}

ErrorScreen.propTypes = {
  errorType: PropTypes.string,
};

export default ErrorScreen;
