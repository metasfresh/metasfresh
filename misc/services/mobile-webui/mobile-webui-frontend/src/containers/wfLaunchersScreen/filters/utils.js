export const computeActiveFacetIdsFromGroups = (groups) => {
  const result = [];
  for (const group of groups) {
    for (const facet of group.facets) {
      if (facet.active) {
        result.push(facet.facetId);
      }
    }
  }
  return result;
};

export const computeActiveFacetsFromGroups = (groups) => {
  const result = [];
  for (const group of groups) {
    for (const facet of group.facets) {
      if (facet.active) {
        result.push(facet);
      }
    }
  }
  return result;
};
