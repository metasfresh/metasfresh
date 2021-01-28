import React, { useState, useEffect, FunctionComponent, ReactElement } from 'react';
import { useHistory } from 'react-router-dom';
import { observer, inject } from 'mobx-react';

import { translate } from '../utils/translate';

import View from './View';
import BottomNav from './BottomNav';
import { RootInstance } from '../models/Store';

interface Props {
  splat?: string;
  token?: string;
  store?: RootInstance;
}

const PasswordRecovery: FunctionComponent<Props> = inject('store')(
  observer(
    ({ store, token, splat }): ReactElement => {
      const history = useHistory();
      const [state, setState] = useState({
        email: '',
        password: '',
        edited: false,
        error: '',
        resetSent: false,
      });

      useEffect(() => {
        // user somehow got here without a valid reset token so redirect him to login
        if (splat.includes('reset') && !token) {
          history.push('/login');
        }

        // only set the title on initial load
        if (!state.edited) {
          store.navigation.setTopViewName(translate('LoginView.passwordReset.notification.title'));
          store.navigation.setBottomViewName(translate('LoginView.fields.loginButton'));
        }
      }, [store, state]);

      const handleChange = (e) => {
        const { id, value } = e.target;
        setState((prevState) => ({
          ...prevState,
          [id]: value,
          edited: true,
          error: '',
        }));
      };

      const handleSubmit = (event) => {
        event.preventDefault();

        const { email, resetSent } = state;

        if (token) {
          // after resetting token user is authenticated so redirect him to the app
          if (resetSent) {
            store.app.getUserSession().then(() => {
              history.push('/');
            });
          } else {
            store.app
              .confirmPasswordReset(token)
              .then(({ data }) => {
                setState((prevState) => ({
                  ...prevState,
                  email: data.email,
                  password: data.newPassword,
                  resetSent: true,
                }));
              })
              .catch((err) => {
                setState((prevState) => ({
                  ...prevState,
                  error: err,
                }));
              });
          }
        } else {
          store.app
            .requestPasswordReset(email)
            .then(() => {
              setState((prevState) => ({
                ...prevState,
                resetSent: true,
              }));
            })
            .catch((err) => {
              setState((prevState) => ({
                ...prevState,
                error: err,
              }));
            });
        }
      };

      const renderForgottenPasswordForm = () => {
        if (state.resetSent) {
          return (
            <div className="field">
              <div className="block">
                <p className="is-text">{translate('LoginView.passwordReset.notification.message')}</p>
              </div>
            </div>
          );
        }

        return (
          <div className="field">
            <p className="control has-icons-left has-icons-right">
              <input
                className="input is-medium"
                id="email"
                type="email"
                value={state.email}
                placeholder={translate('LoginView.fields.email')}
                onChange={handleChange}
              />
              <span className="icon is-small is-left">
                <i className="fas fa-envelope"></i>
              </span>
            </p>
            <div className="block pt-4">
              {!state.email && <p className="is-text">{translate('LoginView.passwordReset.error.fillEmail')}</p>}
            </div>
          </div>
        );
      };

      const renderResetPasswordForm = () => {
        if (state.resetSent) {
          return (
            <div className="block">
              <p className="is-text">Your new password is {state.password}</p>
            </div>
          );
        }
        return (
          <div className="block">
            <p className="is-text">Token: {token}</p>
          </div>
        );
      };

      let buttonDisabled = false;
      if (!token) {
        buttonDisabled = !!(!state.email || state.error);
      }

      const forcedState = {
        path: '/login',
        text: store.navigation.bottomViewName,
      };

      return (
        <>
          <View>
            <div className="container p-4 py-6 login-view">
              <h5 className="title is-3 pb-2">{store.navigation.topViewName}</h5>
              <form className="reset-password-form">
                {token ? renderResetPasswordForm() : renderForgottenPasswordForm()}
                <div className="field">
                  <p className="control">
                    {state.resetSent && !token ? null : (
                      <button
                        type="submit"
                        className="button is-medium is-green-bg is-success"
                        onClick={handleSubmit}
                        disabled={buttonDisabled}
                      >
                        {state.resetSent && token
                          ? translate('LoginView.fields.loginButton')
                          : translate('LoginView.passwordReset.notification.title')}
                      </button>
                    )}
                  </p>
                  {state.error ? <p className="help is-danger">{state.error}</p> : null}
                </div>
              </form>
            </div>
          </View>
          {!(token && state.resetSent) ? <BottomNav forcedState={forcedState} /> : null}
        </>
      );
    }
  )
);

export default PasswordRecovery;
