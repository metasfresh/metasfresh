import {
  WSEventType_entryAddOrChange,
  WSEventType_entryRemove,
} from '../../useCalendarWebsocketEvents';
import { isEqualEntries } from './isEqualEntries';
import { indexEntriesById } from './indexEntriesById';

export const mergeWSEntryEvents = (entriesArray, wsEventsArray) => {
  console.groupCollapsed('mergeWSEventsToCalendarEntries', {
    entriesArray,
    wsEventsArray,
  });

  if (!wsEventsArray || wsEventsArray.length === 0) {
    console.log('=> empty wsEventsArray => NO CHANGE');
    console.groupEnd();
    return entriesArray;
  }

  const resultEntriesById = indexEntriesById(entriesArray);

  let hasChanges = false;
  wsEventsArray.forEach((wsEvent) => {
    if (wsEvent.type === WSEventType_entryAddOrChange) {
      if (!isEqualEntries(resultEntriesById[wsEvent.entry.id], wsEvent.entry)) {
        console.log('changing entry', {
          oldEntry: resultEntriesById[wsEvent.entry.id],
          newEntry: wsEvent.entry,
        });
        resultEntriesById[wsEvent.entry.id] = wsEvent.entry;
        hasChanges = true;
      } else {
        console.log('NOT changing entry because it is the same', {
          oldEntry: resultEntriesById[wsEvent.entry.id],
          newEntry: wsEvent.entry,
        });
      }
    } else if (wsEvent.type === WSEventType_entryRemove) {
      if (wsEvent.entryId in resultEntriesById) {
        delete resultEntriesById[wsEvent.entryId];
        hasChanges = true;
      }
    } else {
      console.warn('Unhandled websocket event: ', wsEvent);
    }
  });

  if (!hasChanges) {
    console.log('=> same => NO CHANGE');
    console.groupEnd();

    return entriesArray;
  }

  console.log('=> new array of ', resultEntriesById);
  console.groupEnd();

  return Object.values(resultEntriesById);
};
