export const indexEntriesById = (entriesArray) => {
  const entriesById = {};
  if (entriesArray) {
    entriesArray.forEach((entry) => {
      entriesById[entry.id] = entry;
    });
  }

  return entriesById;
};
