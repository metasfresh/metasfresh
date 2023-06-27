import { isEqualEntries } from './isEqualEntries';
import { indexEntriesById } from './indexEntriesById';

export const mergeCalendarEntriesArrayToArray = (
  entriesArray,
  entriesToAdd
) => {
  console.groupCollapsed('mergeCalendarEntriesArrayToArray', {
    entriesArray,
    entriesToAdd,
  });

  if (!entriesToAdd || entriesToAdd.length === 0) {
    console.log('=> empty entriesToAdd => NO CHANGE');
    console.groupEnd();

    return entriesArray;
  }

  const resultEntriesById = indexEntriesById(entriesArray);

  let hasChanges = false;
  entriesToAdd.forEach((entry) => {
    if (!isEqualEntries(resultEntriesById[entry.id], entry)) {
      console.log('changing entry', {
        oldEntry: resultEntriesById[entry.id],
        newEntry: entry,
      });
      resultEntriesById[entry.id] = entry;
      hasChanges = true;
    } else {
      console.log('NOT changing entry because it is the same', {
        oldEntry: resultEntriesById[entry.id],
        newEntry: entry,
      });
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
