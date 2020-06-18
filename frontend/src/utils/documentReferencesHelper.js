import { getQueryString } from '.';

export function buildRelatedDocumentsViewUrl({
  targetWindowId,
  windowId,
  documentId,
  tabId,
  rowIds,
  referenceId,
}) {
  const urlParams = getQueryString({
    referenceId: referenceId,
    refType: windowId,
    refDocumentId: documentId,
    refTabId: tabId,
    refRowIds: tabId && rowIds ? JSON.stringify(rowIds) : null,
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

  const referencesToAddByKey = referencesToAdd.reduce(
    (accum, reference) => ({
      ...accum,
      [extractUniqueKey(reference)]: reference,
    }),
    {}
  );

  // Merge referencesToAdd to existing references
  const existingReferencesMerged = existingReferences.map(
    (existingReference) => {
      const key = extractUniqueKey(existingReference);
      const referenceToAdd = referencesToAddByKey[key];
      delete referencesToAddByKey[key];

      return combineReferences(existingReference, referenceToAdd);
    }
  );

  return [
    ...existingReferencesMerged,
    ...Object.values(referencesToAddByKey), // remaining refences to add
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

// visible for testing
export function extractUniqueKey(reference) {
  return reference.targetCategory
    ? `${reference.targetWindowId}_${reference.targetCategory}`
    : `${reference.targetWindowId}`;
}
