import { isEqualEvents } from './isEqualEvents';

export const mergeCalendarEventsArrayToArray = (eventsArray, eventsToAdd) => {
  console.log('mergeCalendarEventsArrayToArray', { eventsArray, eventsToAdd });
  if (!eventsToAdd || eventsToAdd.length === 0) {
    console.log('=> empty eventsToAdd => NO CHANGE');
    return eventsArray;
  }

  const resultEventsById = [];
  eventsArray.forEach((event) => {
    resultEventsById[event.id] = event;
  });

  let hasChanges = false;
  eventsToAdd.forEach((event) => {
    if (!isEqualEvents(resultEventsById[event.id], event)) {
      console.log('changing event', {
        oldEvent: resultEventsById[event.id],
        newEvent: event,
      });
      resultEventsById[event.id] = event;
      hasChanges = true;
    } else {
      console.log('NOT changing event because it is the same', {
        oldEvent: resultEventsById[event.id],
        newEvent: event,
      });
    }
  });

  if (!hasChanges) {
    console.log('=> same => NO CHANGE');
    return eventsArray;
  }

  console.log('=> new array of ', resultEventsById);
  return Object.values(resultEventsById);
};
