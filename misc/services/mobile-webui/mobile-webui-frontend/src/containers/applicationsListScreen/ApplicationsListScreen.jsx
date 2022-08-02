import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';

import { getAvailableApplicationsArray } from '../../reducers/applications';

import ScreenToaster from '../../components/ScreenToaster';
import ApplicationButton from './ApplicationButton';
import LogoHeader from '../../components/LogoHeader';

const ApplicationsListScreen = () => {
  const applications = useSelector((state) => getAvailableApplicationsArray(state));

  useEffect(() => {
    document.title = 'mobile UI';
  }, []);

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
