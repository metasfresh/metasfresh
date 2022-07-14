import { isSameMoment } from '../../../utils/calendarUtils';

export const isEqualEvents = (event1, event2) => {
  if (event1 === event2) {
    return true;
  }
  if (!event1 || !event2) {
    return false;
  }

  return (
    event1.calendarId === event2.calendarId &&
    event1.id === event2.id &&
    event1.resourceId === event2.resourceId &&
    event1.title === event2.title &&
    isSameMoment(event1.start, event2.start) &&
    isSameMoment(event1.end, event2.end) &&
    event1.allDay === event2.allDay &&
    event1.editable === event2.editable &&
    event1.color === event2.color &&
    event1.url === event2.url &&
    !!event1.conflict === !!event2.conflict
  );
};
