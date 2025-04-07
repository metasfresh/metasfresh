import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { getLaunchers, useLaunchersWebsocket } from '../../api/launchers';
import { clearLaunchers, populateLaunchersComplete, populateLaunchersStart } from '../../actions/LauncherActions';
import { getApplicationLaunchers, getApplicationLaunchersFilters } from '../../reducers/launchers';

import WFLauncherButton from './WFLauncherButton';
import { getTokenFromState } from '../../reducers/appHandler';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable, toQRCodeObject, toQRCodeString } from '../../utils/qrCode/hu';
import WFLaunchersFilterButton from './WFLaunchersFilterButton';
import { updateHeaderEntry } from '../../actions/HeaderActions';
import { trl } from '../../utils/translations';
import { appLaunchersBarcodeScannerLocation, appLaunchersFilterLocation } from '../../routes/launchers';
import { useCurrentWorkplace } from '../../api/workplace';
import { useApplicationInfo } from '../../reducers/applications';
import { useCurrentWorkstation } from '../../api/workstation';
import { useScreenDefinition } from '../../hooks/useScreenDefinition';
import { useMobileLocation } from '../../hooks/useMobileLocation';

const WFLaunchersScreen = () => {
  const { history } = useScreenDefinition({ screenId: 'WFLaunchersScreen', back: '/' });
  const dispatch = useDispatch();
  const { url, applicationId } = useMobileLocation();

  const { showFilterByQRCode, showFilters } = useApplicationInfo({ applicationId });
  const { isWorkstationLoading, isWorkstationRequired, workstation, setWorkstationByQRCode } = useCurrentWorkstation({
    applicationId,
  });
  const { isWorkplaceLoading, isWorkplaceRequired, workplace, setWorkplaceByQRCode } = useCurrentWorkplace({
    applicationId,
  });
  const { filterByDocumentNo, facets } = useFilters({ applicationId });
  const { isLaunchersLoading, launchers, filterByQRCode } = useLaunchers({
    applicationId,
    showFilterByQRCode,
    filterByDocumentNo,
    facets,
    isEnabled: !isWorkplaceLoading,
  });

  const workplaceName = workplace?.name;
  const workstationName = workstation?.name;
  useEffect(() => {
    dispatch(
      updateHeaderEntry({
        location: url,
        values: [
          {
            caption: trl('general.workplace'),
            value: workplaceName,
            hidden: !workplaceName,
          },
          {
            caption: trl('general.workstation'),
            value: workstationName,
            hidden: !workstationName,
          },
        ],
      })
    );
  }, [url, workplaceName, workstationName]);

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
      {showFilterByQRCode && (
        <ButtonWithIndicator
          typeFASIconName="fa-solid fa-barcode"
          caption={filterByQRCode ? toQRCodeDisplayable(filterByQRCode) : 'Scan barcode'}
          onClick={() => history.push(appLaunchersBarcodeScannerLocation({ applicationId }))}
          testId="filterByQRCode-button"
        />
      )}
      {showFilters && (
        <WFLaunchersFilterButton
          filterByDocumentNo={filterByDocumentNo}
          facets={facets}
          onClick={() => {
            history.push(appLaunchersFilterLocation({ applicationId }));
          }}
        />
      )}
      <br />
      {launchers &&
        launchers.map((launcher, index) => {
          const id = `launcher-${index}-button`;
          return (
            <WFLauncherButton
              key={id}
              applicationId={launcher.applicationId}
              caption={launcher.caption}
              startedWFProcessId={launcher.startedWFProcessId}
              wfParameters={launcher.wfParameters}
              showWarningSign={launcher.showWarningSign}
              testId={launcher.testId}
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

const useLaunchers = ({ applicationId, showFilterByQRCode, facets, filterByDocumentNo, isEnabled }) => {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);

  const { requestTimestamp, list: launchers } = useSelector((state) => getApplicationLaunchers(state, applicationId));

  const { filterByQRCode: currentFilterByQRCode } = useFilterByQRCode({ applicationId });

  const filterByQRCode = showFilterByQRCode ? currentFilterByQRCode : null;
  const filterByQRCodeString = toQRCodeString(filterByQRCode);

  //
  // Load application launchers
  const onNewLaunchers = ({ applicationId, applicationLaunchers }) => {
    dispatch(populateLaunchersComplete({ applicationId, applicationLaunchers }));
  };
  useEffect(() => {
    if (isEnabled) {
      setLoading(true);
      getLaunchers({ applicationId, filterByQRCodeString, filterByDocumentNo, facets })
        .then((applicationLaunchers) => {
          onNewLaunchers({ applicationId, applicationLaunchers });
        })
        .finally(() => setLoading(false));
    } else {
      console.log('Skip fetching querying launchers is prohibited');
      dispatch(clearLaunchers({ applicationId }));
    }
  }, [isEnabled, applicationId, filterByQRCodeString, filterByDocumentNo, facets, requestTimestamp]);

  //
  // Connect to WebSocket topic
  const userToken = useSelector((state) => getTokenFromState(state));
  useLaunchersWebsocket({
    enabled: isEnabled,
    userToken,
    applicationId,
    filterByQRCode,
    filterByDocumentNo,
    facets,
    onWebsocketMessage: ({ applicationId, applicationLaunchers }) =>
      onNewLaunchers({ applicationId, applicationLaunchers }),
  });

  return {
    isLaunchersLoading: loading,
    launchers,
    filterByQRCode,
  };
};

const useFilters = ({ applicationId }) => {
  return useSelector((state) => getApplicationLaunchersFilters(state, applicationId));
};

export const useFilterByQRCode = ({ applicationId }) => {
  const dispatch = useDispatch();
  const { filterByQRCode } = useSelector((state) => getApplicationLaunchers(state, applicationId));

  const setFilterByQRCode = (qrCode) => {
    dispatch(populateLaunchersStart({ applicationId, filterByQRCode: toQRCodeObject(qrCode) }));
  };

  return {
    filterByQRCode,
    setFilterByQRCode,
  };
};

export default WFLaunchersScreen;
