import React from 'react';
import PropTypes from 'prop-types';

import './CalendarFilters.scss';

const CalendarFilters = ({ resolvedQuery }) => {
  const items = convertResolvedQueryToItemData(resolvedQuery);

  return (
    <>
      {items.map((itemData, index) => (
        <CalendarFilterItem key={index} caption={itemData.caption} />
      ))}
    </>
  );
};

CalendarFilters.propTypes = {
  resolvedQuery: PropTypes.object.isRequired,
};

const convertResolvedQueryToItemData = (resolvedQuery) => {
  if (!resolvedQuery) {
    return [];
  }

  const result = [];

  if (resolvedQuery.onlyResources && resolvedQuery.onlyResources.length > 0) {
    resolvedQuery.onlyResources.forEach((resource) => {
      result.push(resource);
    });
  }

  if (resolvedQuery.onlyProject) {
    result.push(resolvedQuery.onlyProject);
  }

  if (resolvedQuery.onlyCustomer) {
    result.push(resolvedQuery.onlyCustomer);
  }

  if (resolvedQuery.onlyResponsible) {
    result.push(resolvedQuery.onlyResponsible);
  }

  return result;
};

const CalendarFilterItem = ({ caption }) => {
  return <div className="calendar-filter-item">{caption}</div>;
};

CalendarFilterItem.propTypes = {
  caption: PropTypes.string.isRequired,
};

export default CalendarFilters;
