import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import CalendarsDropDown from './CalendarsDropDown';
import ResourcesDropDown from './ResourcesDropDown';
import DatePicker from '../widget/DateTime/DatePicker';
import Checkbox from '../widget/Checkbox';
import { normalizeDateTime } from '../../containers/calendar/calendarUtils';
import ModalComponent from '../modal/ModalComponent';
import ModalButton from '../modal/ModalButton';

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

  const isNewEvent = !form.id;

  const renderModalButtons = () => {
    return (
      <>
        {!isNewEvent && (
          <ModalButton onClick={() => onDelete(form.id)}>Delete</ModalButton>
        )}
        <ModalButton onClick={onCancel}>Cancel</ModalButton>
        <ModalButton onClick={handleClickOK}>Done</ModalButton>
      </>
    );
  };

  return (
    <ModalComponent
      title={isNewEvent ? 'Add event' : 'Edit event'}
      renderButtons={renderModalButtons}
      onClickOutside={onCancel}
      shortcutActions={{
        cancel: onCancel,
      }}
    >
      <div className="window-wrapper calendarEventEditor container-fluid">
        <div className="sections-wrapper">
          <div className="panel">
            <FormRow label="Calendar">
              <CalendarsDropDown
                calendars={availableCalendars}
                selectedCalendar={form.calendar}
                onSelect={onCalendarChanged}
              />
            </FormRow>
            <FormRow label="Resource">
              <ResourcesDropDown
                resources={form.availableResources}
                selectedResource={form.resource}
                onSelect={(resource) => setForm({ ...form, resource })}
              />
            </FormRow>
            <FormRow label="Start date">
              <div className="input-block input-icon-container input-secondary pulse-off">
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
            </FormRow>
            <FormRow label="End date">
              <div className="input-block input-icon-container input-secondary pulse-off">
                <DatePicker
                  dateFormat={true}
                  timeFormat={!form.allDay}
                  value={form.end}
                  inputProps={{}}
                  patch={(date) =>
                    setForm({ ...form, end: normalizeDateTime(date) })
                  }
                />
              </div>
            </FormRow>
            <FormRow label="Title">
              <input
                className="input-field js-input-field"
                type="text"
                value={form.title ?? ''}
                onChange={(e) => setForm({ ...form, title: e.target.value })}
              />
            </FormRow>
            <FormRow label="All day">
              <Checkbox
                widgetData={{
                  value: form.allDay,
                }}
                handlePatch={(fieldName, value) => {
                  setForm({ ...form, allDay: !!value });
                  return Promise.resolve();
                }}
              />
            </FormRow>
          </div>
        </div>
      </div>
    </ModalComponent>
  );
};

CalendarEventEditor.propTypes = {
  availableCalendars: PropTypes.array.isRequired,
  initialEvent: PropTypes.object.isRequired,
  onOK: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
  onDelete: PropTypes.func.isRequired,
};

const FormRow = ({ label, children }) => {
  return (
    <div className="elements-line">
      <div className="row form-group form-field-Value">
        <div className="form-control-label col-sm-3">{label}</div>
        <div className="col-sm-9 ">
          <div className="input-body-container">
            <span />
            {children}
          </div>
        </div>
      </div>
    </div>
  );
};

FormRow.propTypes = {
  label: PropTypes.string,
  children: PropTypes.node.isRequired,
};

export default CalendarEventEditor;
