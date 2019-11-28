import React from 'react';

const MapMarker = ({ text }) => (
  <div className="mapMarker">
    <div className="markerTooltip">{text}</div>
  </div>
);

export default MapMarker;
