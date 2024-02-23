import React, { useEffect, useState } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { getLaunchers, useLaunchersWebsocket } from '../../api/launchers';
import { populateLaunchersComplete, populateLaunchersStart } from '../../actions/LauncherActions';
import { getApplicationLaunchers, getApplicationLaunchersFacets } from '../../reducers/launchers';

import WFLauncherButton from './WFLauncherButton';
import { getTokenFromState } from '../../reducers/appHandler';
import { getApplicationInfoById } from '../../reducers/applications';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable, toQRCodeString } from '../../utils/qrCode/hu';
import WFLaunchersFilterButton from './WFLaunchersFilterButton';
import { pushHeaderEntry } from '../../actions/HeaderActions';
import { trl } from '../../utils/translations';
import { appLaunchersFilterLocation } from '../../routes/launchers';
import { useCurrentWorkplace } from '../../api/workplace';

const WFLaunchersScreen = () => {
  const history = useHistory();
  const dispatch = useDispatch();

  const {
    url,
    params: { applicationId },
  } = useRouteMatch();

  const [currentPanel, setCurrentPanel] = useState('default');
  const { requiresLaunchersQRCodeFilter, showFilters } = useApplicationInfo({ applicationId });
  const { isWorkplaceLoading, isWorkplaceRequired, workplace, setWorkplaceByQRCode } = useCurrentWorkplace();
  const { facets } = useFacets({ applicationId });
  const { isLaunchersLoading, launchers, filterByQRCode, setFilterByQRCode } = useLaunchers({
    applicationId,
    requiresLaunchersQRCodeFilter,
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
              caption: trl('activities.picking.Workplace'),
              value: workplaceName,
            },
          ],
        })
      );
    }
  }, [url, workplaceName]);

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
  } else {
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
        {isLaunchersLoading && <Spinner />}
      </div>
    );
  }
};

const Spinner = () => {
  return (
    <div className="loading">
      <i className="loading-icon fas fa-solid fa-spinner fa-spin" />
    </div>
  );
};

const useApplicationInfo = ({ applicationId }) => {
  return useSelector((state) => getApplicationInfoById({ state, applicationId }), shallowEqual);
};
const useLaunchers = ({ applicationId, requiresLaunchersQRCodeFilter, facets, isEnabled }) => {
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

const useFacets = ({ applicationId }) => {
  const facets = useSelector((state) => getApplicationLaunchersFacets(state, applicationId));
  return { facets };
};
export default WFLaunchersScreen;
