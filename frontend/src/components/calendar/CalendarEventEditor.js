import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import CalendarsDropDown from './CalendarsDropDown';
import ResourcesDropDown from './ResourcesDropDown';
import DatePicker from '../widget/DateTime/DatePicker';
import Checkbox from '../widget/Checkbox';
import { normalizeDateTime } from '../../containers/calendar/calendarUtils';

const getCalendarById = (calendarsArray, calendarId) => {
  if (!calendarsArray || !calendarId) {
    return null;
  }

  return calendarsArray.find((calendar) => calendar.calendarId === calendarId);
};

const computeSelectedResource = (
  availableResources,
  selectedResourceId,
  fallbackToFirstAvailableResource
) => {
  if (!availableResources || availableResources.length === 0) {
    return null;
  }

  if (!selectedResourceId) {
    return fallbackToFirstAvailableResource ? availableResources[0] : null;
  }

  const selectedResource = availableResources.find(
    (availableResource) => availableResource.id === selectedResourceId
  );

  if (selectedResource != null) {
    return selectedResource;
  } else if (fallbackToFirstAvailableResource) {
    return availableResources[0];
  } else {
    return null;
  }
};

const CalendarEventEditor = ({
  availableCalendars,
  initialEvent, // { calendarId, resourceId, title, start, end, allDay }
  onOK,
  onCancel,
  onDelete,
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
      initialEvent.resourceId,
      false
    );

    setForm({
      id: initialEvent.id,
      calendar,
      availableResources,
      resource,
      title: initialEvent.title,
      start: normalizeDateTime(initialEvent.start),
      end: normalizeDateTime(initialEvent?.end || initialEvent.start),
      allDay: initialEvent.allDay,
    });
  }, [initialEvent]);

  const onCalendarChanged = (calendar) => {
    const availableResources = calendar?.resources ?? [];
    const resource = computeSelectedResource(
      availableResources,
      form.resourceId,
      true
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
        <div>
          <DatePicker
            dateFormat={true}
            timeFormat={!form.allDay}
            value={form.start}
            inputProps={{}}
            patch={(date) =>
              setForm({ ...form, start: normalizeDateTime(date) })
            }
          />
        </div>
      </div>
      <div>
        <div>End:</div>
        <div>
          <DatePicker
            dateFormat={true}
            timeFormat={!form.allDay}
            value={form.end}
            inputProps={{}}
            patch={(date) => setForm({ ...form, end: normalizeDateTime(date) })}
          />
        </div>
      </div>
      <div>
        <div>Title:</div>
        <div>
          <input
            type="text"
            value={form.title ?? ''}
            onChange={(e) => setForm({ ...form, title: e.target.value })}
          />
        </div>
      </div>
      <div>
        <div>All Day:</div>
        <div>
          {form.allDay}
          <Checkbox
            widgetData={{
              value: form.allDay,
            }}
            handlePatch={(fieldName, value) => {
              setForm({ ...form, allDay: !!value });
              return Promise.resolve();
            }}
          />
        </div>
      </div>
      <div>
        <button onClick={handleClickOK}>OK</button>
        <button onClick={onCancel}>Cancel</button>
        {form.id && <button onClick={() => onDelete(form.id)}>Delete</button>}
      </div>
    </div>
  );
};

CalendarEventEditor.propTypes = {
  availableCalendars: PropTypes.array.isRequired,
  initialEvent: PropTypes.object.isRequired,
  onOK: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
  onDelete: PropTypes.func.isRequired,
};

export default CalendarEventEditor;
