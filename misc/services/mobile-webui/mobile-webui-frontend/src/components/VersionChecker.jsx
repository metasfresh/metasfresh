import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import PropTypes from 'prop-types';

import { getServerVersion } from '../api/update';
import { setVersion } from '../actions/UpdateActions';
import { getVersionFromState } from '../reducers/update';

const VersionChecker = ({ updateIntervalMillis }) => {
  const currentVersion = useSelector((state) => getVersionFromState(state));

  const dispatch = useDispatch();
  const checkServerVersion = () => {
    console.log('Retrieving version from server...');
    getServerVersion()
      .then((version) => {
        console.log(`Got version "${version}". Current known version is "${currentVersion}"`);
        if (currentVersion === null) {
          dispatch(setVersion(version));
        } else if (currentVersion !== version) {
          // Clear cache
          caches.keys().then((keys) => {
            for (const key of keys) {
              caches.delete(key);
            }
          });

          dispatch(setVersion(version));

          /**
           * (!) if full path contains `/login` on refresh it will lead to a failed to load resource fetch (404) that would make the service worker
           *     redundant. Due to this we need to redirect to the root of the site when a version change is happaning instead of reloading the page
           */
          // let fullUrl = window.location.href;
          // if (fullUrl.includes('/login')) {
          //   window.location.href = '/mobile/';
          // } else {
          //   window.location.reload();
          // }
          console.log('Version changed, reloading page...');
          window.location.reload();
        }
      })
      .catch((error) => {
        console.log('Got error', error);
        // networkStatusOffline();
      });
  };

  useEffect(() => {
    console.log(`Checking version each ${updateIntervalMillis}ms`);
    let intervalId = setInterval(checkServerVersion, updateIntervalMillis);

    return () => {
      clearInterval(intervalId);
    };
  }, [updateIntervalMillis, currentVersion]);

  return <></>;
};

VersionChecker.propTypes = {
  updateIntervalMillis: PropTypes.number.isRequired,
};

export default VersionChecker;
