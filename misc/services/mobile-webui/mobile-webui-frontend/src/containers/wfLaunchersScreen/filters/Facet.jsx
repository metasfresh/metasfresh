import React from 'react';
import PropTypes from 'prop-types';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

export const Facet = ({ facetId, groupId, caption, active, hitCount, onClick }) => {
  let captionEffective = caption;
  if (hitCount != null && hitCount >= 0) {
    captionEffective += ` (${hitCount})`;
  }

  return (
    <ButtonWithIndicator
      testId={facetId}
      data-hitcount={hitCount}
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
