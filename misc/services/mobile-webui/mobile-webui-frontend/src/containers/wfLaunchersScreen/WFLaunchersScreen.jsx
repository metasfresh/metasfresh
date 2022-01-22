import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';
import { map } from 'lodash';

import { populateLaunchers } from '../../actions/LauncherActions';
import { getLaunchers } from '../../api/launchers';
import { selectApplicationLaunchersFromState } from '../../reducers/launchers';
import WFLauncherButton from './WFLauncherButton';
import * as ws from '../../utils/websocket';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { gotoAppLaunchersBarcodeScanner } from '../../routes/launchers';

class WFLaunchersScreen extends Component {
  componentDidMount() {
    const { applicationId, populateLaunchers } = this.props;

    getLaunchers(applicationId).then((applicationLaunchers) => {
      populateLaunchers({ applicationId, applicationLaunchers });
    });
  }

  componentDidUpdate() {
    if (!this.wsClient) {
      const { userToken, applicationId } = this.props;
      this.wsClient = ws.connectAndSubscribe({
        topic: `/v2/userWorkflows/launchers/${userToken}/${applicationId}`,
        onWebsocketMessage: this.onWebsocketMessage,
      });
    }
  }

  componentWillUnmount() {
    ws.disconnectClient(this.wsClient);
    this.wsClient = null;
  }

  onWebsocketMessage = (message) => {
    const { populateLaunchers, applicationId } = this.props;
    const applicationLaunchers = JSON.parse(message.body);
    populateLaunchers({ applicationId, applicationLaunchers });
  };

  onScanBarcodeButtonClicked = () => {
    const { applicationId, gotoAppLaunchersBarcodeScanner } = this.props;
    gotoAppLaunchersBarcodeScanner(applicationId);
  };

  render() {
    const { applicationLaunchers } = this.props;

    return (
      <div className="container launchers-container">
        {applicationLaunchers.scanBarcodeToStartJobSupport && (
          <>
            <div className="mt-0">
              <ButtonWithIndicator caption="Scan barcode" onClick={this.onScanBarcodeButtonClicked} />
            </div>
            <br />
          </>
        )}

        {map(applicationLaunchers.list, (launcher, index) => {
          const key = launcher.startedWFProcessId ? 'started-' + launcher.startedWFProcessId : 'new-' + index;
          return (
            <WFLauncherButton
              key={key}
              applicationId={launcher.applicationId}
              caption={launcher.caption}
              startedWFProcessId={launcher.startedWFProcessId}
              wfParameters={launcher.wfParameters}
              showWarningSign={launcher.showWarningSign}
            />
          );
        })}
      </div>
    );
  }
}

WFLaunchersScreen.propTypes = {
  //
  // Props
  userToken: PropTypes.string.isRequired,
  applicationId: PropTypes.string.isRequired,
  applicationLaunchers: PropTypes.object.isRequired,
  //
  // Actions
  populateLaunchers: PropTypes.func.isRequired,
  gotoAppLaunchersBarcodeScanner: PropTypes.func.isRequired,
};

const mapStateToProps = (state, { match }) => {
  const { applicationId } = match.params;

  return {
    userToken: state.appHandler.token,
    applicationId,
    applicationLaunchers: selectApplicationLaunchersFromState(state, applicationId),
  };
};

export default withRouter(
  connect(mapStateToProps, { populateLaunchers, gotoAppLaunchersBarcodeScanner })(WFLaunchersScreen)
);
