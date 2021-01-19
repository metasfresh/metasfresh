import React, { useState, FunctionComponent, ReactElement } from 'react';
import { useHistory } from 'react-router-dom';
import { observer, inject } from 'mobx-react';

import { translate } from '../utils/translate';

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
          <div className="container p-4">
            <h1 className="title">{store.navigation.topViewName}</h1>
            <form>
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
                  <span className="icon is-small is-right">
                    <i className="fas fa-check"></i>
                  </span>
                </p>
              </div>
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

              <div className="field">
                <p className="control">
                  <a onClick={handleForgotPassword}>{translate('LoginView.fields.forgotPasswordButton')}</a>
                </p>
              </div>

              <div className="field">
                <div className="control">
                  <button type="submit" className="button is-success" onClick={handleSubmit}>
                    {translate('LoginView.fields.loginButton')}
                  </button>
                </div>
                {app.loginError ? <p className="help is-danger">{app.loginError}</p> : null}
              </div>
            </form>
          </div>
        </View>
      );
    }
  )
);

export default Login;
