import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { useApplicationLaunchers } from '../../reducers/launchers';
import { useFilterByQRCode } from './useFilterByQRCode';
import { toQRCodeString } from '../../utils/qrCode/hu';
import { clearLaunchers, populateLaunchersComplete } from '../../actions/LauncherActions';
import { getLaunchers, useLaunchersWebsocket } from '../../api/launchers';
import { getTokenFromState } from '../../reducers/appHandler';
import { useApplicationInfo } from '../../reducers/applications';

export const useLaunchers = ({ applicationId, showFilterByQRCode, facets, filters, isEnabled }) => {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);

  const { maxStartedLaunchers, allowStartNextJobOnly } = useApplicationInfo({ applicationId });
  const { requestTimestamp, launchers } = useApplicationLaunchers({ applicationId });

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
      getLaunchers({ applicationId, filterByQRCodeString, filters, facets })
        .then((applicationLaunchers) => {
          onNewLaunchers({ applicationId, applicationLaunchers });
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
      onNewLaunchers({ applicationId, applicationLaunchers });
    },
  });

  return {
    isLaunchersLoading: loading,
    launchers: computeFinalLaunchers({ launchers, allowStartNextJobOnly, maxStartedLaunchers }),
    filterByQRCode,
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
