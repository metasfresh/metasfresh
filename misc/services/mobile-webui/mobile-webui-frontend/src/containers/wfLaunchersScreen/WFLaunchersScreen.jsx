import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { getLaunchers, useLaunchersWebsocket } from '../../api/launchers';
import { clearLaunchers, populateLaunchersComplete, populateLaunchersStart } from '../../actions/LauncherActions';
import { getApplicationLaunchers, getApplicationLaunchersFilters } from '../../reducers/launchers';

import WFLauncherButton from './WFLauncherButton';
import { getTokenFromState } from '../../reducers/appHandler';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable, toQRCodeString } from '../../utils/qrCode/hu';
import WFLaunchersFilterButton from './WFLaunchersFilterButton';
import { pushHeaderEntry } from '../../actions/HeaderActions';
import { trl } from '../../utils/translations';
import { appLaunchersFilterLocation } from '../../routes/launchers';
import { useCurrentWorkplace } from '../../api/workplace';
import { useApplicationInfo } from '../../reducers/applications';
import { useCurrentWorkstation } from '../../api/workstation';

const WFLaunchersScreen = () => {
  const history = useHistory();
  const dispatch = useDispatch();

  const {
    url,
    params: { applicationId },
  } = useRouteMatch();

  const [currentPanel, setCurrentPanel] = useState('default');
  const { requiresLaunchersQRCodeFilter, showFilters } = useApplicationInfo({ applicationId });
  const { isWorkstationLoading, isWorkstationRequired, workstation, setWorkstationByQRCode } = useCurrentWorkstation({
    applicationId,
  });
  const { isWorkplaceLoading, isWorkplaceRequired, workplace, setWorkplaceByQRCode } = useCurrentWorkplace({
    applicationId,
  });
  const { filterByDocumentNo, facets } = useFilters({ applicationId });
  const { isLaunchersLoading, launchers, filterByQRCode, setFilterByQRCode } = useLaunchers({
    applicationId,
    requiresLaunchersQRCodeFilter,
    filterByDocumentNo,
    facets,
    isEnabled: !isWorkplaceLoading,
  });

  const workplaceName = workplace?.name;
  useEffect(() => {
    if (workplaceName) {
      dispatch(
        pushHeaderEntry({
          location: url,
          values: [
            {
              caption: trl('general.workplace'),
              value: workplaceName,
            },
          ],
        })
      );
    }
  }, [url, workplaceName]);

  const workstationName = workstation?.name;
  useEffect(() => {
    if (workstationName) {
      dispatch(
        pushHeaderEntry({
          location: url,
          values: [
            {
              caption: trl('general.workstation'),
              value: workstationName,
            },
          ],
        })
      );
    }
  }, [url, workstationName]);

  //
  // Get Workstation
  if (isWorkstationLoading) {
    return (
      <div className="container launchers-container">
        <Spinner />
      </div>
    );
  } else if (isWorkstationRequired && !workstation) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => setWorkstationByQRCode(scannedBarcode)}
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkstationPlaceholder')}
          continuousRunning={true}
        />
      </div>
    );
  }

  //
  // Get Workspace
  if (isWorkplaceLoading) {
    return (
      <div className="container launchers-container">
        <Spinner />
      </div>
    );
  } else if (isWorkplaceRequired && !workplace) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => setWorkplaceByQRCode(scannedBarcode)}
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkplacePlaceholder')}
          continuousRunning={true}
        />
      </div>
    );
  }

  //
  // Launchers
  return (
    <div className="container launchers-container">
      {currentPanel === 'scanQRCode' && (
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => {
            setFilterByQRCode(scannedBarcode);
            setCurrentPanel('default');
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
        <WFLaunchersFilterButton
          filterByDocumentNo={filterByDocumentNo}
          facets={facets}
          onClick={() => {
            history.push(appLaunchersFilterLocation({ applicationId }));
          }}
        />
      )}
      <br />
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
      {isLaunchersLoading && <Spinner />}
    </div>
  );
};

//
//
// ------------------------------------------------------------------------------------------------------------------------------
//
//

const Spinner = () => {
  return (
    <div className="loading">
      <i className="loading-icon fas fa-solid fa-spinner fa-spin" />
    </div>
  );
};

//
//
// ------------------------------------------------------------------------------------------------------------------------------
//
//

const useLaunchers = ({ applicationId, requiresLaunchersQRCodeFilter, facets, filterByDocumentNo, isEnabled }) => {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);

  const {
    filterByQRCode: currentFilterByQRCode,
    requestTimestamp,
    list: launchers,
  } = useSelector((state) => getApplicationLaunchers(state, applicationId));

  const filterByQRCode = requiresLaunchersQRCodeFilter ? currentFilterByQRCode : null;
  const isAllowQueryingLaunchers = isEnabled && (!requiresLaunchersQRCodeFilter || !!filterByQRCode);

  //
  // Load application launchers
  const onNewLaunchers = ({ applicationId, applicationLaunchers }) => {
    dispatch(populateLaunchersComplete({ applicationId, applicationLaunchers }));
  };
  useEffect(() => {
    if (isAllowQueryingLaunchers) {
      setLoading(true);
      getLaunchers({ applicationId, filterByQRCode, filterByDocumentNo, facets })
        .then((applicationLaunchers) => {
          onNewLaunchers({ applicationId, applicationLaunchers });
        })
        .finally(() => setLoading(false));
    } else {
      console.log('Skip fetching querying launchers is prohibited');
      dispatch(clearLaunchers({ applicationId }));
    }
  }, [
    isAllowQueryingLaunchers,
    applicationId,
    toQRCodeString(filterByQRCode),
    filterByDocumentNo,
    facets,
    requestTimestamp,
  ]);

  //
  // Connect to WebSocket topic
  const userToken = useSelector((state) => getTokenFromState(state));
  useLaunchersWebsocket({
    enabled: isAllowQueryingLaunchers,
    userToken,
    applicationId,
    filterByQRCode,
    filterByDocumentNo,
    facets,
    onWebsocketMessage: ({ applicationId, applicationLaunchers }) =>
      onNewLaunchers({ applicationId, applicationLaunchers }),
  });

  const setFilterByQRCode = (qrCode) => {
    dispatch(populateLaunchersStart({ applicationId, filterByQRCode: { code: qrCode, displayable: qrCode } }));
  };

  return {
    isLaunchersLoading: loading,
    launchers,
    filterByQRCode,
    setFilterByQRCode,
  };
};

const useFilters = ({ applicationId }) => {
  return useSelector((state) => getApplicationLaunchersFilters(state, applicationId));
};

export default WFLaunchersScreen;
