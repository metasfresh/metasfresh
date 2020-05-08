export function mergePartialGroupToGroupsArray(groups, partialGroup) {
  if (!partialGroup) {
    return groups;
  }

  let result = [];
  let partialGroupMerged = false;

  for (const group of groups) {
    if (!partialGroupMerged && group.caption === partialGroup.caption) {
      const changedGroup = mergeReferencesToGroup(
        group,
        partialGroup.references
      );

      result.push(changedGroup);
      partialGroupMerged = true;
    } else {
      result.push(group);
    }
  }

  if (!partialGroupMerged) {
    result.push(partialGroup);
    partialGroupMerged = true;
  }

  //
  // Sort groups by caption alphabetically, keep miscGroup last.
  result = result.sort((group1, group2) => {
    if (group1.miscGroup == group2.miscGroup) {
      return group1.caption.localeCompare(group2.caption);
    } else if (group1.miscGroup) {
      return +1; // keep misc group last
    } else {
      return -1;
    }
  });

  return result;
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

  const referencesToAddById = {};
  referencesToAdd.forEach((reference) => {
    referencesToAddById[reference.id] = reference;
  });

  let references = [];

  for (const existingReference of existingReferences) {
    const referenceToAdd = referencesToAddById[existingReference.id];
    delete referencesToAddById[existingReference.id];

    if (
      referenceToAdd &&
      referenceToAdd.priority < existingReference.priority
    ) {
      references.push(referenceToAdd);
    } else {
      references.push(existingReference);
    }
  }

  Object.values(referencesToAddById).forEach((referenceToAdd) =>
    references.push(referenceToAdd)
  );

  return references.sort((reference1, reference2) => {
    return reference1.caption.localeCompare(reference2.caption);
  });
}
