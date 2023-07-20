export const updateEntriesFromConflicts = (entriesArray, conflictsArray) => {
  // console.groupCollapsed('updateEntriesFromConflicts', {
  //   entriesArray,
  //   conflictsArray,
  // });

  if (!entriesArray) {
    // console.log('=> empty array');
    // console.groupEnd();

    return entriesArray;
  }

  const entryIdsWithConflicts = extractEntryIdsWithConflicts(conflictsArray);

  let hasChanges = false;
  const changedEntries = entriesArray.map((entry) => {
    const conflict = entryIdsWithConflicts.includes(entry.id);
    const newEntry = updateEntry(entry, conflict);
    if (newEntry !== entry) {
      hasChanges = true;
    }
    return newEntry;
  });

  // console.log('=>', {
  //   hasChanges,
  //   changedEntries,
  //   entriesArray,
  //   conflictsArray,
  //   entryIdsWithConflicts,
  // });
  // console.groupEnd();

  return hasChanges ? changedEntries : entriesArray;
};

const extractEntryIdsWithConflicts = (conflictsArray) => {
  const entryIdsWithConflicts = [];
  if (conflictsArray) {
    conflictsArray.forEach((conflict) => {
      if (conflict.status === 'CONFLICT') {
        entryIdsWithConflicts.push(conflict.entryId1);
        entryIdsWithConflicts.push(conflict.entryId2);
      }
    });
  }
  return entryIdsWithConflicts;
};

const updateEntry = (entry, conflict) => {
  const conflictOld = !!entry.conflict;
  return conflict === conflictOld ? entry : { ...entry, conflict };
};
