import counterpart from 'counterpart';
import React, { Component } from 'react';

const OFFLINE_MESSAGE_LINE1 = 'Connection lost.';
const OFFLINE_MESSAGE_LINE2 =
  'There are some connection issues. ' +
  'Check connection and try to refresh the page.';

class ErrorScreen extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    let line1 = counterpart.translate('window.error.noStatus.title');
    let line2 = counterpart.translate('window.error.noStatus.description');

    if (!line1) {
      line1 = OFFLINE_MESSAGE_LINE1;
    }

    if (!line2) {
      line2 = OFFLINE_MESSAGE_LINE2;
    }

    return (
      <div className="screen-freeze">
        <h3>{line1}</h3>
        <p>{line2}</p>
      </div>
    );
  }
}

export default ErrorScreen;
