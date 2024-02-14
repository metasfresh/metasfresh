import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { getLaunchers, useLaunchersWebsocket } from '../../api/launchers';
import { populateLaunchersComplete, populateLaunchersStart } from '../../actions/LauncherActions';
import { getApplicationLaunchers, getApplicationLaunchersFacets } from '../../reducers/launchers';

import WFLauncherButton from './WFLauncherButton';
import { getTokenFromState } from '../../reducers/appHandler';
import { getApplicationInfoById } from '../../reducers/applications';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable, toQRCodeObject, toQRCodeString } from '../../utils/qrCode/hu';
import WFLaunchersFilterButton from './WFLaunchersFilterButton';
import { pushHeaderEntry } from '../../actions/HeaderActions';
import { trl } from '../../utils/translations';
import { appLaunchersFilterLocation } from '../../routes/launchers';
import { useCurrentWorkplace } from '../../api/workplace';

const WFLaunchersScreen = () => {
  const history = useHistory();

  const {
    url,
    params: { applicationId },
  } = useRouteMatch();

  const [currentPanel, setCurrentPanel] = useState('default');
  const [loading, setLoading] = useState(false);

  const { requiresLaunchersQRCodeFilter, showFilters } = useSelector((state) =>
    getApplicationInfoById({ state, applicationId })
  );

  const { isWorkplaceLoading, isWorkplaceRequired, workplace, setWorkplaceByQRCode } = useCurrentWorkplace();
  const workplaceName = workplace?.name;
  const showWorkplaceScanner = isWorkplaceRequired && !workplace;

  useEffect(() => {
    if (workplaceName) {
      dispatch(
        pushHeaderEntry({
          location: url,
          values: [
            {
              caption: trl('activities.picking.Workplace'),
              value: workplaceName,
            },
          ],
        })
      );
    }
  }, [url, workplaceName]);

  const {
    filterByQRCode: currentFilterByQRCode,
    requestTimestamp,
    list: launchers,
  } = useSelector((state) => getApplicationLaunchers(state, applicationId));

  const facets = useSelector((state) => getApplicationLaunchersFacets(state, applicationId));

  const filterByQRCode = requiresLaunchersQRCodeFilter ? currentFilterByQRCode : null;
  const isAllowQueryingLaunchers = (!requiresLaunchersQRCodeFilter || !!filterByQRCode) && !showWorkplaceScanner;

  //
  // Load application launchers
  const onNewLaunchers = ({ applicationId, applicationLaunchers }) => {
    dispatch(populateLaunchersComplete({ applicationId, applicationLaunchers }));
  };
  const dispatch = useDispatch();
  useEffect(() => {
    if (!isAllowQueryingLaunchers) {
      console.log('Skip fetching querying launchers is prohibited');
      return;
    }

    setLoading(true);
    getLaunchers({ applicationId, filterByQRCode, facets })
      .then((applicationLaunchers) => {
        onNewLaunchers({ applicationId, applicationLaunchers });
      })
      .finally(() => setLoading(false));
  }, [isAllowQueryingLaunchers, applicationId, toQRCodeString(filterByQRCode), facets, requestTimestamp]);

  //
  // Connect to WebSocket topic
  const userToken = useSelector((state) => getTokenFromState(state));
  useLaunchersWebsocket({
    enabled: isAllowQueryingLaunchers,
    userToken,
    applicationId,
    filterByQRCode,
    facets,
    onWebsocketMessage: ({ applicationId, applicationLaunchers }) =>
      onNewLaunchers({ applicationId, applicationLaunchers }),
  });

  const onWorkplaceQRCodeScanned = (qrCode) => {
    setWorkplaceByQRCode(qrCode);
  };

  if (isWorkplaceLoading) {
    return (
      <div className="container launchers-container">
        <Spinner />
      </div>
    );
  } else if (showWorkplaceScanner) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => onWorkplaceQRCodeScanned(scannedBarcode)}
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkplacePlaceholder')}
          continuousRunning={true}
        />
      </div>
    );
  }

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
      {currentPanel === 'default' && showFilters && !requiresLaunchersQRCodeFilter && (
        <>
          <WFLaunchersFilterButton
            facets={facets}
            onClick={() => {
              history.push(appLaunchersFilterLocation({ applicationId }));
            }}
          />
          <br />
        </>
      )}
      {currentPanel === 'default' &&
        launchers &&
        launchers.map((launcher, index) => {
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
      {loading && <Spinner />}
    </div>
  );
};

const Spinner = () => {
  return (
    <div className="loading">
      <i className="loading-icon fas fa-solid fa-spinner fa-spin" />
    </div>
  );
};

export default WFLaunchersScreen;
