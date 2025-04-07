import React, { useCallback, useEffect, useState } from 'react';

import ScreenToaster from '../components/ScreenToaster';
import LogoHeader from '../components/LogoHeader';
import { useMobileConfiguration } from '../api/configuration';
import ButtonWithIndicator from '../components/buttons/ButtonWithIndicator';
import UserAndPassAuth from './authMethods/UserAndPassAuth';
import QrCodeAuth from './authMethods/QrCodeAuth';
import { trl } from '../utils/translations';
import { useAuth } from '../hooks/useAuth';
import { useUITraceLocationChange } from '../utils/ui_trace/useUITraceLocationChange';
import { useMobileNavigation } from '../hooks/useMobileNavigation';
import PropTypes from 'prop-types';
import Spinner from '../components/Spinner';

const KNOWN_AUTH_METHODS = {
  QR_Code: 'QR_Code',
  UserPass: 'UserPass',
};

const VIEW = {
  LOGIN: 'login',
  ALTERNATIVE_METHODS: 'alternativeMethods',
};

const LoginScreen = () => {
  const history = useMobileNavigation();
  const [currentView, setCurrentView] = useState(VIEW.LOGIN);
  const { isConfigLoading, currentAuthMethod, setCurrentAuthMethod, availableAuthMethods } = useAuthenticationMethods();
  const auth = useAuth();

  useEffect(() => {
    if (auth.isLoggedIn()) {
      console.log(`LoginScreen: ALREADY LOGGED IN. Forwarding back to from location`);
      history.goToFromLocation();
    }
  }, []);

  useEffect(() => {
    document.title = 'mobile UI';
  }, []);

  useUITraceLocationChange();

  const handleSetAuthMethod = useCallback(
    (method) => {
      setCurrentAuthMethod(method);
      setCurrentView(VIEW.LOGIN);
    },
    [setCurrentAuthMethod, setCurrentView]
  );

  return (
    <div id="LoginScreen" className="login-view">
      <LogoHeader />
      {isConfigLoading && <Spinner />}
      {currentView === VIEW.LOGIN && !isConfigLoading && (
        <LoginView
          currentAuthMethod={currentAuthMethod}
          availableAuthMethods={availableAuthMethods}
          onSetAuthMethodClicked={handleSetAuthMethod}
          onAlternativeAuthMethodClicked={() => setCurrentView(VIEW.ALTERNATIVE_METHODS)}
        />
      )}
      {currentView === VIEW.ALTERNATIVE_METHODS && !isConfigLoading && (
        <SelectAuthMethodView
          availableAuthMethods={availableAuthMethods}
          onSetAuthMethodClicked={handleSetAuthMethod}
        />
      )}
      <ScreenToaster />
    </div>
  );
};

//
//
//
//
//

const LoginView = ({
  currentAuthMethod,
  availableAuthMethods,
  onSetAuthMethodClicked,
  onAlternativeAuthMethodClicked,
}) => {
  return (
    <>
      <LoginMethodPanel authMethod={currentAuthMethod} />
      {availableAuthMethods && availableAuthMethods.length === 2 && (
        <div className="section is-size-5" style={{ paddingTop: 0 }}>
          <div className="container px-6">
            <ButtonWithIndicator
              caption={trl('login.alternativeMethods')}
              onClick={() =>
                onSetAuthMethodClicked(availableAuthMethods.find((method) => method !== currentAuthMethod))
              }
              additionalCssClass={'alternative-button'}
            />
          </div>
        </div>
      )}
      {availableAuthMethods && availableAuthMethods.length > 2 && (
        <div className="section is-size-5" style={{ paddingTop: 0 }}>
          <div className="container px-6">
            <ButtonWithIndicator
              caption={trl('login.alternativeMethods')}
              onClick={onAlternativeAuthMethodClicked}
              additionalCssClass={'alternative-button'}
            />
          </div>
        </div>
      )}
    </>
  );
};
LoginView.propTypes = {
  currentAuthMethod: PropTypes.string.isRequired,
  availableAuthMethods: PropTypes.array.isRequired,
  onSetAuthMethodClicked: PropTypes.func.isRequired,
  onAlternativeAuthMethodClicked: PropTypes.func.isRequired,
};

//
//
//
//
//

const LoginMethodPanel = ({ authMethod }) => {
  if (authMethod === KNOWN_AUTH_METHODS.UserPass) {
    return <UserAndPassAuth />;
  } else if (authMethod === KNOWN_AUTH_METHODS.QR_Code) {
    return <QrCodeAuth />;
  } else {
    console.warn(`Unknown auth method "${authMethod}". Falling back to "${KNOWN_AUTH_METHODS.UserPass}"`);
    return <UserAndPassAuth />;
  }
};
LoginMethodPanel.propTypes = {
  authMethod: PropTypes.string.isRequired,
};

//
//
//
//
//

const SelectAuthMethodView = ({ availableAuthMethods, onSetAuthMethodClicked }) => {
  return (
    <div className="pt-3 section">
      {availableAuthMethods.map((method) => (
        <ButtonWithIndicator
          key={method}
          caption={trl(`login.authMethod.${method}`)}
          onClick={() => onSetAuthMethodClicked(method)}
        />
      ))}
    </div>
  );
};
SelectAuthMethodView.propTypes = {
  availableAuthMethods: PropTypes.array.isRequired,
  onSetAuthMethodClicked: PropTypes.func.isRequired,
};

//
//
//
//
//

const useAuthenticationMethods = () => {
  const [currentAuthMethod, setCurrentAuthMethod] = useState(KNOWN_AUTH_METHODS.UserPass);
  const [availableAuthMethods, setAvailableAuthMethods] = useState([]);
  //console.log('useAuthenticationMethods', { currentAuthMethod, availableAuthMethods });

  const { isConfigLoading } = useMobileConfiguration({
    onSuccess: (config) => {
      const availableAuthMethodsNew = config.availableAuthMethods
        .map((method) => KNOWN_AUTH_METHODS[method])
        .filter((method) => !!method);
      const currentAuthMethodNew = KNOWN_AUTH_METHODS[config.defaultAuthMethod] || KNOWN_AUTH_METHODS.UserPass;
      //console.log('useAuthenticationMethods: got config', { config, availableAuthMethodsNew, currentAuthMethodNew });
      setAvailableAuthMethods(availableAuthMethodsNew);
      setCurrentAuthMethod(currentAuthMethodNew);
    },
  });

  return { isConfigLoading, currentAuthMethod, setCurrentAuthMethod, availableAuthMethods };
};

//
//
//
//
//

export default LoginScreen;
