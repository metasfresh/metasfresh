import React, { Component } from 'react';

class Indicator extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {value, caption} = this.props;
        return (
            <div className="indicator">
                <div className="indicator-value">{value}</div>
                <div className="indicator-kpi-caption">{caption}</div>
            </div>
        );
    }
}

export default Indicator;
