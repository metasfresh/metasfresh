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
  conflicts
    .filter((conflict) => conflict.status === 'CONFLICT')
    .forEach((conflict) => {
      const resourceId1 = entriesById[conflict.entryId1]?.resourceId;
      if (resourceId1) {
        resourceIdsWithConflicts[resourceId1] = true;
      }

      const resourceId2 = entriesById[conflict.entryId2]?.resourceId;
      if (resourceId2) {
        resourceIdsWithConflicts[resourceId2] = true;
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
