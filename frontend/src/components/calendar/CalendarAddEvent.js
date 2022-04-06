import PropTypes from 'prop-types';
import { RawList } from '../widget/List/RawList';
import React from 'react';

// const convertToMoment = (dateObj) => {
//   if (!dateObj) {
//     return null;
//   } else if (Moment.isMoment(dateObj)) {
//     return dateObj;
//   } else {
//     return Moment(dateObj);
//   }
// };
//
// const convertMovementToString = (movement) => {
// };

const CalendarAddEvent = ({
  calendars,
  date,
  allDay = true,
  onAddEvent,
  onCancel,
}) => {
  const [selectedCalendar, setSelectedCalendar] = React.useState(
    calendars && calendars[0]
  );
  const [isCalendarFieldFocused, setIsCalendarFieldFocused] =
    React.useState(false);
  const [isCalendarFieldToggled, setIsCalendarFieldToggled] =
    React.useState(false);

  const [title, setTitle] = React.useState('');

  const handleClickOK = () => {
    onAddEvent({
      title: title,
      start: date,
      end: date,
      allDay: allDay,
      resourceId: selectedCalendar.key,
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
          <RawList
            rank="primary"
            list={calendars || []}
            onSelect={setSelectedCalendar}
            selected={selectedCalendar}
            isFocused={isCalendarFieldFocused}
            isToggled={isCalendarFieldToggled}
            onOpenDropdown={() => setIsCalendarFieldToggled(true)}
            onCloseDropdown={() => setIsCalendarFieldToggled(false)}
            onFocus={() => setIsCalendarFieldFocused(true)}
            onBlur={() => setIsCalendarFieldFocused(false)}
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
  date: PropTypes.string.isRequired,
  allDay: PropTypes.bool,
  onAddEvent: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default CalendarAddEvent;
