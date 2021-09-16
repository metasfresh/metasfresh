import counterpart from 'counterpart';
import React, { Component } from 'react';
import PropTypes from 'prop-types';

const OFFLINE_MESSAGE_LINE1 = 'Connection lost.';
const OFFLINE_MESSAGE_LINE2 =
  'There are some connection issues. ' +
  'Check connection and try to refresh the page.';
const BAD_GATEWAY_LINE1 = 'Instance is not available';
const BAD_GATEWAY_LINE2 = 'There are some connection issues.';

class ErrorScreen extends Component {
  constructor(props) {
    super(props);
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

  getLineMsg = (targetLine) => {
    const { errorType } = this.props;
    switch (errorType) {
      case 'badGateway':
        if (targetLine === 'line1') return BAD_GATEWAY_LINE1;
        if (targetLine === 'line2') return BAD_GATEWAY_LINE2;
        break;
      default:
        if (targetLine === 'line1') return OFFLINE_MESSAGE_LINE1;
        if (targetLine === 'line2') return OFFLINE_MESSAGE_LINE2;
    }
  };

  render() {
    let line1 = this.getLineFromCounterpart('title');
    let line2 = this.getLineFromCounterpart('description');

    if (line1.includes('{lang.')) {
      line1 = this.getLineMsg('line1');
    }

    if (line2.includes('{lang.')) {
      line2 = this.getLineMsg('line2');
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
