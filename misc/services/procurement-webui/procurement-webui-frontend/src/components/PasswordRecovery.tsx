import React, { useState, useEffect, FunctionComponent, ReactElement } from 'react';
import { useHistory } from 'react-router-dom';
import { observer, inject } from 'mobx-react';

import { translate } from '../utils/translate';

import View from './View';
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
      });
      const { app } = store;

      useEffect(() => {
        // only set the title on initial load
        if (!state.edited) {
          store.navigation.setViewNames(translate('LoginView.passwordReset.notification.title'));
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

        const { email, password } = state;

        if (token) {
          // store.app
          //     .requestPasswordReset(email)
          //     .then((response) => {
          //       console.log('RESET RESPONSE: ', response);
          //     })
          //     .catch((err) => {
          //       setState((prevState) => ({
          //         ...prevState,
          //         error: err,
          //       }));
          //     });
        } else {
          store.app
            .requestPasswordReset(email)
            .then((response) => {
              console.log('RESET RESPONSE: ', response);
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
        return (
          <div className="field">
            <p className="control has-icons-left has-icons-right">
              <input
                className="input"
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
          </div>
        );
      };

      const renderResetPasswordForm = () => {
        return (
          <div className="field">
            <p className="control has-icons-left">
              <input
                className="input"
                id="password"
                type="password"
                value={state.password}
                placeholder={translate('LoginView.fields.password')}
                onChange={handleChange}
              />
              <span className="icon is-small is-left">
                <i className="fas fa-lock"></i>
              </span>
            </p>
          </div>
        );
      };

      return (
        <View>
          <div className="container p-4">
            <h1 className="title">{store.navigation.topViewName}</h1>
            <form>
              {token ? renderResetPasswordForm() : renderForgottenPasswordForm()}
              <div className="field">
                <p className="control">
                  <button type="submit" className="button is-success" onClick={handleSubmit}>
                    {translate('LoginView.passwordReset.notification.title')}
                  </button>
                </p>
                {state.error ? <p className="help is-danger">{state.error}</p> : null}
              </div>
            </form>
          </div>
        </View>
      );
    }
  )
);

export default PasswordRecovery;
