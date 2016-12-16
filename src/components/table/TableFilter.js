import React, { Component } from 'react';

class TableFilter extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {
            openModal, fullScreen
        } = this.props;

        return (
            <div className="form-flex-align">
                <button
                    className="btn btn-meta-outline-secondary btn-distance btn-sm"
                    onClick={openModal}
                >
                    Add new
                </button>
                <button
                    className="btn-icon btn-meta-outline-secondary pointer"
                    onClick={fullScreen}
                >
                    <i className="meta-icon-fullscreen"/>
                </button>
            </div>
        )
    }
}

export default TableFilter
