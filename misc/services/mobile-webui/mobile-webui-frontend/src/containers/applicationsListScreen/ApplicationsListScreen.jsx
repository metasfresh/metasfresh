import React from 'react';
import { useSelector } from 'react-redux';

import { getAvailableApplicationsArray } from '../../reducers/applications';
import LogoHeader from '../../components/screenHeaders/LogoHeader';
import ApplicationButton from './ApplicationButton';
import ScreenToaster from '../../components/ScreenToaster';

const ApplicationsListScreen = () => {
  const applications = useSelector((state) => getAvailableApplicationsArray(state));

  return (
    <div>
      <LogoHeader />
      <div className="section">
        {applications.map((app) => (
          <ApplicationButton
            key={app.id}
            applicationId={app.id}
            caption={app.caption}
            iconClassNames={app.iconClassNames}
          />
        ))}
      </div>
      <ScreenToaster />
    </div>
  );
};

export default ApplicationsListScreen;
