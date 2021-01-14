import React, { useState, FunctionComponent } from 'react';
import { useHistory } from 'react-router-dom';
import { translate } from '../utils/translate';
import View from './View';
import { loginRequest } from '../api/index';
interface Props {
  splat?: string;
}

const Login: FunctionComponent<Props> = ({}) => {
  const history = useHistory();
  const [state, setState] = useState({
    email: '',
    password: '',
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
    loginRequest(email, password);
    history.push('/');
  };

  return (
    <View>
      <div className="container p-4">
        <h1 className="title">{translate('LoginView.fields.loginButton')}</h1>
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
                placeholder={translate('LoginView.fields.email')}
                onChange={handleChange}
              />
              <span className="icon is-small is-left">
                <i className="fas fa-lock"></i>
              </span>
            </p>
          </div>
          <div className="field">
            <p className="control">
              <button type="submit" className="button is-success" onClick={handleSubmit}>
                {translate('LoginView.fields.loginButton')}
              </button>
            </p>
          </div>
        </form>
      </div>
    </View>
  );
};

export default Login;
