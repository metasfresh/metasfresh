import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import update from 'react-addons-update';
import {push} from 'react-router-redux';

import '../../assets/css/header.css';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import Subheader from './SubHeader';
import Breadcrumb from './Breadcrumb';
import MasterWidget from '../MasterWidget';
import SideList from '../app/SideList';
import Indicator from './Indicator';
import Inbox from './Inbox/Inbox';


import {
    indicatorState
} from '../../actions/WindowActions';

class Header extends Component {
    constructor(props){
        super(props);

        this.state = {
            isSubheaderShow: false,
            isSideListShow: false,
            menuOverlay: null,
            scrolled: false
        }
    }

    handleSubheaderOpen = () => {
        this.setState(Object.assign({}, this.state, {isSubheaderShow: !this.state.isSubheaderShow}));
    }
    handleSideListToggle = () => {
        const {isSideListShow} = this.state;
        this.toggleScrollScope(!isSideListShow);

        this.setState(Object.assign({}, this.state, {isSideListShow: !isSideListShow}));
    }
    handleCloseSideList = (callback) => {
        this.setState(Object.assign({}, this.state, {isSideListShow: false}), callback);
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
            }));
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

    render() {
        const {
            docSummaryData, siteName, docNoData, docNo, docStatus, docStatusData,
            windowType, dataId, breadcrumb, showSidelist, references, actions, indicator,
            viewId
        } = this.props;

        const {
            isSubheaderShow, isSideListShow, menuOverlay
        } = this.state;

        return (
            <div>
                {(isSubheaderShow) ? <div className="backdrop" onClick={e => this.handleBackdropClick(false)}></div> : null}
                {(isSideListShow) ? <div className="backdrop" onClick={e => this.handleCloseSideList(false)}></div> : null}
                <nav className={"header header-super-faded " + (this.state.scrolled ? "header-shadow": "")}>
                    <div className="container-fluid">
                        <div className="header-container">
                            <div className="header-left-side">
                                <div
                                    onClick={e => this.handleCloseSideList(this.handleSubheaderOpen)}
                                    className={"btn-square btn-header " + (isSubheaderShow ? "btn-meta-default-dark btn-subheader-open btn-header-open" : "btn-meta-primary")}
                                >
                                    <i className="meta-icon-more" />
                                </div>

                                <Breadcrumb
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

                                <Inbox />


                                {showSidelist &&
                                    <div
                                        className={"btn-square btn-header side-panel-toggle " + (isSideListShow ? "btn-meta-default-bright btn-header-open" : "btn-meta-primary")}
                                        onClick={e => this.handleBackdropClick(this.handleSideListToggle)}
                                    >
                                        <i className="meta-icon-list" />
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
                />}

                {showSidelist && <SideList
                    windowType={windowType}
                    open={isSideListShow}
                />}
            </div>
        )
    }
}


Header.propTypes = {
    dispatch: PropTypes.func.isRequired,
    indicator: PropTypes.string.isRequired,
    viewId: PropTypes.string
};

function mapStateToProps(state) {
    const {windowHandler,listHandler} = state;

    const {
        viewId
    } = listHandler || {
        viewId: ""
    }


    const {
        indicator
    } = windowHandler || {
        indicator: ""
    }

    return {
        indicator,
        viewId
    }
}

Header = connect(mapStateToProps)(Header)

export default Header
