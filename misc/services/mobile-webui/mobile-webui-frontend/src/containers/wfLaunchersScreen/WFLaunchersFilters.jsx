import React, { useEffect, useState } from 'react';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import PropTypes from 'prop-types';
import { trl } from '../../utils/translations';
import { countLaunchers, getFacets } from '../../api/launchers';
import GetDocumentNoDialog from './GetDocumentNoDialog';
import { useQuery } from '../../hooks/useQuery';

const useGroupsLoadingCounter = () => {
  const [loadingCount, setLoadingCount] = useState(0);

  return {
    groupsLoading: loadingCount > 0,
    setGroupsLoadingStart: () => setLoadingCount((count) => count + 1),
    setGroupsLoadingFinish: () => setLoadingCount((count) => count - 1),
  };
};

const useGroups = ({ applicationId, filterByDocumentNo, activeFacetIdsInitial }) => {
  const { groupsLoading, setGroupsLoadingStart, setGroupsLoadingFinish } = useGroupsLoadingCounter();
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    loadGroups({ filterByDocumentNo, activeFacetIds: activeFacetIdsInitial });
  }, [applicationId, filterByDocumentNo, activeFacetIdsInitial]);

  const loadGroups = ({ activeFacetIds }) => {
    setGroupsLoadingStart();
    getFacets({ applicationId, filterByDocumentNo, activeFacetIds })
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

const useResultsCount = ({ applicationId, filterByDocumentNo, groupsLoading, groups }) => {
  const { isPending, data } = useQuery({
    enabled: !groupsLoading,
    queryKey: [applicationId, filterByDocumentNo, groups],
    queryFn: () => {
      const facetIds = computeActiveFacetIdsFromGroups(groups);
      return countLaunchers({ applicationId, filterByDocumentNo, facetIds });
    },
  });

  return { resultsCountLoading: isPending, resultsCount: data };
};

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
const computeActiveFacetIdsFromGroups = (groups) => {
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
const computeActiveFacetsFromGroups = (groups) => {
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

const WFLaunchersFilters = ({
  applicationId,
  showFilterByDocumentNo,
  filterByDocumentNo: filterByDocumentNoInitial,
  activeFacetIds: activeFacetIdsInitial,
  onDone,
}) => {
  const [isFilterByDocumentNoModalDisplayed, setIsFilterByDocumentNoModalDisplayed] = useState(false);
  const [filterByDocumentNo, setFilterByDocumentNo] = useState(filterByDocumentNoInitial);
  const { groups, groupsLoading, toggleActiveFacet } = useGroups({
    applicationId,
    filterByDocumentNo,
    activeFacetIdsInitial,
  });
  const { resultsCountLoading, resultsCount } = useResultsCount({
    applicationId,
    filterByDocumentNo,
    groupsLoading,
    groups,
  });

  const onFilterByDocumentNoChanged = (filterByDocumentNoNew) => {
    setFilterByDocumentNo(filterByDocumentNoNew);
    onDone({ filterByDocumentNo: filterByDocumentNoNew, facets: [] });
  };
  const onFilterByDocumentNoCleared = () => {
    setFilterByDocumentNo('');
    setIsFilterByDocumentNoModalDisplayed(false);
  };

  const onFacetClicked = ({ facetId }) => {
    toggleActiveFacet({ facetId });
  };

  const onApplyFilters = () => {
    onDone({ filterByDocumentNo, facets: computeActiveFacetsFromGroups(groups) });
  };
  const onClearFilters = () => {
    onDone({ filterByDocumentNo: null, facets: [] });
  };

  return (
    <div className="filters">
      {showFilterByDocumentNo && (
        <>
          <ButtonWithIndicator
            id="filterByDocumentNo-button"
            caption={filterByDocumentNo ? filterByDocumentNo : trl('general.DocumentNo')}
            onClick={() => setIsFilterByDocumentNoModalDisplayed(true)}
          />
          <br />
        </>
      )}
      {isFilterByDocumentNoModalDisplayed && (
        <GetDocumentNoDialog
          documentNo={filterByDocumentNo}
          onOK={onFilterByDocumentNoChanged}
          onClear={() => onFilterByDocumentNoCleared()}
        />
      )}
      {groups &&
        groups.map((group) => (
          <FacetGroup key={group.groupId} caption={group.caption} facets={group.facets} onClick={onFacetClicked} />
        ))}
      {groupsLoading && (
        <div className="loading">
          <i className="loading-icon fas fa-solid fa-spinner fa-spin" />
        </div>
      )}
      <div className="bottom-buttons">
        {!groupsLoading && resultsCount > 0 && (
          <ButtonWithIndicator
            id="showResults"
            caption={trl('general.filter.showResults', { count: resultsCount })}
            typeFASIconName={resultsCountLoading ? 'fa-spinner fa-spin' : null}
            disabled={resultsCountLoading}
            onClick={onApplyFilters}
          />
        )}
        <ButtonWithIndicator id="clearFilters" captionKey="general.filter.clearFilters" onClick={onClearFilters} />
      </div>
    </div>
  );
};

WFLaunchersFilters.propTypes = {
  applicationId: PropTypes.string.isRequired,
  showFilterByDocumentNo: PropTypes.bool.isRequired,
  filterByDocumentNo: PropTypes.string,
  activeFacetIds: PropTypes.array,
  onDone: PropTypes.func.isRequired,
};

const FacetGroup = ({ caption, facets, onClick }) => {
  return (
    <div className="group">
      <div className="caption">{caption}</div>
      {facets &&
        facets.map((facet) => (
          <Facet
            key={facet.facetId}
            facetId={facet.facetId}
            groupId={facet.groupId}
            caption={facet.caption}
            active={!!facet.active}
            hitCount={facet.hitCount}
            onClick={onClick}
          />
        ))}
    </div>
  );
};
FacetGroup.propTypes = {
  caption: PropTypes.string.isRequired,
  facets: PropTypes.array.isRequired,
  onClick: PropTypes.func.isRequired,
};

const Facet = ({ facetId, groupId, caption, active, hitCount, onClick }) => {
  let captionEffective = caption;
  if (hitCount != null && hitCount >= 0) {
    captionEffective += ` (${hitCount})`;
  }

  return (
    <ButtonWithIndicator
      testId={facetId}
      additionalCssClass="facet-button"
      caption={captionEffective}
      typeFASIconName={active ? 'fa-check' : null}
      onClick={() => onClick({ facetId, groupId })}
    />
  );
};
Facet.propTypes = {
  facetId: PropTypes.string.isRequired,
  groupId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  active: PropTypes.bool.isRequired,
  hitCount: PropTypes.number,
  onClick: PropTypes.func.isRequired,
};

export default WFLaunchersFilters;
