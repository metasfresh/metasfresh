import React, { useMemo } from 'react';
import PropTypes from 'prop-types';
import Calendar from './Calendar';
import Header from '../../components/header/Header';

//import history from '../../services/History';
import { useHistory } from 'react-router-dom';
import { buildURL } from '../../utils';

import './CalendarPage.scss';

const DEFAULT_CALENDAR_VIEW = 'resourceTimelineMonth';

const updateURI = (
  history,
  location,
  {
    simulationId,
    onlyResourceIds,
    onlyProjectId,
    onlyCustomerId,
    onlyResponsibleId,
    view,
  }
) => {
  // IMPORTANT: keep the URL query param names
  // in sync with de.metas.ui.web.process.json.JSONProcessInstanceResult.JSONOpenCalendarAction
  const url = buildURL(location.pathname, {
    ...location.query,
    simulationId,
    resourceId: onlyResourceIds ? onlyResourceIds.join(',') : null,
    projectId: onlyProjectId,
    customerId: onlyCustomerId,
    responsibleId: onlyResponsibleId,
    view,
  });

  history.push(url);
};

const CalendarPage = ({ location }) => {
  const history = useHistory();

  const onlyResourceIds = useMemo(
    () =>
      location.query.resourceId ? location.query.resourceId.split(',') : null,
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
          onlyResponsibleId={location.query.responsibleId}
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
