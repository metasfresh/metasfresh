import React, { useState, useEffect, FunctionComponent, ReactElement } from 'react';
import { useHistory } from 'react-router-dom';
import { observer, inject } from 'mobx-react';

import { translate } from '../utils/translate';
import logo from '../static/images/logo.png';
import poweredByLogo from '../static/images/poweredby.png';

import View from './View';
import { RootInstance } from '../models/Store';

interface Props {
  store?: RootInstance;
}

const Login: FunctionComponent<Props> = inject('store')(
  observer(
    ({ store }): ReactElement => {
      const history = useHistory();
      const [state, setState] = useState({
        email: '',
        password: '',
      });
      const { app } = store;

      useEffect(() => {
        if (!(state.email || state.password)) {
          store.navigation.setViewNames(translate('LoginView.fields.loginButton'));
        }
      });

      const handleChange = (e) => {
        const { id, value } = e.target;
        setState((prevState) => ({
          ...prevState,
          [id]: value,
        }));
      };

      const handleSubmit = (event) => {
        event.preventDefault();

        const { email, password } = state;

        store.logIn(email, password).then((response) => {
          !response.loginError && history.push('/');
        });
      };

      const handleForgotPassword = (event) => {
        event.preventDefault();

        history.push('/forgottenPassword');
      };

      return (
        <View>
          <div className="container p-4 pb-6 login-view">
            <div className="login-logo pb-6">
              <img src={logo} className="logo" />
            </div>
            <form>
              <div className="field">
                <p className="control has-icons-left">
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
              </div>
              <div className="field">
                <p className="control has-icons-left">
                  <input
                    className="input is-medium"
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

              <div className="field my-4">
                <p className="control">
                  <a onClick={handleForgotPassword} className="green-color">
                    {translate('LoginView.fields.forgotPasswordButton')}
                  </a>
                </p>
              </div>

              <div className="field">
                <div className="control">
                  <button
                    type="submit"
                    className="button is-medium is-fullwidth is-success is-green-bg"
                    onClick={handleSubmit}
                  >
                    {translate('LoginView.fields.loginButton')}
                  </button>
                </div>
                {app.loginError ? <p className="help is-danger">{app.loginError}</p> : null}
              </div>
            </form>
          </div>
          <div className="login-poweredby-logo">
            <img src={poweredByLogo} className="poweredby" />
          </div>
        </View>
      );
    }
  )
);

export default Login;
