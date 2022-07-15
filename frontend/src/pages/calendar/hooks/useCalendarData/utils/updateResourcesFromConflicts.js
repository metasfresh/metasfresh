import { indexEntriesById } from './indexEntriesById';

export const updateResourcesFromConflicts = ({
  resources,
  conflicts,
  entries,
}) => {
  if (!resources) {
    return resources;
  }

  const resourceIdsWithConflicts = extractResourceIdsWithConflicts({
    entries,
    conflicts,
  });
  if (resourceIdsWithConflicts.length === 0) {
    return resources;
  }

  const changedResources = [];
  let hasChanges = false;
  resources.forEach((resource) => {
    const newConflictFlag = resourceIdsWithConflicts.includes(resource.id);
    const changedResource = updateResource(resource, newConflictFlag);
    changedResources.push(changedResource);
    if (changedResource !== resource) {
      hasChanges = true;
    }
  });

  if (!hasChanges) {
    return resources;
  } else {
    return changedResources;
  }
};

const extractResourceIdsWithConflicts = ({ entries, conflicts }) => {
  if (!conflicts || !entries) {
    return [];
  }

  const entriesById = indexEntriesById(entries);

  const resourceIdsWithConflicts = {};
  conflicts.forEach((conflict) => {
    const entry1 = entriesById[conflict.entryId1];
    if (entry1?.conflict) {
      resourceIdsWithConflicts[entry1.resourceId] = true;
    }

    const entry2 = entriesById[conflict.entryId2];
    if (entry2?.conflict) {
      resourceIdsWithConflicts[entry2.resourceId] = true;
    }
  });

  return Object.keys(resourceIdsWithConflicts);
};

const updateResource = (resource, newConflictFlag) => {
  const oldConflictFlag = !!resource.conflict;
  if (oldConflictFlag !== newConflictFlag) {
    return {
      ...resource,
      conflict: newConflictFlag,
    };
  } else {
    return resource;
  }
};
