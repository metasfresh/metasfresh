import { indexEntriesById } from './indexEntriesById';

export const updateResourcesFromConflicts = ({
  resources,
  conflicts,
  entries,
}) => {
  console.groupCollapsed('updateResourcesFromConflicts', {
    resources,
    conflicts,
    entries,
  });

  if (!resources) {
    console.log('=> empty resources, returning empty');
    console.groupEnd();

    return resources;
  }

  const conflictCountsByResourceId = computeConflictCountsByResourceId({
    entries,
    conflicts,
  });
  console.log('conflictCountsByResourceId', conflictCountsByResourceId);

  const entriesCountsByResourceId = computeEntriesCountsByResourceId({
    entries,
  });
  console.log('entriesCountsByResourceId', entriesCountsByResourceId);

  const changedResources = [];
  let hasChanges = false;
  resources.forEach((resource) => {
    const changedResource = updateResource(resource, {
      conflictsCount: conflictCountsByResourceId[resource.id] ?? 0,
      entriesCount: entriesCountsByResourceId[resource.id] ?? 0,
    });
    changedResources.push(changedResource);
    if (changedResource !== resource) {
      hasChanges = true;
    }
  });

  console.log('=> returning', { changedResources, hasChanges });
  console.groupEnd();

  return hasChanges ? changedResources : resources;
};

const computeConflictCountsByResourceId = ({ entries, conflicts }) => {
  if (!conflicts || !entries) {
    return {};
  }

  const entriesById = indexEntriesById(entries);

  const conflictCountsByResourceId = {};
  conflicts
    .filter((conflict) => conflict.status === 'CONFLICT')
    .forEach((conflict) => {
      const resourceId1 = entriesById[conflict.entryId1]?.resourceId;

      if (resourceId1) {
        const count = conflictCountsByResourceId[resourceId1] ?? 0;
        conflictCountsByResourceId[resourceId1] = count + 1;
      }

      const resourceId2 = entriesById[conflict.entryId2]?.resourceId;
      if (resourceId2 && resourceId2 !== resourceId1) {
        const count = conflictCountsByResourceId[resourceId2] ?? 0;
        conflictCountsByResourceId[resourceId2] = count + 1;
      }
    });

  return conflictCountsByResourceId;
};

const computeEntriesCountsByResourceId = ({ entries }) => {
  if (!entries) {
    return {};
  }

  const entriesCountByResourceId = {};
  entries.forEach((entry) => {
    const resourceId = entry.resourceId;
    const count = entriesCountByResourceId[resourceId] ?? 0;
    entriesCountByResourceId[resourceId] = count + 1;
  });

  return entriesCountByResourceId;
};

const updateResource = (resource, { conflictsCount, entriesCount }) => {
  console.log('updateResource', { resource, conflictsCount, entriesCount });
  return resource.conflictsCount !== conflictsCount ||
    resource.entriesCount !== entriesCount
    ? { ...resource, conflictsCount, entriesCount }
    : resource;
};
