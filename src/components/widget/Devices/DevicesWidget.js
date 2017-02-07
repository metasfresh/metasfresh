import React, { Component } from 'react';

import Device from './Device';

class DevicesWidget extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {devices, handleChange} = this.props;

        return (
            <div>
                {devices && devices.map((item, index) =>
                    <Device
                        device={item}
                        key={index}
                        handleChange={handleChange}
                    />
                )}
            </div>
        )
    }
}

export default DevicesWidget;
