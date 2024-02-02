import React, { useEffect, useMemo, useState } from 'react';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import PropTypes from 'prop-types';
import { countLaunchers, getFacets } from '../../api/launchers';
import { trl } from '../../utils/translations';

const toggleFacet = (groups, facetId) => {
  return changeFacets(groups, (facet) => (facet.facetId === facetId ? { ...facet, active: !facet.active } : facet));
};

const changeFacets = (groups, facetMapper) => {
  const result = [];
  let groupChanges = false;
  for (const group of groups) {
    let newFacets = [];
    let facetChanged = false;
    for (const facet of group.facets) {
      const newFacet = facetMapper(facet);
      newFacets.push(newFacet);
      if (facet !== newFacet) {
        facetChanged = true;
      }
    }

    const newGroup = facetChanged ? { ...group, facets: newFacets } : group;
    result.push(newGroup);
    if (group !== newGroup) {
      groupChanges = true;
    }
  }

  return groupChanges ? result : groups;
};

const activateFacets = (groups, activeFacets) => {
  const activeFacetIds = activeFacets ? activeFacets.map((facet) => facet.facetId) : [];

  return changeFacets(groups, (facet) => {
    return !facet.active && activeFacetIds.includes(facet.facetId) ? { ...facet, active: true } : facet;
  });
};

const getActiveFacets = (groups) => {
  const result = [];
  groups.forEach((group) => {
    group.facets.forEach((facet) => {
      if (facet.active) {
        result.push(facet);
      }
    });
  });

  return result;
};

const WFLaunchersFilters = ({ applicationId, facets: facetsInitial, onDone }) => {
  const [groups, setGroups] = useState([]);
  const [resultsCount, setResultsCount] = useState(-1);
  const [resultsCountLoading, setResultsCountLoading] = useState(false);
  const facets = useMemo(() => getActiveFacets(groups), [groups]);

  //
  // Fetch available facets
  useEffect(() => {
    getFacets(applicationId).then((response) => {
      setGroups(activateFacets(response.groups, facetsInitial));
    });
  }, [facetsInitial]);

  //
  // Compute items count for currently active facets
  useEffect(() => {
    setResultsCountLoading(true);
    countLaunchers({ applicationId, facets })
      .then((count) => {
        setResultsCount(count);
      })
      .finally(() => {
        setResultsCountLoading(false);
      });
  }, [facets]);

  const onFacetClicked = ({ facetId }) => {
    setGroups(toggleFacet(groups, facetId));
  };

  const onApplyFilters = () => {
    onDone(getActiveFacets(groups));
  };
  const onClearFilters = () => {
    onDone([]);
  };

  return (
    <div className="filters">
      {groups &&
        groups.map((group) => (
          <FacetGroup key={group.groupId} caption={group.caption} facets={group.facets} onClick={onFacetClicked} />
        ))}
      <div className="bottom-buttons">
        {resultsCount > 0 && (
          <ButtonWithIndicator
            caption={trl('general.filter.showResults', { count: resultsCount })}
            typeFASIconName={resultsCountLoading ? 'fa-spinner fa-spin' : null}
            disabled={resultsCountLoading}
            onClick={onApplyFilters}
          />
        )}
        <ButtonWithIndicator caption={trl('general.filter.clearFilters')} onClick={onClearFilters} />
      </div>
    </div>
  );
};

WFLaunchersFilters.propTypes = {
  applicationId: PropTypes.string.isRequired,
  facets: PropTypes.array.isRequired,
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
            caption={facet.caption}
            active={!!facet.active}
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

const Facet = ({ facetId, caption, active, onClick }) => {
  return (
    <ButtonWithIndicator
      caption={caption}
      typeFASIconName={active ? 'fa-check' : null}
      onClick={() => onClick({ facetId })}
    />
  );
};
Facet.propTypes = {
  facetId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  active: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default WFLaunchersFilters;
