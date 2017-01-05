import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import update from 'react-addons-update';
import {push} from 'react-router-redux';

import '../../assets/css/header.css';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import Subheader from './SubHeader';
import Breadcrumb from './Breadcrumb';
import MasterWidget from '../widget/MasterWidget';
import SideList from './SideList';
import Indicator from './Indicator';
import Inbox from '../inbox/Inbox';
import Tooltips from '../tooltips/Tooltips';
import Prompt from '../app/Prompt';

import {
    indicatorState
} from '../../actions/WindowActions';

import {
    getRootBreadcrumb
} from '../../actions/MenuActions';

import {
    printDoc,
    openModal,
    deleteData
} from '../../actions/WindowActions';


import keymap from '../../keymap.js';
import GlobalShortcuts from '../shortcuts/GlobalContextShortcuts';
import { ShortcutManager } from 'react-shortcuts';
const shortcutManager = new ShortcutManager(keymap);

class Header extends Component {
    constructor(props){
        super(props);

        this.state = {
            isSubheaderShow: false,
            isSideListShow: false,
            isMenuOverlayShow: false,
            menuOverlay: null,
            scrolled: false,
            isInboxOpen: false,
            tooltip: {
                open: ''
            },
            prompt: {
                open: false
            }
        }
    }

     getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    handleSubheaderOpen = () => {
        this.setState(
            Object.assign({}, this.state, {
                isSubheaderShow: !this.state.isSubheaderShow
            })
        );
    }

    handleInboxOpen = (state, callback) => {
        this.setState(
            Object.assign({}, this.state, {
                isInboxOpen: !!state
            }), callback
        );
    }

    handleSideListToggle = () => {
        const {isSideListShow} = this.state;
        this.toggleScrollScope(!isSideListShow);

        this.setState(
            Object.assign({}, this.state, {
                isSideListShow: !isSideListShow
            })
        );
    }

    handleCloseSideList = (callback) => {
        this.setState(
            Object.assign({}, this.state, {
                isSideListShow: false
            }), callback);
        this.toggleScrollScope(false);
    }

    handleBackdropClick = (callback) => {
        this.setState(Object.assign({}, this.state, {isSubheaderShow: false}), callback);
    }

    handleMenuOverlay = (e, nodeId) => {
        const {isSubheaderShow, isSideListShow} = this.state;
        e && e.preventDefault();

        let toggleBreadcrumb = () => {
            this.setState(Object.assign({}, this.state, {
                menuOverlay: nodeId
            }), () => {
                if(nodeId !== "") {
                    this.setState(Object.assign({}, this.state, {
                        isMenuOverlayShow: true
                    }));
                } else {
                    this.setState(Object.assign({}, this.state, {
                        isMenuOverlayShow: false
                    }));
                }

            });
        }
        if(isSubheaderShow){
            this.handleBackdropClick(toggleBreadcrumb);
        }else if(isSideListShow){
            this.handleCloseSideList(toggleBreadcrumb);
        }else{
            toggleBreadcrumb();
        }
    }

    componentDidMount() {
        const {dispatch} = this.props;
        dispatch(getRootBreadcrumb());
        document.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        document.removeEventListener('scroll', this.handleScroll);
    }

    handleScroll = (event) => {
        let scrollTop = event.srcElement.body.scrollTop;

        if(scrollTop > 0) {
            this.setState(Object.assign({}, this.state, {
                scrolled: true
            }))
        } else {
            this.setState(Object.assign({}, this.state, {
                scrolled: false
            }))
        }
    }

    handleDashboardLink = () => {
        const {dispatch} = this.props;
        dispatch(push("/"));
    }

    toggleScrollScope = (open) => {
        if(!open){
            document.body.style.overflow = "auto";
        }else{
            document.body.style.overflow = "hidden";
        }
    }

    toggleTooltip = (tooltip) => {
        this.setState(
            Object.assign({}, this.state, {
                tooltip: Object.assign({}, this.state, {
                    open: tooltip
                })
            })
        );
    }

    openModal = (windowType, type, caption, isAdvanced) => {
        const {dispatch} = this.props;
        dispatch(openModal(caption, windowType, type, null, null, isAdvanced));
        this.handleBackdropClick(false);
    }

    handlePrint = (windowType, docId, docNo) => {
        const {dispatch} = this.props;
        const url = config.API_URL +
            '/window/' + windowType +
            '/' + docId +
            '/print/' + windowType + '_' + (docNo ? docNo : docId) + '.pdf';
        window.open(url, "_blank");
        this.handleBackdropClick(false);
    }

    handleDelete = () => {
        const {dispatch} = this.props;

        this.setState(Object.assign({}, this.state, {
            prompt: Object.assign({}, this.state.prompt, {
                open: true
            })
        }));
    }

    handleClone = (windowType, docId) => {
        //TODO when API ready
    }

    handlePromptCancelClick = () => {
        this.handleBackdropClick(false);
        this.setState(Object.assign({}, this.state, {
            prompt: Object.assign({}, this.state.prompt, {
                open: false
            })
        }));
    }

    handlePromptSubmitClick = (windowType, docId) => {
        const {dispatch} = this.props;

        this.setState(Object.assign({}, this.state, {
            prompt: Object.assign({}, this.state.prompt, {
                open: false
            })
        }), () => {
            dispatch(deleteData(windowType, docId))
                .then(response => {
                    dispatch(push('/window/' + windowType));
                });
            }
        );
    }

    redirect = (where) => {
        const {dispatch} = this.props;
        dispatch(push(where));
        this.handleBackdropClick(false);
    }

    render() {
        const {
            docSummaryData, siteName, docNoData, docNo, docStatus, docStatusData,
            windowType, dataId, breadcrumb, showSidelist, references, actions, indicator,
            viewId, inbox, homemenu, selected
        } = this.props;

        const {
            isSubheaderShow, isSideListShow, menuOverlay, isInboxOpen, scrolled,
            isMenuOverlayShow, tooltip, prompt
        } = this.state;

        return (
            <div>
            {
                prompt.open &&
                <Prompt
                    title={"Delete"}
                    text={"Are you sure?"}
                    buttons={{submit: "Delete", cancel: "Cancel"}}
                    onCancelClick={this.handlePromptCancelClick}
                    onSubmitClick={() => this.handlePromptSubmitClick(windowType, dataId)}
                />
            }

                {(isSubheaderShow) ? <div className="backdrop" onClick={e => this.handleBackdropClick(false)}></div> : null}
                {(isSideListShow) ? <div className="backdrop" onClick={e => this.handleCloseSideList(false)}></div> : null}
                <nav className={"header header-super-faded js-not-unselect " + (scrolled ? "header-shadow": "")}>
                    <div className="container-fluid">
                        <div className="header-container">
                            <div className="header-left-side">
                                <div
                                    onClick={e => {this.handleCloseSideList(this.handleSubheaderOpen); this.toggleTooltip('')}}
                                    onMouseEnter={(e) => this.toggleTooltip('tooltip1')}
                                    onMouseLeave={(e) => this.toggleTooltip('')}
                                    className={"btn-square btn-header tooltip-parent " +
                                        (isSubheaderShow ?
                                            "btn-meta-default-dark btn-subheader-open btn-header-open"
                                            : "btn-meta-primary")
                                        }
                                >
                                    <i className="meta-icon-more" />

                                    { tooltip.open === 'tooltip1' &&
                                    <Tooltips
                                        name={keymap.GLOBAL_CONTEXT.OPEN_ACTIONS_MENU}
                                        action={'Action menu'}
                                        type={''}
                                    /> }
                                </div>

                                <Breadcrumb
                                    homemenu={homemenu}
                                    breadcrumb={breadcrumb}
                                    windowType={windowType}
                                    docNo={docNo}
                                    docNoData={docNoData}
                                    docSummaryData={docSummaryData}
                                    dataId={dataId}
                                    siteName={siteName}
                                    menuOverlay={menuOverlay}
                                    handleMenuOverlay={this.handleMenuOverlay}
                                />

                            </div>
                            <div className="header-center">
                                <img src={logo} className="header-logo pointer" onClick={() => this.handleDashboardLink()} />
                            </div>
                            <div className="header-right-side">
                                {docStatus &&
                                    <div className="hidden-sm-down">
                                        <MasterWidget
                                            windowType={windowType}
                                            dataId={dataId}
                                            widgetData={[docStatusData]}
                                            noLabel={true}
                                            type="primary"
                                            {...docStatus}
                                        />
                                    </div>
                                }

                                <div className={
                                    "header-item-container header-item-container-static pointer tooltip-parent "+
                                    (isInboxOpen ? "header-item-open " : "")}
                                    onClick={(e) => this.handleInboxOpen(true, this.handleMenuOverlay)}
                                    onMouseEnter={(e) => this.toggleTooltip('tooltip2')}
                                    onMouseLeave={(e) => this.toggleTooltip('')}
                                >
                                    <span className={"header-item header-item-badge icon-lg"}>
                                        <i className="meta-icon-notifications" />
                                        {inbox.unreadCount > 0 && <span className="notification-number">{inbox.unreadCount}</span>}
                                    </span>
                                    { tooltip.open === 'tooltip2' &&
                                        <Tooltips
                                            name={keymap.GLOBAL_CONTEXT.OPEN_INBOX_MENU}
                                            action={'Inbox'}
                                            type={''}
                                        />
                                    }
                                </div>

                                <Inbox
                                    open={isInboxOpen}
                                    close={this.handleInboxOpen}
                                    disableClickOutside={!isInboxOpen}
                                    inbox={inbox}
                                />

                                {showSidelist &&
                                    <div
                                        className={
                                            "btn-square btn-header side-panel-toggle tooltip-parent " +
                                            (isSideListShow ?
                                                "btn-meta-default-bright btn-header-open"
                                                : "btn-meta-primary")
                                        }
                                        onClick={e => {this.handleBackdropClick(this.handleSideListToggle); this.toggleTooltip('')}}
                                        onMouseEnter={(e) => this.toggleTooltip('tooltip3')}
                                        onMouseLeave={(e) => this.toggleTooltip('')}
                                    >
                                        <i className="meta-icon-list" />
                                        { tooltip.open === 'tooltip3' &&
                                            <Tooltips
                                                name={keymap.GLOBAL_CONTEXT.OPEN_SIDEBAR_MENU}
                                                action={'Side list'}
                                                type={''}
                                            />
                                        }

                                    </div>
                                }
                            </div>
                        </div>
                    </div>
                    <Indicator indicator={indicator} />
                </nav>

                {isSubheaderShow && <Subheader
                    dataId={dataId}
                    references={references}
                    actions={actions}
                    windowType={windowType}
                    viewId={viewId}
                    onClick={e => this.handleBackdropClick(false)}
                    docNo={docNoData && docNoData.value}
                    openModal={this.openModal}
                    handlePrint={this.handlePrint}
                    handleDelete={this.handleDelete}
                    handleClone={this.handleClone}
                    redirect={this.redirect}
                    selected={selected}
                />}

                {showSidelist && <SideList
                    windowType={windowType}
                    open={isSideListShow}
                />}

                <GlobalShortcuts
                    handleSubheaderOpen={isSubheaderShow ? () => this.handleBackdropClick() : () => this.handleSubheaderOpen()}
                    handleMenuOverlay={isMenuOverlayShow ? () => this.handleMenuOverlay("", "") : () => this.handleMenuOverlay("", homemenu.nodeId)}
                    closeMenuOverlay={() => this.handleMenuOverlay("", "")}
                    handleSideListToggle = {showSidelist ? isSideListShow ? this.handleCloseSideList : e => this.handleBackdropClick(this.handleSideListToggle) : ''}
                    handleInboxOpen = {isInboxOpen ? () => this.handleInboxOpen(false) : () => this.handleInboxOpen(true)}
                    closeInbox = {() => this.handleInboxOpen(false)}
                    openModal = {dataId? () => this.openModal(windowType, "window", "Advanced edit", true) : ''}
                    handlePrint={dataId ? () => this.handlePrint(windowType, dataId, docNoData.value) : ''}
                    handleDelete={dataId ? this.handleDelete: ''}
                    redirect={windowType ? () => this.redirect('/window/'+ windowType +'/new') : ''}
                />
            </div>
        )
    }
}


Header.propTypes = {
    dispatch: PropTypes.func.isRequired,
    indicator: PropTypes.string.isRequired,
    selected: PropTypes.array.isRequired,
    viewId: PropTypes.string,
    homemenu: PropTypes.object.isRequired,
    inbox: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {windowHandler,listHandler, appHandler, menuHandler} = state;

    const {
        viewId
    } = listHandler || {
        viewId: ""
    }

    const {
        inbox
    } = appHandler || {
        inbox: {}
    }

    const {
        homemenu
    } = menuHandler || {
        homemenu: []
    }

    const {
        indicator,
        selected
    } = windowHandler || {
        indicator: "",
        selected: []
    }

    return {
        indicator,
        selected,
        viewId,
        inbox,
        homemenu
    }
}

Header.childContextTypes = {
    shortcuts: PropTypes.object.isRequired
}

Header = connect(mapStateToProps)(Header)

export default Header
