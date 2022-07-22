import React, { useMemo } from 'react';
import PropTypes from 'prop-types';
import Calendar from './Calendar';
import Header from '../../components/header/Header';

import history from '../../services/History';
import { getQueryString } from '../../utils';

const updateURI = (location, { simulationId }) => {
  const queryParams = getQueryString({
    ...location.query,
    simulationId,
  });

  history.replace(`${location.pathname}?${queryParams}`);
};

const CalendarPage = ({ location }) => {
  const onlyResourceIds = useMemo(
    () => (location.query.resourceId ? [location.query.resourceId] : null),
    [location.query.resourceId]
  );

  return (
    <div>
      <Header />
      <div className="header-sticky-distance js-unselect panel-vertical-scroll dashboard">
        <Calendar
          simulationId={location.query.simulationId}
          onlyResourceIds={onlyResourceIds}
          onlyProjectId={location.query.projectId}
          onlyCustomerId={location.query.customerId}
          onParamsChanged={({ simulationId }) =>
            updateURI(location, { simulationId })
          }
        />
      </div>
    </div>
  );
};

CalendarPage.propTypes = {
  location: PropTypes.object.isRequired,
};

export default CalendarPage;
