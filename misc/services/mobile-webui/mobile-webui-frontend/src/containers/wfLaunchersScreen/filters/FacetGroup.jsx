import React from 'react';
import PropTypes from 'prop-types';
import { Facet } from './Facet';

export const FacetGroup = ({ caption, facets, onClick }) => {
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
