import React from 'react';
import PropTypes from 'prop-types';
import { useLocation, useHistory, useRouteMatch } from 'react-router-dom';
import { useStore } from 'react-redux';

import BackButton from './BackButton';
import { getApplicationCaptionById } from '../../reducers/applications';
import counterpart from 'counterpart';

const Header = (props) => {
  const store = useStore();
  const state = store.getState();
  const location = useLocation();
  const history = useHistory();
  const showBackButton = state.appHandler.token && location.pathname !== '/' && location.pathname !== '/login';

  let applicationId = props.applicationId;
  if (!applicationId) {
    const routerMatch = useRouteMatch();
    applicationId = routerMatch.params.applicationId;
  }

  const applicationName = getApplicationCaptionById({
    state,
    applicationId,
    fallbackCaption: counterpart.translate('appName'),
  });

  const onBackButtonClick = () => {
    if (showBackButton) {
      history.goBack();
    }
  };

  return (
    <header className="p-4 header">
      <div className="columns is-mobile">
        <div className="column pt-1 is-2">{showBackButton ? <BackButton onClick={onBackButtonClick} /> : null}</div>
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
  applicationId: PropTypes.string,
};

export default Header;
