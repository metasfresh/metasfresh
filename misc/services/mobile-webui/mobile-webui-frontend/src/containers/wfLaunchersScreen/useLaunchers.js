import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { useApplicationLaunchers } from '../../reducers/launchers';
import { useFilterByQRCode } from './useFilterByQRCode';
import { toQRCodeString } from '../../utils/qrCode/hu';
import { clearLaunchers, populateLaunchersComplete, populateLaunchersPushed } from '../../actions/LauncherActions';
import { getLaunchers, useLaunchersWebsocket } from '../../api/launchers';
import { getTokenFromState } from '../../reducers/appHandler';
import { useApplicationInfo } from '../../reducers/applications';

export const useLaunchers = ({ applicationId, showFilterByQRCode, facets, filters, isEnabled }) => {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);

  const { maxStartedLaunchers, allowStartNextJobOnly } = useApplicationInfo({ applicationId });
  const { requestTimestamp, launchers, actions } = useApplicationLaunchers({ applicationId });

  const { filterByQRCode: currentFilterByQRCode } = useFilterByQRCode({ applicationId });

  const filterByQRCode = showFilterByQRCode ? currentFilterByQRCode : null;
  const filterByQRCodeString = toQRCodeString(filterByQRCode);

  //
  // Load application launchers
  const onNewLaunchers = ({ applicationId, applicationLaunchers, requestTimestamp }) => {
    dispatch(populateLaunchersComplete({ applicationId, applicationLaunchers, requestTimestamp }));
  };
  useEffect(() => {
    if (isEnabled) {
      setLoading(true);
      // Capture WHEN the request is issued (not when it resolves): a launchers response is only
      // authoritative about processes that existed when the request went out. Threading this into
      // populateLaunchersComplete lets the wfProcesses reducer keep a process started after this.
      const launchersFetchStartedAt = Date.now();
      getLaunchers({ applicationId, filterByQRCodeString, filters, facets })
        .then((applicationLaunchers) => {
          onNewLaunchers({ applicationId, applicationLaunchers, requestTimestamp: launchersFetchStartedAt });
        })
        .finally(() => setLoading(false));
    } else {
      console.log('Skip fetching querying launchers is prohibited');
      dispatch(clearLaunchers({ applicationId }));
    }
  }, [isEnabled, applicationId, filterByQRCodeString, ...Object.values(filters), facets, requestTimestamp]);

  //
  // Connect to WebSocket topic
  const userToken = useSelector((state) => getTokenFromState(state));
  useLaunchersWebsocket({
    enabled: isEnabled,
    userToken,
    applicationId,
    filterByQRCode,
    filters,
    facets,
    onWebsocketMessage: ({ applicationId, applicationLaunchers }) => {
      // Refreshes the visible list only. Must not go through populateLaunchersComplete, whose action type
      // carries the power to prune workflow processes.
      dispatch(populateLaunchersPushed({ applicationId, applicationLaunchers }));
    },
  });

  return {
    isLaunchersLoading: loading,
    launchers: computeFinalLaunchers({ launchers, allowStartNextJobOnly, maxStartedLaunchers }),
    filterByQRCode,
    actions,
  };
};

const computeFinalLaunchers = ({ launchers, allowStartNextJobOnly = false, maxStartedLaunchers = 0 }) => {
  if (!launchers?.length) return launchers;

  const countStarted = launchers.filter((launcher) => isLauncherAlreadyStarted(launcher)).length;
  let isMaxStartedLaunchersAllowedExceeded =
    maxStartedLaunchers && maxStartedLaunchers > 0 && countStarted >= maxStartedLaunchers;
  if (isMaxStartedLaunchersAllowedExceeded && !allowStartNextJobOnly) return launchers;

  let newJobLauncherAlreadyAllowed = false;

  return launchers.map((launcher) => {
    let disabled;
    if (isLauncherAlreadyStarted(launcher)) {
      disabled = false;
      // console.log('Launcher already started', { disabled, launcher });
    } else if (isMaxStartedLaunchersAllowedExceeded) {
      disabled = true;
      // console.log('Max allowed launchers exceeded', { disabled, launcher });
    } else if (allowStartNextJobOnly && newJobLauncherAlreadyAllowed) {
      disabled = true;
      // console.log('A new job is already allowed to start', { disabled, launcher });
    } else {
      disabled = false;
      newJobLauncherAlreadyAllowed = true;
      // console.log('ALLOW new job', { disabled, launcher });
    }

    return { ...launcher, disabled };
  });
};

const isLauncherAlreadyStarted = (launcher) => {
  return !!launcher?.startedWFProcessId;
};
