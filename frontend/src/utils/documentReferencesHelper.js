import { getQueryString } from '.';

export function buildRelatedDocumentsViewUrl({
  targetWindowId,
  windowId,
  documentId,
  tabId,
  rowIds,
}) {
  const urlParams = getQueryString({
    refType: windowId,
    refId: documentId,
    refTabId: tabId,
    refRowIds: tabId ? JSON.stringify(rowIds || []) : undefined,
  });

  return `/window/${targetWindowId}?${urlParams}`;
}

export function mergePartialGroupToGroupsArray(groups, groupToAdd) {
  if (!groupToAdd) {
    return groups;
  }

  var groupAdded = false;

  const result = groups.map((group) => {
    if (!groupAdded && group.caption === groupToAdd.caption) {
      groupAdded = true;
      return mergeReferencesToGroup(group, groupToAdd.references);
    } else {
      return group;
    }
  });

  if (!groupAdded) {
    result.push(groupToAdd);
    groupAdded = true;
  }

  //
  // Sort groups by caption alphabetically, keep miscGroup last.
  return result.sort(compareGroupsByCaption);
}

/** Sort groups by caption alphabetically, keep miscGroup last. */
function compareGroupsByCaption(group1, group2) {
  if (group1.miscGroup == group2.miscGroup) {
    return group1.caption.localeCompare(group2.caption);
  } else if (group1.miscGroup) {
    return +1; // keep misc group last
  } else {
    return -1;
  }
}

function mergeReferencesToGroup(group, referencesToAdd) {
  if (!referencesToAdd || !referencesToAdd.length) {
    return group;
  }

  return {
    ...group,
    references: mergeReferencesToReferences(group.references, referencesToAdd),
  };
}

export function mergeReferencesToReferences(
  existingReferences,
  referencesToAdd
) {
  if (!referencesToAdd) {
    return existingReferences;
  }

  const referencesToAddById = referencesToAdd.reduce(
    (accum, reference) => ({ ...accum, [reference.id]: reference }),
    {}
  );

  // Merge referencesToAdd to existing references
  const existingReferencesMerged = existingReferences.map(
    (existingReference) => {
      const referenceToAdd = referencesToAddById[existingReference.id];
      delete referencesToAddById[existingReference.id];

      return combineReferences(existingReference, referenceToAdd);
    }
  );

  return [
    ...existingReferencesMerged,
    ...Object.values(referencesToAddById), // remaining refences to add
  ].sort(compareReferencesByCaption);
}

function combineReferences(existingReference, referenceToAdd) {
  if (referenceToAdd && referenceToAdd.priority < existingReference.priority) {
    return referenceToAdd;
  } else {
    return existingReference;
  }
}

function compareReferencesByCaption(reference1, reference2) {
  return reference1.caption.localeCompare(reference2.caption);
}
