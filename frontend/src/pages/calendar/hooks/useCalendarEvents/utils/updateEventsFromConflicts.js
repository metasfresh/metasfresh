export const updateEventsFromConflicts = (eventsArray, conflictsArray) => {
  if (!eventsArray) {
    return eventsArray;
  }

  const entryIdsWithConflicts = [];
  if (conflictsArray) {
    conflictsArray.forEach((conflict) => {
      if (conflict.status === 'CONFLICT') {
        entryIdsWithConflicts.push(conflict.entryId1);
        entryIdsWithConflicts.push(conflict.entryId2);
      }
    });
  }

  let hasChanges = false;
  const changedEvents = eventsArray.map((event) => {
    const conflictFlagNew = entryIdsWithConflicts.includes(event.id);
    if (!!event.conflict === conflictFlagNew) {
      return event;
    } else {
      const newEvent = {
        ...event,
        conflict: conflictFlagNew,
      };
      hasChanges = true;
      return newEvent;
    }
  });

  if (!hasChanges) {
    return eventsArray;
  }

  console.log('updateEventsFromConflicts', {
    changedEvents,
    eventsArray,
    conflictsArray,
  });
  return changedEvents;
};
