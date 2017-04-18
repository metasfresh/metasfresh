import React, { Component } from 'react';
import {connect} from 'react-redux';

import Attachments from './Attachments';
import Referenced from './Referenced';
import DocumentList from '../app/DocumentList';
import onClickOutside from 'react-onclickoutside';
import Tooltips from '../tooltips/Tooltips';
import keymap from '../../keymap.js';

class SideList extends Component {
    constructor(props) {
        super(props);

        const {defaultTab} = this.props;

        this.state = {
            tab: defaultTab ? defaultTab : 0,
            tooltipOpen: false
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

    toggleTooltip = (id) => {
        this.setState({
            tooltipOpen: id
        })
    }

    render() {
        const {
            tab, tooltipOpen
        } = this.state;

        const tabs = [
            {icon: 'meta-icon-list', title: 'Document list'},
            {icon: 'meta-icon-share', title: 'Referenced documents'},
            {icon: 'meta-icon-attachments', title: 'Attachments'}
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
                            className={'order-list-btn tooltip-parent ' +
                                (index === tab ? 'active ' : '')
                            }
                            onClick={() => this.changeTab(index)}
                            onMouseEnter={() =>
                                this.toggleTooltip(index)
                            }
                            onMouseLeave={() =>
                                this.toggleTooltip()
                            }
                            tabIndex={0}
                        >
                            <i className={item.icon} />
                            { tooltipOpen === index &&
                                <Tooltips
                                    name={
                                        keymap
                                            .GLOBAL_CONTEXT[
                                                'OPEN_SIDEBAR_MENU_' + index
                                            ]
                                    }
                                    action={item.title}
                                    type={''}
                                />
                            }
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
