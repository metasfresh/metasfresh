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

const WFLaunchersScreen = () => {
  const {
    params: { applicationId },
  } = useRouteMatch();

  const [showQRCodeScanner, setShowQRCodeScanner] = useState(false);

  const { requiresLaunchersQRCodeFilter } = useSelector((state) => getApplicationInfoById({ state, applicationId }));

  const {
    filterByQRCode: currentFilterByQRCode,
    requestTimestamp,
    list: launchers,
  } = useSelector((state) => getApplicationLaunchers(state, applicationId));

  const filterByQRCode = requiresLaunchersQRCodeFilter ? currentFilterByQRCode : null;
  const isFilterValid = !requiresLaunchersQRCodeFilter || !!filterByQRCode;

  const onNewLaunchers = ({ applicationId, applicationLaunchers }) => {
    setShowQRCodeScanner(false);
    dispatch(populateLaunchersComplete({ applicationId, applicationLaunchers }));
  };
  //
  // Load application launchers
  const dispatch = useDispatch();
  useEffect(() => {
    if (!isFilterValid) {
      console.log('Skip fetching because QR Code is required but not yet scanned');
      return;
    }

    getLaunchers(applicationId, filterByQRCode).then((applicationLaunchers) => {
      onNewLaunchers({ applicationId, applicationLaunchers });
    });
  }, [isFilterValid, applicationId, toQRCodeString(filterByQRCode), requestTimestamp]);

  //
  // Connect to WebSocket topic
  const userToken = useSelector((state) => getTokenFromState(state));
  useLaunchersWebsocket({
    enabled: isFilterValid,
    userToken,
    applicationId,
    filterByQRCode,
    onWebsocketMessage: ({ applicationId, applicationLaunchers }) =>
      onNewLaunchers({ applicationId, applicationLaunchers }),
  });

  const showQRCodeScannerEffective = showQRCodeScanner || !isFilterValid;

  return (
    <div className="container launchers-container">
      {showQRCodeScannerEffective && (
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => {
            dispatch(populateLaunchersStart({ applicationId, filterByQRCode: toQRCodeObject(scannedBarcode) }));
          }}
        />
      )}
      {!showQRCodeScannerEffective && requiresLaunchersQRCodeFilter && (
        <div className="mb-5">
          <ButtonWithIndicator
            caption={filterByQRCode ? toQRCodeDisplayable(filterByQRCode) : 'Scan barcode'}
            onClick={() => setShowQRCodeScanner(true)}
          />
        </div>
      )}
      {!showQRCodeScannerEffective &&
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
