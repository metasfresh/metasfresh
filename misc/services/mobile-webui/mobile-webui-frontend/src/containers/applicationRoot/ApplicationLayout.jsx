import { useHistory, useRouteMatch } from 'react-router-dom';
import ViewHeader from '../ViewHeader';
import ScreenToaster from '../../components/ScreenToaster';
import React from 'react';
import { useSelector } from 'react-redux';
import { getApplicationInfoById } from '../../reducers/applications';
import PropTypes from 'prop-types';

export const ApplicationLayout = ({ applicationId, Component }) => {
  const applicationInfo = getApplicationInfo(applicationId);
  const history = useHistory();

  return (
    <div className="app-container">
      <div className="app-header">
        <div className="columns is-mobile is-size-3">
          <div className="column is-2 app-icon">
            <span className="icon">
              <i className={applicationInfo.iconClassNames} />
            </span>
          </div>
          <div className="column">
            <span>{applicationInfo.caption}</span>
          </div>
        </div>
      </div>
      <div className="app-content">
        <ViewHeader />
        <Component />
        <ScreenToaster />
      </div>
      <div className="app-footer">
        <div className="app-footer">
          <div className="columns is-mobile">
            <div className="column is-half">
              <button className="button is-fullwidth" onClick={() => history.goBack()}>
                <span className="icon">
                  <i className="fas fa-chevron-left" />
                </span>
                <span>Back</span>
              </button>
            </div>
            <div className="column is-half">
              <button className="button is-fullwidth" onClick={() => history.push('/')}>
                <span className="icon">
                  <i className="fas fa-home" />
                </span>
                <span>Home</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

ApplicationLayout.propTypes = {
  applicationId: PropTypes.string,
  Component: PropTypes.any.isRequired,
};

const getApplicationInfo = (knownApplicationId) => {
  let applicationId;
  if (knownApplicationId) {
    applicationId = knownApplicationId;
  } else {
    const routerMatch = useRouteMatch();
    applicationId = routerMatch.params.applicationId;
  }

  return useSelector((state) => getApplicationInfoById({ state, applicationId }));
};
