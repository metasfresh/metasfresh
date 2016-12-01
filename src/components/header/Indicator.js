import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import '../../assets/css/styles.css';

class Indicator extends Component {
    constructor(props){
        super(props);
    }
    renderIndicator = (state) => {
        switch(state){
            case "saved":
                return "indicator-success";
                break;
            case "pending":
                return "indicator-pending";
                break;
            case "error":
                return "indicator-error";
                break;
        }
    }
    render() {
        const {indicator} = this.props;
        return (
            <div>
                <div className={"indicator-bar indicator-" + indicator} />
            </div>
        )
    }
}

export default Indicator
