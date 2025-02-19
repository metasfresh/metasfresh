import { isSameMoment } from '../../../utils/calendarUtils';

export const isEqualEntries = (entry1, entry2) => {
  if (entry1 === entry2) {
    return true;
  }
  if (!entry1 || !entry2) {
    return false;
  }

  return (
    entry1.calendarId === entry2.calendarId &&
    entry1.id === entry2.id &&
    entry1.resourceId === entry2.resourceId &&
    entry1.title === entry2.title &&
    isSameMoment(entry1.start, entry2.start) &&
    isSameMoment(entry1.end, entry2.end) &&
    entry1.allDay === entry2.allDay &&
    entry1.editable === entry2.editable &&
    entry1.color === entry2.color &&
    entry1.url === entry2.url &&
    !!entry1.conflict === !!entry2.conflict
  );
};
