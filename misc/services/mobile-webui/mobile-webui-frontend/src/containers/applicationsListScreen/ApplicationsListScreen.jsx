import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { getAvailableApplicationsArray } from '../../reducers/applications';

import ScreenToaster from '../../components/ScreenToaster';
import ApplicationButton from './ApplicationButton';
import LogoHeader from '../../components/LogoHeader';
import { getApplicationStartFunction } from '../../apps';
import { appLaunchersLocation } from '../../routes/launchers';
import { useAuth } from '../../hooks/useAuth';
import { trl } from '../../utils/translations';
import { useUITraceLocationChange } from '../../utils/ui_trace/useUITraceLocationChange';
import { useScreenDefinition } from '../../hooks/useScreenDefinition';

const SCREEN_ID = 'ApplicationsListScreen';

const ApplicationsListScreen = () => {
  const { history } = useScreenDefinition({ screenId: SCREEN_ID, isHomeStop: true, back: '/' });

  const applications = useSelector((state) => getAvailableApplicationsArray(state));
  const applicationsDisplayed = applications.filter((app) => !!app.showInMainMenu);

  //
  // If there is only one application displayed then start it automatically
  useEffect(() => {
    if (applicationsDisplayed.length === 1) {
      const singleApplicationId = applicationsDisplayed[0].id;
      console.log(`Automatically starting single application ${singleApplicationId}`);
      handleAppClick(singleApplicationId);
    }
  }, [applications]);

  useEffect(() => {
    document.title = 'mobile UI';
  }, []);

  useUITraceLocationChange();

  const dispatch = useDispatch();
  const handleAppClick = (applicationId) => {
    const startApplicationFunc = getApplicationStartFunction(applicationId);
    if (startApplicationFunc) {
      dispatch(startApplicationFunc());
    } else {
      history.push(appLaunchersLocation({ applicationId }));
    }
  };

  const auth = useAuth();
  const handleLogout = () => {
    auth.logout();
  };

  return (
    <div id={SCREEN_ID} className="applications-list">
      <LogoHeader />
      <div className="section">
        {applicationsDisplayed.map((app) => (
          <ApplicationButton
            key={app.id}
            id={app.id}
            caption={app.caption}
            iconClassNames={app.iconClassNames}
            onClick={() => handleAppClick(app.id)}
          />
        ))}
        <br />
        <ApplicationButton
          key="logout"
          id="logout"
          caption={trl('logout')}
          iconClassNames="fas fa-power-off"
          onClick={() => handleLogout()}
        />
      </div>
      <ScreenToaster />
    </div>
  );
};

export default ApplicationsListScreen;
