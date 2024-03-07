import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import React, { useMemo } from 'react';
import PropTypes from 'prop-types';

const computeCaption = ({ filterByDocumentNo, facets }) => {
  let parts = [];
  if (filterByDocumentNo) {
    parts.push(filterByDocumentNo);
  }

  if (facets) {
    facets.forEach((facet) => parts.push(facet.caption));
  }

  const caption = parts.join(', ');
  return caption ? caption : 'Filter...';
};

const WFLaunchersFilterButton = ({ filterByDocumentNo, facets, onClick }) => {
  const caption = useMemo(() => computeCaption({ filterByDocumentNo, facets }), [filterByDocumentNo, facets]);
  return (
    <ButtonWithIndicator
      additionalCssClass="filter-button"
      caption={caption}
      typeFASIconName="fa-filter"
      disabled={false}
      onClick={onClick}
    />
  );
};

WFLaunchersFilterButton.propTypes = {
  filterByDocumentNo: PropTypes.string,
  facets: PropTypes.array.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default WFLaunchersFilterButton;
