import React, { Component } from 'react';

import Device from './Device';

class DevicesWidget extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {devices, handleChange} = this.props;
        const isMore = devices.length > 1;

        return (
            <div className="form-group-flex-item">
                {devices && devices.map((item, index) =>
                    <Device
                        device={item}
                        key={index}
                        index={index}
                        handleChange={handleChange}
                        isMore={isMore}
                    />
                )}
            </div>
        )
    }
}

export default DevicesWidget;
