import { useHistory, useRouteMatch } from 'react-router-dom';
import { ViewHeader } from '../ViewHeader';
import ScreenToaster from '../../components/ScreenToaster';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import PropTypes from 'prop-types';
import { getCaptionFromHeaders, useHomeLocation } from '../../reducers/headers';
import { isWfProcessLoaded } from '../../reducers/wfProcesses';
import { trl } from '../../utils/translations';
import { useApplicationInfo } from '../../reducers/applications';

export const ApplicationLayout = ({ applicationId, Component }) => {
  const history = useHistory();

  //
  // If the required process was not loaded,
  // then redirect to home
  const redirectToHome = isWFProcessRequiredButNotLoaded();
  useEffect(() => {
    if (redirectToHome) {
      history.push('/');
    }
  }, [redirectToHome]);

  const applicationInfo = useApplicationInfo({ applicationId });
  const homeLocation = useHomeLocation();

  const captionFromHeaders = useSelector((state) => getCaptionFromHeaders(state));
  const caption = captionFromHeaders ? captionFromHeaders : applicationInfo.caption;

  useEffect(() => {
    document.title = caption;
  }, [caption]);

  if (redirectToHome) {
    return null;
  }
  return (
    <div className="app-container">
      <div className="app-header">
        <div className="columns is-mobile">
          <div className="column is-2 app-icon">
            <span className="icon">
              <i className={applicationInfo.iconClassNames} />
            </span>
          </div>
          <div className="column is-10 app-caption">
            <span>{caption}</span>
          </div>
        </div>
      </div>
      <div className="app-content">
        <ViewHeader />
        <Component />
        <ScreenToaster />
      </div>
      <div className="app-footer">
        <div className="columns is-mobile">
          <div className="column is-half">
            <button className="button is-fullwidth" onClick={() => history.goBack()}>
              <span className="icon">
                <i className="fas fa-chevron-left" />
              </span>
              <span>{trl('general.Back')}</span>
            </button>
          </div>
          <div className="column is-half">
            <button className="button is-fullwidth" onClick={() => history.push(homeLocation.location)}>
              <span className="icon">
                <i className={homeLocation.iconClassName} />
              </span>
              <span>{trl('general.Home')}</span>
            </button>
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

const isWFProcessRequiredButNotLoaded = () => {
  const { params } = useRouteMatch();
  const wfProcessId = params.wfProcessId || params.workflowId;
  if (!wfProcessId) {
    return false;
  }

  const isWFProcessLoaded = useSelector((state) => isWfProcessLoaded(state, wfProcessId));
  return !isWFProcessLoaded;
};
