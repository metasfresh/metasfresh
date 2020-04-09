import React from 'react';
import PropTypes from 'prop-types';

const MapMarker = ({ text }) => (
  <div className="mapMarker">
    <div className="markerTooltip">{text}</div>
  </div>
);

MapMarker.propTypes = {
  text: PropTypes.string,
};

export default MapMarker;
