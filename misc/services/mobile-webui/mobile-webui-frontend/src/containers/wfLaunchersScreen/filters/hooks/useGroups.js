import { useEffect, useState } from 'react';
import { getFacets } from '../../../../api/launchers';
import { computeActiveFacetIdsFromGroups } from '../utils';

export const useGroups = ({ applicationId, activeFacetIdsInitial, filters }) => {
  const { groupsLoading, setGroupsLoadingStart, setGroupsLoadingFinish } = useGroupsLoadingCounter();
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    loadGroups({ activeFacetIds: activeFacetIdsInitial });
  }, [applicationId, activeFacetIdsInitial.join('|'), filters]);

  const loadGroups = ({ activeFacetIds }) => {
    setGroupsLoadingStart();
    getFacets({ applicationId, activeFacetIds, filters })
      .then((groups) => setGroups(groups))
      .finally(() => setGroupsLoadingFinish());
  };

  const toggleActiveFacet = ({ facetId }) => {
    const groupsNew = toggleActiveFacetOfGroups({ groups, facetId });
    setGroups(groupsNew);

    const activeFacetIds = computeActiveFacetIdsFromGroups(groupsNew);
    loadGroups({ activeFacetIds });
  };

  return { groups, groupsLoading, toggleActiveFacet };
};

//
//
//
//
//

const useGroupsLoadingCounter = () => {
  const [loadingCount, setLoadingCount] = useState(0);

  return {
    groupsLoading: loadingCount > 0,
    setGroupsLoadingStart: () => setLoadingCount((count) => count + 1),
    setGroupsLoadingFinish: () => setLoadingCount((count) => count - 1),
  };
};

//
//
//
//
//

const toggleActiveFacetOfGroups = ({ groups, facetId }) => {
  const groupsNew = [];
  let groupChanged = false;

  for (const group of groups) {
    const facetsNew = [];
    let facetChanged = false;

    for (const facet of group.facets) {
      if (facet.facetId === facetId) {
        facetsNew.push({ ...facet, active: !facet.active });
        facetChanged = true;
      } else {
        facetsNew.push(facet);
      }
    }

    if (facetChanged) {
      groupsNew.push({ ...group, facets: facetsNew });
      groupChanged = true;
    } else {
      groupsNew.push(group);
    }
  }

  return groupChanged ? groupsNew : groups;
};

//
//
//
//
//
