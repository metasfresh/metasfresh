export const updateEntriesFromConflicts = (entriesArray, conflictsArray) => {
  if (!entriesArray) {
    return entriesArray;
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
  const changedEntries = entriesArray.map((entry) => {
    const conflictFlagNew = entryIdsWithConflicts.includes(entry.id);
    if (!!entry.conflict === conflictFlagNew) {
      return entry;
    } else {
      const newEntry = {
        ...entry,
        conflict: conflictFlagNew,
      };
      hasChanges = true;
      return newEntry;
    }
  });

  if (!hasChanges) {
    return entriesArray;
  }

  console.log('updateEntriesFromConflicts', {
    changedEntries,
    entriesArray,
    conflictsArray,
  });
  return changedEntries;
};
