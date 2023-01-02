import React, { Component } from 'react';
import PropTypes from 'prop-types';
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

/**
 * @typedef {object} Props Component props
 * @prop {*} devices
 * @prop {*} handleChange
 * @prop {*} tabIndex
 * @todo Check props. Which proptype? Required or optional?
 */
DevicesWidget.propTypes = {
  devices: PropTypes.any,
  handleChange: PropTypes.any,
  tabIndex: PropTypes.any,
};

export default DevicesWidget;
