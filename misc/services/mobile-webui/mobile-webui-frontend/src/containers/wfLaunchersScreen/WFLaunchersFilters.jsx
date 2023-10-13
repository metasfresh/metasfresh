import React, { useEffect, useState } from 'react';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import PropTypes from 'prop-types';
import { countLaunchers, getFacets } from '../../api/launchers';

const toggleFacet = ({ groups, groupId, facetId }) => {
  return groups.map((group) => {
    if (group.groupId === groupId) {
      return {
        ...group,
        facets: group.facets.map((facet) => {
          if (facet.facetId === facetId) {
            return {
              ...facet,
              active: !facet.active,
            };
          } else {
            return facet;
          }
        }),
      };
    } else {
      return group;
    }
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

const WFLaunchersFilters = ({ applicationId, onDone }) => {
  const [groups, setGroups] = useState([]);
  const [resultsCount, setResultsCount] = useState(-1);

  useEffect(() => {
    getFacets(applicationId).then((response) => {
      console.log('Got response', { response });
      setGroups(response.groups);
    });
  }, []);

  useEffect(() => {
    const facets = getActiveFacets(groups);
    countLaunchers({ applicationId, facets }).then((count) => {
      setResultsCount(count);
    });
  }, [groups]);

  const onFacetClicked = ({ groupId, facetId }) => {
    console.log('facet clicked', { groupId, facetId });
    setGroups(toggleFacet({ groups, groupId, facetId }));
  };

  const onApplyFilters = () => {
    console.log('Apply filters');
    onDone?.();
  };
  const onClearFilters = () => {
    console.log('Clear filters');
    onDone?.();
  };

  return (
    <div className="filters">
      {groups &&
        groups.map((group) => (
          <FacetGroup
            key={group.groupId}
            groupId={group.groupId}
            caption={group.caption}
            facets={group.facets}
            onClick={onFacetClicked}
          />
        ))}
      <div className="bottom-buttons">
        <ButtonWithIndicator caption={`Show ${resultsCount} results`} onClick={onApplyFilters} />
        <ButtonWithIndicator caption="Clear filters" onClick={onClearFilters} />
      </div>
    </div>
  );
};

WFLaunchersFilters.propTypes = {
  applicationId: PropTypes.string.isRequired,
  onDone: PropTypes.func,
};

const FacetGroup = ({ groupId, caption, facets, onClick }) => {
  return (
    <div className="group">
      <div className="caption">{caption}</div>
      {facets &&
        facets.map((facet) => (
          <Facet
            key={facet.facetId}
            facetId={facet.facetId}
            groupId={groupId}
            caption={facet.caption}
            active={!!facet.active}
            onClick={onClick}
          />
        ))}
    </div>
  );
};
FacetGroup.propTypes = {
  groupId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  facets: PropTypes.array.isRequired,
  onClick: PropTypes.func.isRequired,
};

const Facet = ({ facetId, groupId, caption, active, onClick }) => {
  return (
    <ButtonWithIndicator
      caption={caption}
      typeFASIconName={active ? 'fa-check' : null}
      onClick={() => onClick({ groupId, facetId })}
    />
  );
};
Facet.propTypes = {
  facetId: PropTypes.string.isRequired,
  groupId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  active: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default WFLaunchersFilters;
