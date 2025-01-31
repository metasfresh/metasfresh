export const extractCalendarIdsFromArray = (calendarsArray) => {
  if (!calendarsArray) return [];
  return calendarsArray.map((calendar) => calendar.calendarId);
};
