import React, { Component } from 'react';

import Device from './Device';

/**
 * @file Class based component.
 * @module DevicesWidget
 * @extends Component
 */
class DevicesWidget extends Component {
  constructor(props) {
    super(props);
  }

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { devices, handleChange, tabIndex } = this.props;
    const isMore = devices.length > 1;

    return (
      <div className="form-group-flex-item">
        {devices &&
          devices.map((item, index) => (
            <Device
              device={item}
              key={index}
              index={index}
              handleChange={handleChange}
              isMore={isMore}
              tabIndex={tabIndex}
            />
          ))}
      </div>
    );
  }
}

export default DevicesWidget;
