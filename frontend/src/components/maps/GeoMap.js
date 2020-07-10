import React, { Component } from 'react';
import cx from 'classnames';
import GoogleMapReact from 'google-map-react';
import OSMap from 'pigeon-maps';
import Marker from 'pigeon-marker';
import PropTypes from 'prop-types';

import MapMarker from './MapMarker';

class Map extends Component {
  static defaultProps = {
    center: {
      lat: 52.31,
      lng: 13.24,
    },
    zoom: 10,
  };

  handleApiLoaded = (map, maps) => {
    const { data } = this.props;
    const bounds = new maps.LatLngBounds();

    for (let i = 0; i < data.length; i += 1) {
      const marker = new maps.Marker(
        {
          position: {
            lat: data[i].latitude,
            lng: data[i].longitude,
          },
        },
        map
      );

      bounds.extend(marker.getPosition());
    }

    map.fitBounds(bounds);

    map.setCenter(bounds.getCenter());

    map.setZoom(map.getZoom() - 1);

    if (map.getZoom() > 15) {
      map.setZoom(15);
    }
  };

  render() {
    const { data, center, zoom, toggleState, mapConfig } = this.props;

    if (data && mapConfig) {
      return (
        <div
          className={cx('mapComponent', {
            'col-12': toggleState === 'map',
            'col-6': toggleState === 'all',
            'd-none': toggleState === 'grid' || toggleState == null,
          })}
        >
          <div style={{ height: '100%', width: '100%' }}>
            {mapConfig.provider === 'OpenStreetMap' ? (
              <OSMap
                defaultCenter={[center.lat, center.lng]}
                defaultZoom={zoom}
                limitBounds="center"
              >
                {data.map((location) => (
                  <Marker
                    key={location.rowId}
                    anchor={[location.latitude, location.longitude]}
                    payload={1}
                  />
                ))}
              </OSMap>
            ) : (
              <GoogleMapReact
                bootstrapURLKeys={{
                  key: mapConfig.googleMapsApiKey,
                }}
                defaultCenter={center}
                defaultZoom={zoom}
                yesIWantToUseGoogleMapApiInternals
                onGoogleApiLoaded={({ map, maps }) =>
                  this.handleApiLoaded(map, maps)
                }
              >
                {data.map((location) => (
                  <MapMarker
                    lat={location.latitude}
                    lng={location.longitude}
                    key={location.rowId}
                    text={location.name}
                  />
                ))}
              </GoogleMapReact>
            )}
          </div>
        </div>
      );
    }

    return null;
  }
}

Map.propTypes = {
  data: PropTypes.object,
  mapConfig: PropTypes.object,
  center: PropTypes.any,
  toggleState: PropTypes.any,
  zoom: PropTypes.any,
};

export default Map;
