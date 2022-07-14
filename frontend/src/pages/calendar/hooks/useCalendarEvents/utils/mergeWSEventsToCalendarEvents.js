import {
  WSEventType_addOrChange,
  WSEventType_remove,
} from '../../useCalendarWebsocketEvents';
import { isEqualEvents } from './isEqualEvents';

export const mergeWSEventsToCalendarEvents = (eventsArray, wsEventsArray) => {
  console.groupCollapsed('mergeWSEventsToCalendarEvents', {
    eventsArray,
    wsEventsArray,
  });

  if (!wsEventsArray || wsEventsArray.length === 0) {
    console.log('=> empty wsEventsArray => NO CHANGE');
    console.groupEnd();
    return eventsArray;
  }

  const resultEventsById = [];
  if (eventsArray) {
    eventsArray.forEach((event) => {
      resultEventsById[event.id] = event;
    });
  }

  let hasChanges = false;
  wsEventsArray.forEach((wsEvent) => {
    if (wsEvent.type === WSEventType_addOrChange) {
      if (!isEqualEvents(resultEventsById[wsEvent.entry.id], wsEvent.entry)) {
        console.log('changing event', {
          oldEvent: resultEventsById[wsEvent.entry.id],
          newEvent: wsEvent.entry,
        });
        resultEventsById[wsEvent.entry.id] = wsEvent.entry;
        hasChanges = true;
      } else {
        console.log('NOT changing event because it is the same', {
          oldEvent: resultEventsById[wsEvent.entry.id],
          newEvent: wsEvent.entry,
        });
      }
    } else if (wsEvent.type === WSEventType_remove) {
      if (wsEvent.entryId in resultEventsById) {
        delete resultEventsById[wsEvent.entryId];
        hasChanges = true;
      }
    } else {
      console.warn('Unhandled websocket event: ', wsEvent);
    }
  });

  if (!hasChanges) {
    console.log('=> same => NO CHANGE');
    console.groupEnd();
    return eventsArray;
  }

  console.log('=> new array of ', resultEventsById);
  console.groupEnd();
  return Object.values(resultEventsById);
};
