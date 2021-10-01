import React from 'react';
import { useStore } from 'react-redux';
import { useLocation, useHistory } from 'react-router-dom';
import PropTypes from 'prop-types';
import BackButton from './BackButton';

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

  //<header className="p-4 navbar is-fixed-top is-flex is-align-items-center is-justify-content-space-around header">

  return (
    <header className="p-4 is-fixed-top header">
      <div className="columns pl-5 is-mobile">
        <div className="column dupasradupapapapapapapa">
          {showBackButton ? <BackButton onClickExec={handleClick} /> : null}
        </div>
        <div className="column is-four-fifths has-text-centered header-title">
          <h4 className="title is-4">{appName}</h4>
          <div className="subtitle">network: {network ? 'online' : 'offline'} </div>
        </div>
        <div className="column" />
      </div>
    </header>
  );
};

Header.propTypes = {
  appName: PropTypes.string,
};

export default Header;

/*<div className="container">
  <div
    className="columns pl-5 is-mobile"
    onClick={() => {
      navigation.removeViewFromHistory();
      history.goBack();
    }}
  >
    <div className="column is-flex is-11 is-size-3 green-color mt-1">
      <div className="mt-2">
        <i className="fas fa-chevron-left" />
      </div>
      <div className="pt-1 pl-2 nav-text">
        <span className="is-size-4">{text}</span>
      </div>
    </div>
  </div>
</div>*/
