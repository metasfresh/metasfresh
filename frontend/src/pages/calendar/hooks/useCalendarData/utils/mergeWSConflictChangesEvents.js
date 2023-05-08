export const mergeWSConflictChangesEvents = (conflictsArray, wsEventsArray) => {
  if (!wsEventsArray || wsEventsArray.length === 0) {
    return conflictsArray;
  }

  let changedConflictsArray = conflictsArray;
  wsEventsArray.forEach((wsEvent) => {
    changedConflictsArray = mergeSingleWSConflictChangesEvent(
      changedConflictsArray,
      wsEvent
    );
  });

  return changedConflictsArray;
};

const mergeSingleWSConflictChangesEvent = (conflictsArray, wsEvent) => {
  console.groupCollapsed('mergeSingleWSConflictChangesEvent', {
    conflictsArray,
    wsEvent,
  });

  //
  // Remove conflicts which are affected by this event
  const affectedEntryIds = wsEvent.affectedEntryIds;
  let changedConflictsArray = conflictsArray.filter(
    (conflict) =>
      !affectedEntryIds.includes(conflict.entryId1) &&
      !affectedEntryIds.includes(conflict.entryId2)
  );
  console.log('conflicts after removing affected:', changedConflictsArray);

  //
  // Add conflicts from this event
  changedConflictsArray = [...changedConflictsArray, ...wsEvent.conflicts];
  console.log('conflicts after adding new ones:', changedConflictsArray);

  console.groupEnd();
  return changedConflictsArray;
};
