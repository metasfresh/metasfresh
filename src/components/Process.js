import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

export class Process extends Component {
    constructor(props){
        super(props);
    }

    renderElements = () => {

    }

    render() {
        const {breadcrumb} = this.props;
        return (
            <div key="window" className="window-wrapper">
                {this.renderElements()}
            </div>
        );
    }
}
Process.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Process = connect()(Process);

export default Process
