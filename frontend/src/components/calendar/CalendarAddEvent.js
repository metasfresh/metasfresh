import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import CalendarsDropDown from './CalendarsDropDown';
import ResourcesDropDown from './ResourcesDropDown';

const getCalendarById = (availableCalendars, calendarId) => {
  if (!availableCalendars || !calendarId) {
    return null;
  }

  return availableCalendars.find(
    (calendar) => calendar.calendarId === calendarId
  );
};

const computeSelectedResource = (availableResources, selectedResourceId) => {
  if (!availableResources || availableResources.length === 0) {
    return null;
  }

  if (!selectedResourceId) {
    return availableResources[0];
  }

  const selectedResource = availableResources.find(
    (availableResource) => availableResource.id === selectedResourceId
  );

  return selectedResource ? selectedResource : availableResources[0];
};

const CalendarAddEvent = ({
  availableCalendars,
  initialEvent, // { calendarId, resourceId, title, start, end, allDay }
  onOK,
  onCancel,
}) => {
  const [form, setForm] = React.useState({
    calendar: null,
    availableResources: [],
    resource: null,
    title: '',
    start: null,
    end: null,
    allDay: true,
  });

  useEffect(() => {
    const calendar = getCalendarById(
      availableCalendars,
      initialEvent.calendarId
    );

    const availableResources = calendar?.resources ?? [];
    const resource = computeSelectedResource(
      availableResources,
      initialEvent.resourceId
    );

    setForm({
      id: initialEvent.id,
      calendar,
      availableResources,
      resource,
      title: initialEvent.title,
      start: initialEvent.start,
      end: initialEvent?.end || initialEvent.start,
      allDay: initialEvent.allDay,
    });
  }, [initialEvent]);

  const onCalendarChanged = (calendar) => {
    const availableResources = calendar?.resources ?? [];
    const resource = computeSelectedResource(
      availableResources,
      form.resourceId
    );
    setForm({ ...form, calendar, availableResources, resource });
  };

  const handleClickOK = () => {
    onOK({
      id: form.id,
      calendarId: form.calendar?.calendarId || null,
      resourceId: form.resource?.id || null,
      title: form.title,
      start: form.start,
      end: form.end,
      allDay: form.allDay,
    });
  };

  return (
    <div>
      <div>
        <div>ID</div>
        <div>{form.id}</div>
      </div>
      <div>
        <div>Calendar:</div>
        <div>
          <CalendarsDropDown
            calendars={availableCalendars}
            selectedCalendar={form.calendar}
            onSelect={onCalendarChanged}
          />
        </div>
      </div>
      <div>
        <div>Resource:</div>
        <div>
          <ResourcesDropDown
            resources={form.availableResources}
            selectedResource={form.resource}
            onSelect={(resource) => setForm({ ...form, resource })}
          />
        </div>
      </div>
      <div>
        <div>Start:</div>
        <div>{`${form.start}`}</div>
      </div>
      <div>
        <div>End:</div>
        <div>{`${form.end}`}</div>
      </div>
      <div>
        <div>Title:</div>
        <div>
          <input
            type="text"
            value={form.title}
            onChange={(e) => setForm({ ...form, title: e.target.value })}
          />
        </div>
      </div>
      <div>
        <div>All Day:</div>
        <div>{form.allDay}</div>
      </div>
      <div>
        <button onClick={handleClickOK}>OK</button>
        <button
          onClick={() => {
            onCancel();
          }}
        >
          Cancel
        </button>
      </div>
    </div>
  );
};

CalendarAddEvent.propTypes = {
  availableCalendars: PropTypes.array.isRequired,
  initialEvent: PropTypes.object.isRequired,
  onOK: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default CalendarAddEvent;
