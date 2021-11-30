import React from 'react';
import { useStore } from 'react-redux';
import { useLocation, useHistory } from 'react-router-dom';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import BackButton from './BackButton';

const Header = ({ appName, hidden }) => {
  const store = useStore();
  const state = store.getState();
  const location = useLocation();
  const history = useHistory();
  const { activeApplication } = state.applications;
  const showBackButton = state.appHandler.token && location.pathname !== '/' && location.pathname !== '/login';
  const applicationName = activeApplication ? activeApplication.caption : appName;

  const handleClick = () => {
    if (showBackButton) {
      history.goBack();
    }
  };

  return (
    <header className={classnames('p-4', { hidden: hidden, header: !hidden })}>
      <div className="columns is-mobile">
        <div className="column pt-1 is-2">{showBackButton ? <BackButton onClickExec={handleClick} /> : null}</div>
        <div className="column is-flex-grow-2 has-text-centered header-title">
          <h4 className="title is-half is-4 pt-2 pb-3">{applicationName}</h4>
          {/* <div className="subtitle">network: {network ? 'online' : 'offline'} </div> */}
        </div>
        <div className="column is-2" />
      </div>
    </header>
  );
};

Header.propTypes = {
  appName: PropTypes.string,
  hidden: PropTypes.bool,
};

export default Header;
