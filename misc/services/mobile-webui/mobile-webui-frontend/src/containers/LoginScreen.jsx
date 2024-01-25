import React, { useEffect, useState } from 'react';
import { toastError } from '../utils/toast';

import ScreenToaster from '../components/ScreenToaster';
import LogoHeader from '../components/LogoHeader';
import { getMobileConfiguration } from '../api/configuration';
import ButtonWithIndicator from '../components/buttons/ButtonWithIndicator';
import UserAndPassAuth from './authMethods/UserAndPassAuth';
import QrCodeAuth from './authMethods/QrCodeAuth';
import { trl } from '../utils/translations';

const KNOWN_AUTH_METHODS = {
  QR_CODE: 'qrCode',
  USER_PASS: 'userAndPass',
};

const VIEW = {
  LOGIN: 'login',
  ALTERNATIVE_METHODS: 'alternativeMethods',
};

const LoginScreen = () => {
  const [currentAuthMethod, setCurrentAuthMethod] = useState(KNOWN_AUTH_METHODS.USER_PASS);
  const [availableAuthMethods, setAvailableAuthMethods] = useState([]);
  const [currentView, setCurrentView] = useState(VIEW.LOGIN);

  const getAuthMethodScreen = () => {
    if (currentAuthMethod === KNOWN_AUTH_METHODS.USER_PASS) {
      return <UserAndPassAuth />;
    } else if (currentAuthMethod === KNOWN_AUTH_METHODS.QR_CODE) {
      return <QrCodeAuth />;
    } else {
      toastError({ plainMessage: 'Unknown auth method!' });
    }
  };

  const getLoginScreen = () => {
    return (
      <>
        {getAuthMethodScreen()}
        {availableAuthMethods && availableAuthMethods.length > 1 && (
          <div className="section is-size-5" style={{ paddingTop: 0 }}>
            <div className="container px-6">
              <ButtonWithIndicator
                caption={trl('login.alternativeMethods')}
                onClick={() => setCurrentView(VIEW.ALTERNATIVE_METHODS)}
                additionalCssClass={'alternative-button'}
              />
            </div>
          </div>
        )}
      </>
    );
  };

  const toggleAuthMethod = (method) => {
    setCurrentAuthMethod(method);
    setCurrentView(VIEW.LOGIN);
  };

  const getAlternativeMethodsScreen = () => {
    return (
      <div className="pt-3 section">
        {availableAuthMethods.map((method, index) => (
          <ButtonWithIndicator
            key={index}
            caption={trl(`login.authMethod.${method}`)}
            onClick={() => toggleAuthMethod(method)}
          />
        ))}
      </div>
    );
  };

  useEffect(() => {
    document.title = 'mobile UI';
  }, []);

  useEffect(() => {
    getMobileConfiguration().then((config) => {
      setCurrentAuthMethod(KNOWN_AUTH_METHODS[config.defaultAuthMethod] || KNOWN_AUTH_METHODS.USER_PASS);

      const authMethods = config.availableAuthMethods
        .map((method) => KNOWN_AUTH_METHODS[method])
        .filter((method) => method !== null && method !== undefined);

      setAvailableAuthMethods(authMethods);
    });
  }, [setCurrentAuthMethod, setCurrentAuthMethod]);

  return (
    <div className="login-view">
      <LogoHeader />
      {currentView === VIEW.LOGIN && getLoginScreen()}
      {currentView === VIEW.ALTERNATIVE_METHODS && getAlternativeMethodsScreen()}
      <ScreenToaster />
    </div>
  );
};

export default LoginScreen;
