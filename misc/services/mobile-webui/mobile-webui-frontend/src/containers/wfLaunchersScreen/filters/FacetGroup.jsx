import React from 'react';
import PropTypes from 'prop-types';
import { Facet } from './Facet';

export const FacetGroup = ({ groupId, caption, facets, onClick }) => {
  return (
    <div className="group" data-testid={groupId}>
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
  groupId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  facets: PropTypes.array.isRequired,
  onClick: PropTypes.func.isRequired,
};
