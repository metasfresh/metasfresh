import React from 'react';
import PropTypes from 'prop-types';
import SimpleList from '../../../components/widget/List/SimpleList';

const toKeyCaption = (resource) => {
  if (!resource) return null;

  return {
    key: resource.id,
    caption: resource.title,
  };
};

const ResourcesDropDown = ({ resources, selectedResource, onSelect }) => {
  const resourcesById = resources
    ? resources.reduce((accum, resource) => {
        accum[resource.id] = resource;
        return accum;
      }, {})
    : {};

  const resourceKeyCaptionsArray =
    resources && resources.length > 0 ? resources.map(toKeyCaption) : [];

  const handleOnSelect = (resourceKeyCaptionEntry) => {
    const resource = resourceKeyCaptionEntry
      ? resourcesById[resourceKeyCaptionEntry.key]
      : null;

    onSelect(resource);
  };

  return (
    <SimpleList
      list={resourceKeyCaptionsArray}
      selected={toKeyCaption(selectedResource)}
      onSelect={handleOnSelect}
    />
  );
};

ResourcesDropDown.propTypes = {
  resources: PropTypes.array.isRequired,
  selectedResource: PropTypes.object,
  onSelect: PropTypes.func.isRequired,
};

export default ResourcesDropDown;
