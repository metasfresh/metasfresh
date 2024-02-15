import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import React, { useMemo } from 'react';
import PropTypes from 'prop-types';

const computeCaption = (facets) => {
  const caption = facets.map((facet) => facet.caption).join(', ');
  return caption ? caption : 'Filter...';
};

const WFLaunchersFilterButton = ({ facets, onClick }) => {
  const caption = useMemo(() => computeCaption(facets), facets);
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
  facets: PropTypes.array.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default WFLaunchersFilterButton;
