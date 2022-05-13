import React from 'react';
import PropTypes from 'prop-types';
import CalendarsDropDown from './CalendarsDropDown';
import ResourcesDropDown from './ResourcesDropDown';

const computeSelectedResource = (availableResources, selectedResource) => {
  if (!availableResources || availableResources.length === 0) {
    return null;
  }

  if (selectedResource == null) {
    return availableResources[0];
  }

  return availableResources.find(
    (availableResource) => availableResource.id === selectedResource.id
  )
    ? selectedResource
    : availableResources[0];
};

const CalendarAddEvent = ({
  calendars,
  date,
  allDay = true,
  onAddEvent,
  onCancel,
}) => {
  const [calendar, setCalendar] = React.useState(calendars?.[0] ?? null);

  const [availableResources, setAvailableResources] = React.useState(
    calendar?.resources ?? []
  );
  const [resource, setResource] = React.useState(
    availableResources?.[0] ?? null
  );

  const [title, setTitle] = React.useState('');

  const onCalendarChanged = (newCalendar) => {
    setCalendar(newCalendar);

    const newAvailableResources = newCalendar?.resources ?? [];
    setAvailableResources(newAvailableResources);

    let newSelectedResource = computeSelectedResource(
      newAvailableResources,
      resource
    );
    setResource(newSelectedResource);
  };

  const handleClickOK = () => {
    onAddEvent({
      calendarId: calendar.calendarId,
      resourceId: resource.id,
      title: title,
      start: date,
      end: date,
      allDay: allDay,
    });
  };
  const handleClickCancel = () => {
    onCancel();
  };

  return (
    <div>
      <div>
        <div>Calendar:</div>
        <div>
          <CalendarsDropDown
            calendars={calendars}
            selectedCalendar={calendar}
            onSelect={onCalendarChanged}
          />
        </div>
      </div>
      <div>
        <div>Resource:</div>
        <div>
          <ResourcesDropDown
            resources={calendar?.resources ?? []}
            selectedResource={resource}
            onSelect={setResource}
          />
        </div>
      </div>
      <div>
        <div>Date:</div>
        <div>{`${date}`}</div>
      </div>
      <div>
        <div>Title:</div>
        <div>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>
      </div>
      <div>
        <button onClick={handleClickOK}>OK</button>
        <button onClick={handleClickCancel}>Cancel</button>
      </div>
    </div>
  );
};

CalendarAddEvent.propTypes = {
  calendars: PropTypes.array.isRequired,
  date: PropTypes.oneOfType([PropTypes.string, PropTypes.object]).isRequired,
  allDay: PropTypes.bool,
  onAddEvent: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default CalendarAddEvent;
