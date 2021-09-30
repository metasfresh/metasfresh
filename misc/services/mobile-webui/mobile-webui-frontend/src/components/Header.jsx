import React from 'react';
import { useStore } from 'react-redux';
import { useLocation, useHistory } from 'react-router-dom';
import PropTypes from 'prop-types';

const Header = ({ appName }) => {
  const store = useStore();
  const state = store.getState();
  const location = useLocation();
  const history = useHistory();
  const { network } = state.appHandler;
  const showBackButton = state.appHandler.token && location.pathname !== '/' && location.pathname !== '/login';

  const handleClick = () => {
    if (showBackButton) {
      history.goBack();
    }
  };

  return (
    <header className="p-4 navbar is-fixed-top is-flex is-align-items-center is-justify-content-space-around header">
      {showBackButton ? (
        <button className="button" onClick={handleClick}>
          Back
        </button>
      ) : null}
      <div className="header-title">
        <h4 className="title is-4">{appName}</h4>
        <div className="subtitle">network: {network ? 'online' : 'offline'} </div>
      </div>
    </header>
  );
};

Header.propTypes = {
  appName: PropTypes.string,
};

export default Header;
