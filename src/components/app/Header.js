import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import update from 'react-addons-update';
import {push} from 'react-router-redux';

import '../../assets/css/header.css';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import Subheader from './SubHeader';
import Widget from '../Widget';
import SideList from '../app/SideList';
import Indicator from './Indicator';
import MenuOverlay from './MenuOverlay';


import {
    indicatorState
} from '../../actions/WindowActions';

class Header extends Component {
    constructor(props){
        super(props);

        this.state = {
            isSubheaderShow: false,
            isSideListShow: false,
            indicator: 'saved',
            menuOverlay: null,
            scrolled: false
        }
    }

    linkToPage = (page) => {
        const {dispatch} = this.props;
        dispatch(push("/window/"+page));
    }

    handleSubheaderOpen = () => {
        this.setState(Object.assign({}, this.state, {isSubheaderShow: !this.state.isSubheaderShow}));
    }
    handleSideListToggle = () => {
        this.setState(Object.assign({}, this.state, {isSideListShow: !this.state.isSideListShow}));
    }
    handleCloseSideList= (callback) => {
        this.setState(Object.assign({}, this.state, {isSideListShow: false}), callback);
    }
    handleBackdropClick = (callback) => {
        this.setState(Object.assign({}, this.state, {isSubheaderShow: false}), callback);
    }
    handleMenuOverlay = (e, nodeId) => {
        const {isSubheaderShow, isSideListShow} = this.state;
        e && e.preventDefault();

        let toggelBreadcrumb = () => {
            this.setState(Object.assign({}, this.state, {
                menuOverlay: nodeId
            }));
        }
        if(isSubheaderShow){
            this.handleBackdropClick(toggelBreadcrumb);
        }else if(isSideListShow){
            this.handleCloseSideList(toggelBreadcrumb);
        }else{
            toggelBreadcrumb();
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

    renderBreadcrumb = () => {
        const {breadcrumb, windowType, docNo, docNoData, docSummaryData, dataId, siteName} = this.props;
        const {menuOverlay} = this.state;

        return (
            <span className="header-breadcrumb">
                {breadcrumb && breadcrumb.map((item, index) =>
                    <span key={index}>
                        {!!index && <span className="divider">/</span>}
                        <span title={(index === 0 ? "" : item.children.captionBreadcrumb)}
                            className={ (!item.children.elementId ? "menu-overlay-expand " : (docNo ? 'menu-overlay-link' : '')) + (index === 0 ? "ico-home" : "")}
                            onClick={ !item.children.elementId ?  e => this.handleMenuOverlay(e, item.nodeId) : (windowType ? e => this.linkToPage(windowType) : '' )}
                        >
                            {(index === 0) ? <i className="meta-icon-menu" /> : item && item.children && item.children.captionBreadcrumb}
                        </span>
                        {menuOverlay === item.nodeId &&
                            <MenuOverlay
                                nodeId={item.nodeId}
                                node={item}
                                onClickOutside={e => this.handleMenuOverlay(e, "")}
                                disableOnClickOutside={menuOverlay !== item.nodeId}
                                siteName={siteName}
                                index={index}
                            />
                        }
                    </span>
                )}
                {docNo && <span className="divider">/</span>}

                {docNo && <span className="header-input-id header-input-sm">
                    <Widget
                        windowType={windowType}
                        dataId={dataId}
                        widgetData={[docNoData]}
                        noLabel={true}
                        icon={true}
                        {...docNo}
                    />
                </span>}

                {docSummaryData && <div className="header-breadcrumb">
                    <span>{docSummaryData.value}</span>
                </div>}

                {siteName && <span className="divider">/</span>}

                {siteName && <div className="header-breadcrumb">
                    <span>{siteName}</span>
                </div>}

            </span>
        )
    }

    render() {
        const {docSummaryData, docNoData, docNo, docStatus, docStatusData, windowType, dataId, breadcrumb, showSidelist, references,actions} = this.props;
        const {isSubheaderShow, isSideListShow, indicator, menuOverlay} = this.state;

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

                                {this.renderBreadcrumb()}
                            </div>
                            <div className="header-center">
                                <img src={logo} className="header-logo pointer" onClick={() => this.handleDashboardLink()} />
                            </div>
                            <div className="header-right-side">
                                {docStatus &&
                                    <Widget
                                        windowType={windowType}
                                        dataId={dataId}
                                        widgetData={[docStatusData]}
                                        noLabel={true}
                                        type="primary"
                                        {...docStatus}
                                    />
                                }

                                <span className="notification"><i className="meta-icon-notifications"/><span className="notification-number">4</span></span>

                                <Indicator indicator={this.props.indicator} />

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
                </nav>

                {isSubheaderShow && <Subheader
                    dataId={dataId}
                    references={references}
                    actions={actions}
                    windowType={windowType}
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
    indicator: PropTypes.string.isRequired
};

function mapStateToProps(state) {
    const {windowHandler} = state;


    const {
        indicator
    } = windowHandler || {
        indicator: ""
    }

    return {
      indicator
    }
}

Header = connect(mapStateToProps)(Header)

export default Header
