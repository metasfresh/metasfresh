import React, { FunctionComponent } from 'react';
import { useHistory } from "react-router-dom";

import View from './View';

interface Props {
  splat?: string
}

const Login: FunctionComponent<Props> = ({
  splat
}) => {
  let history = useHistory();

  const handleSubmit = (event) => {
    event.preventDefault();

    history.push("/");
  }

  return (
    <View>
      <div className="container">
        <h1 className="title">
          Login
        </h1>
        <form>
          <div className="field">
            <p className="control has-icons-left has-icons-right">
              <input className="input" type="email" placeholder="Email" />
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
              <input className="input" type="password" placeholder="Password" />
              <span className="icon is-small is-left">
                <i className="fas fa-lock"></i>
              </span>
            </p>
          </div>
          <div className="field">
            <p className="control">
              <button type="submit" className="button is-success" onClick={handleSubmit}>
                Login
              </button>
            </p>
          </div>
        </form>
      </div>
    </View>
  );
}

export default Login;