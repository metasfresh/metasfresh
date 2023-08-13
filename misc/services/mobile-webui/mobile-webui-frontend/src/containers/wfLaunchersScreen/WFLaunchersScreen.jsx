import React, { useEffect, useRef } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { map } from 'lodash';
import * as ws from '../../utils/websocket';

import { getLaunchers } from '../../api/launchers';
import { appLaunchersBarcodeScannerLocation } from '../../routes/launchers';
import { populateLaunchers } from '../../actions/LauncherActions';
import { getApplicationLaunchers } from '../../reducers/launchers';

import WFLauncherButton from './WFLauncherButton';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { getTokenFromState } from '../../reducers/appHandler';

const WFLaunchersScreen = () => {
  const {
    params: { applicationId },
  } = useRouteMatch();

  //
  // Load application launchers
  const dispatch = useDispatch();
  useEffect(() => {
    getLaunchers(applicationId).then((applicationLaunchers) => {
      dispatch(populateLaunchers({ applicationId, applicationLaunchers }));
    });
  }, [applicationId]);

  //
  // Connect to WebSocket topic
  const userToken = useSelector((state) => getTokenFromState(state));
  const wsClientRef = useRef(null);
  useEffect(() => {
    if (!wsClientRef.current) {
      wsClientRef.current = ws.connectAndSubscribe({
        topic: `/v2/userWorkflows/launchers/${userToken}/${applicationId}`,
        debug: false,
        onWebsocketMessage: (message) => {
          const applicationLaunchers = JSON.parse(message.body);
          dispatch(populateLaunchers({ applicationId, applicationLaunchers }));
        },
      });
    }

    return () => {
      if (wsClientRef.current) {
        ws.disconnectClient(wsClientRef.current);
        wsClientRef.current = null;
      }
    };
  }, []);

  const history = useHistory();
  const onScanBarcodeButtonClicked = () => {
    history.push(appLaunchersBarcodeScannerLocation({ applicationId }));
  };

  const applicationLaunchers = useSelector((state) => getApplicationLaunchers(state, applicationId));

  return (
    <div className="container launchers-container">
      {applicationLaunchers.scanBarcodeToStartJobSupport && (
        <>
          <div className="mt-0">
            <ButtonWithIndicator caption="Scan barcode" onClick={onScanBarcodeButtonClicked} />
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
};

export default WFLaunchersScreen;
