import React, { Component } from 'react';
import {connect} from 'react-redux';
import DocumentList from '../app/DocumentList';
import onClickOutside from 'react-onclickoutside';

class SideList extends Component {
    constructor(props) {
        super(props);
    }

    handleClickOutside = () => {
        const {closeSideList} = this.props;
        closeSideList();
    }

    render() {
        const {open, windowType, closeOverlays} = this.props;

        return (
            <div
                ref={(c) => this.panel = c}
                className={
                    "order-list-panel overlay-shadow order-list-panel-open"
                }
            >
                <div className="order-list-panel-body">
                    <DocumentList
                        windowType={windowType}
                        type="list"
                        open={open}
                        closeOverlays={closeOverlays}
                    />
                </div>
            </div>
        )
    }
}

SideList = connect()(onClickOutside(SideList))

export default SideList;
