import React, { Component } from 'react';
import {connect} from 'react-redux';
import Attachments from './Attachments';
import Referenced from './Referenced';
import DocumentList from '../app/DocumentList';
import onClickOutside from 'react-onclickoutside';

class SideList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            tab: 0
        }
    }

    handleClickOutside = () => {
        const {closeSideList} = this.props;
        closeSideList();
    }

    changeTab = (index) => {
        this.setState({
            tab: index
        })
    }

    renderBody = () => {
        const {
            open, windowType, closeOverlays, closeSideList, isSideListShow,
            docId, pagination, sorting, viewId
        } = this.props;

        const {
            tab
        } = this.state;

        switch (tab) {
            case 0:
                return <DocumentList
                    type="list"
                    readonly={true}
                    defaultViewId={
                        (viewId && viewId.windowType === windowType) ?
                            viewId.id : null
                    }
                    defaultSort={
                        (sorting && sorting.windowType === windowType) ?
                            sorting.sort : null
                    }
                    defaultPage={
                        (pagination && pagination.windowType === windowType) ?
                            pagination.page : null
                    }
                    {...{open, windowType, closeOverlays, closeSideList,
                    isSideListShow}}
                />
            case 1:
                return <Referenced
                    {...{windowType, docId}}
                />
            case 2:
                return <Attachments
                    {...{windowType, docId}}
                />
        }
    }

    render() {
        const {
            tab
        } = this.state;

        const tabs = [
            'meta-icon-list', 'meta-icon-share', 'meta-icon-attachments'
        ]

        return (
            <div
                ref={(c) => this.panel = c}
                className="order-list-panel overlay-shadow order-list-panel-open"
            >
                <div className="order-list-panel-body">
                    <div className="order-list-nav">
                        {tabs.map((item, index) => <div
                            key={index}
                            className={'order-list-btn ' +
                                (index === tab ? 'active ' : '')
                            }
                            onClick={() => this.changeTab(index)}
                        >
                            <i className={item} />
                        </div>)}
                    </div>
                    {this.renderBody()}
                </div>
            </div>
        )
    }
}

function mapStateToProps(state) {
    const { listHandler } = state;
    const {
        sorting,
        pagination,
        viewId
    } = listHandler || {
        sorting: {},
        pagination: {},
        viewId: ''
    }

    return {
        sorting, pagination, viewId
    }
}

SideList = connect(mapStateToProps)(onClickOutside(SideList))

export default SideList;
