import React, { Component } from 'react';
import DocumentList from '../app/DocumentList';

class SideList extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {open, windowType} = this.props;
        return (
            <div
                ref={(c) => this.panel = c}
                className={
                    "order-list-panel overlay-shadow " +
                    (open ? "order-list-panel-open":"")
                }
            >
                <div className="order-list-panel-body">
                    <DocumentList windowType={windowType} type="list" />
                </div>
            </div>
        )
    }
}

export default SideList
