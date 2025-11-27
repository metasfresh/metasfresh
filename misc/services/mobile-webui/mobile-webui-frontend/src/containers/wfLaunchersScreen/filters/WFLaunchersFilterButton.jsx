import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { trl } from '../../../utils/translations';

const WFLaunchersFilterButton = ({ filters, facets, onClick }) => {
  return (
    <ButtonWithIndicator
      id="filter-button"
      additionalCssClass="filter-button"
      caption={computeCaption({ filters, facets })}
      typeFASIconName="fa-filter"
      disabled={false}
      onClick={onClick}
    />
  );
};

WFLaunchersFilterButton.propTypes = {
  filters: PropTypes.object,
  facets: PropTypes.array.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default WFLaunchersFilterButton;

//
//
//
//
//

const computeCaption = ({ filters, facets }) => {
  let parts = [];
  if (filters?.filterByDocumentNo) {
    parts.push(filters?.filterByDocumentNo);
  }
  if (filters?.filterByQtyAvailableAtPickFromLocator) {
    parts.push(trl('general.filter.filterByQtyAvailableAtPickFromLocator'));
  }

  if (facets) {
    facets.forEach((facet) => parts.push(facet.caption));
  }

  const caption = parts.join(', ');
  return caption ? caption : 'Filter...';
};
