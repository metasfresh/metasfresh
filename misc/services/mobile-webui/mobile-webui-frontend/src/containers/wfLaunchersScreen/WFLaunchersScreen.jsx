import React, { useEffect, useState } from 'react';
import { useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { map } from 'lodash';

import { getLaunchers, useLaunchersWebsocket } from '../../api/launchers';
import { populateLaunchersComplete, populateLaunchersStart } from '../../actions/LauncherActions';
import { getApplicationLaunchers } from '../../reducers/launchers';

import WFLauncherButton from './WFLauncherButton';
import { getTokenFromState } from '../../reducers/appHandler';
import { getApplicationInfoById } from '../../reducers/applications';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable, toQRCodeObject, toQRCodeString } from '../../utils/huQRCodes';
import WFLaunchersFilters from './WFLaunchersFilters';

const WFLaunchersScreen = () => {
  const {
    params: { applicationId },
  } = useRouteMatch();

  const [currentPanel, setCurrentPanel] = useState('default');
  //  const [showQRCodeScanner, setShowQRCodeScanner] = useState(false);
  const { requiresLaunchersQRCodeFilter } = useSelector((state) => getApplicationInfoById({ state, applicationId }));
  const {
    filterByQRCode: currentFilterByQRCode,
    requestTimestamp,
    list: launchers,
  } = useSelector((state) => getApplicationLaunchers(state, applicationId));

  const filterByQRCode = requiresLaunchersQRCodeFilter ? currentFilterByQRCode : null;
  const isAllowQueryingLaunchers = !requiresLaunchersQRCodeFilter || !!filterByQRCode;

  //
  // Load application launchers
  const onNewLaunchers = ({ applicationId, applicationLaunchers }) => {
    setCurrentPanel('default');
    dispatch(populateLaunchersComplete({ applicationId, applicationLaunchers }));
  };
  const dispatch = useDispatch();
  useEffect(() => {
    if (!isAllowQueryingLaunchers) {
      console.log('Skip fetching querying launchers is prohibited');
      return;
    }

    getLaunchers(applicationId, filterByQRCode).then((applicationLaunchers) => {
      onNewLaunchers({ applicationId, applicationLaunchers });
    });
  }, [isAllowQueryingLaunchers, applicationId, toQRCodeString(filterByQRCode), requestTimestamp]);

  //
  // Connect to WebSocket topic
  const userToken = useSelector((state) => getTokenFromState(state));
  useLaunchersWebsocket({
    enabled: isAllowQueryingLaunchers,
    userToken,
    applicationId,
    filterByQRCode,
    onWebsocketMessage: ({ applicationId, applicationLaunchers }) =>
      onNewLaunchers({ applicationId, applicationLaunchers }),
  });

  //const showQRCodeScannerEffective = currentPanel === 'scanQRCode' || !isAllowQueryingLaunchers;

  return (
    <div className="container launchers-container">
      {currentPanel === 'scanQRCode' && (
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => {
            dispatch(populateLaunchersStart({ applicationId, filterByQRCode: toQRCodeObject(scannedBarcode) }));
          }}
        />
      )}
      {currentPanel === 'default' && requiresLaunchersQRCodeFilter && (
        <div className="mb-5">
          <ButtonWithIndicator
            caption={filterByQRCode ? toQRCodeDisplayable(filterByQRCode) : 'Scan barcode'}
            onClick={() => setCurrentPanel('scanQRCode')}
          />
        </div>
      )}
      {currentPanel === 'default' && !requiresLaunchersQRCodeFilter && (
        <>
          <ButtonWithIndicator caption="Filter..." disabled={false} onClick={() => setCurrentPanel('filters')} />
          <br />
        </>
      )}
      {currentPanel === 'filters' && (
        <WFLaunchersFilters applicationId={applicationId} onDone={() => setCurrentPanel('default')} />
      )}
      {currentPanel === 'default' &&
        launchers &&
        map(launchers, (launcher, index) => {
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
