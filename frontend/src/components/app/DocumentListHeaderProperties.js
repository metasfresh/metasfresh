import React from 'react';
import PropTypes from 'prop-types';
import { renderHeaderPropertiesGroups } from '../../utils/documentListHelper';

export const DocumentListHeaderProperties = ({ headerProperties }) => {
  const propsGroups = headerProperties?.groups;
  if (!propsGroups || propsGroups.length <= 0) {
    return null;
  }

  return (
    <div className="panel panel-primary">
      <div className="panel-groups-header">
        <div className="optional">
          {renderHeaderPropertiesGroups(propsGroups)}
        </div>
      </div>
    </div>
  );
};

DocumentListHeaderProperties.propTypes = {
  headerProperties: PropTypes.object,
};
