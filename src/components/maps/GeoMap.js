import React, { Component } from 'react';
// import React, { Fragment, FunctionComponent } from 'react';
import cx from 'classnames';

import GoogleMapReact from 'google-map-react';

import MapMarker from './MapMarker';

class Map extends Component {
  static defaultProps = {
    center: {
      lat: 52.31,
      lng: 13.24,
    },
    zoom: 11,
  };

  render() {
    const { data, toggleState } = this.props;

    if (data) {
      return (
        <div
          className={cx('mapComponent', {
            'col-12': toggleState === 2,
            'col-6': toggleState === 1,
            'd-none': toggleState === 0 || toggleState == null,
          })}
        >
          <div style={{ height: '100vh', width: '100%' }}>
            <GoogleMapReact
              bootstrapURLKeys={{
                key: 'AIzaSyDeo1sbSGwxTkukztT2EkqFafF3Hp8SmsY',
              }}
              defaultCenter={this.props.center}
              defaultZoom={this.props.zoom}
            >
              {data.map(location => (
                <MapMarker
                  lat={location.latitude}
                  lng={location.longitude}
                  key={location.rowId}
                />
              ))}
            </GoogleMapReact>
          </div>
        </div>
      );
    }

    return null;
  }
}

export default Map;
