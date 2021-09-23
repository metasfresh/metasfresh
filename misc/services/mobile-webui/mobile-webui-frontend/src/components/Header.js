import React from 'react';
import { useStore } from 'react-redux';
import { useHistory } from 'react-router-dom';

import { useAuth } from '../hooks/useAuth';

const Header = ({ appName }) => {
  const auth = useAuth();
  const history = useHistory();
  const store = useStore();
  const state = store.getState();
  const { token, network } = state.appHandler;

  return (
    <header className="p-4 navbar is-fixed-top is-flex is-align-items-center is-justify-content-space-around header">
      <div className="header-title">
        <h4 className="title is-4">{appName}</h4>
        <div className="subtitle">network: {network ? 'online' : 'offline'} </div>
      </div>
      { token ? (
        <button
          onClick={() => {
            auth.logout().then(() => history.push("/"));
          }}
        >
        Log out
        </button>
      ) : null}
    </header>
  );
}

export default Header;
