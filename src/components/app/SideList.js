import React, { Component } from 'react';
import DocumentList from './DocumentList';

class SideList extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {open} = this.props;
        return (
            <div ref={(c)=>this.panel=c} className={"order-list-panel overlay-shadow " + (open ? "order-list-panel-open":"")}>
                <div className="order-list-panel-body">
                    <DocumentList windowType={143} />
                </div>
            </div>

        )
    }
}

export default SideList
