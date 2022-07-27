import React, { useMemo } from 'react';
import PropTypes from 'prop-types';
import Calendar from './Calendar';
import Header from '../../components/header/Header';

//import history from '../../services/History';
import { useHistory } from 'react-router-dom';
import { buildURL } from '../../utils';

import './CalendarPage.scss';

const DEFAULT_CALENDAR_VIEW = 'resourceTimelineYear';

const updateURI = (
  history,
  location,
  { simulationId, onlyResourceIds, onlyProjectId, onlyCustomerId, view }
) => {
  const url = buildURL(location.pathname, {
    ...location.query,
    simulationId,
    resourceIds: onlyResourceIds ? onlyResourceIds.join(',') : null,
    projectId: onlyProjectId,
    customerId: onlyCustomerId,
    view,
  });

  history.push(url);
};

const CalendarPage = ({ location }) => {
  const history = useHistory();

  const onlyResourceIds = useMemo(
    () =>
      location.query.resourceIds ? location.query.resourceIds.split(',') : null,
    [location.query.resourceId]
  );

  return (
    <div>
      <Header />
      <div className="calendar-container">
        <Calendar
          view={location.query?.view ?? DEFAULT_CALENDAR_VIEW}
          simulationId={location.query.simulationId}
          onlyResourceIds={onlyResourceIds}
          onlyProjectId={location.query.projectId}
          onlyCustomerId={location.query.customerId}
          onParamsChanged={(params) => updateURI(history, location, params)}
        />
      </div>
    </div>
  );
};

CalendarPage.propTypes = {
  location: PropTypes.object.isRequired,
};

export default CalendarPage;
