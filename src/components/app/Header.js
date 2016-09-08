import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import update from 'react-addons-update';

import '../../assets/css/header.css';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import Subheader from './SubHeader';
import Widget from '../Widget';
import OrderList from '../app/OrderList';
import Indicator from './Indicator';


import {
    indicatorState
} from '../../actions/WindowActions';

class Header extends Component {
    constructor(props){
        super(props);

        this.state = {
            isSubheaderShow: false,
            isOrderListShow: false,
            indicator: 'saved'
        }
    }

    handleSubheaderOpen = () => {
        this.setState(Object.assign({}, this.state, {isSubheaderShow: !this.state.isSubheaderShow}));
    }
    handleOrderListToggle = () => {
        this.setState(Object.assign({}, this.state, {isOrderListShow: !this.state.isOrderListShow}));
    }
    handleBackdropClick = () => {
        this.setState(Object.assign({}, this.state, {isSubheaderShow: false}));
    }


    render() {
        const {docNoData, docNo, docStatus, docStatusData, windowType, dataId, breadcrumb} = this.props;
        const {isSubheaderShow, isOrderListShow, indicator} = this.state;

        return (
            <div>
                {(isSubheaderShow || isOrderListShow) ? <div className="backdrop" onClick={this.handleBackdropClick}></div> : null}
                <nav className="header header-super-faded">
                    <div className="container">
                        <div className="header-container">
                            <div className="header-left-side">
                                <div
                                    onClick={this.handleSubheaderOpen}
                                    className={"btn-square btn-header " + (isSubheaderShow ? "btn-meta-default btn-subheader-open btn-header-open" : "btn-meta-primary")}
                                >
                                    <i className="meta-icon-more" />
                                </div>

                                <span className="header-breadcrumb">
                                    {breadcrumb.map((item, index) =>
                                        <span key={index}>
                                            {!!index && <span className="divider">/</span>}
                                            <a href="">{item}</a>
                                        </span>
                                    )}
                                </span>


                                {docNo && <div className="header-input-id header-input-sm">
                                    <Widget
                                        windowType={windowType}
                                        dataId={dataId}
                                        widgetData={[docNoData]}
                                        noLabel={true}
                                        icon={true}
                                        {...docNo}
                                    />
                                </div>}
                            </div>
                            <div className="header-center">
                                <img src={logo} className="header-logo"/>
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

                                <Indicator indicator={this.props.indicator} />

                                <div
                                    className={"btn-square btn-header side-panel-toggle " + (isOrderListShow ? "btn-meta-default-bright btn-header-open" : "btn-meta-primary")}
                                    onClick={this.handleOrderListToggle}
                                >
                                    {isOrderListShow ? <i className="meta-icon-close-1" />: <i className="meta-icon-list" />}
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>

                <Subheader open={isSubheaderShow} windowType={windowType} />
                <OrderList open={isOrderListShow} />

            </div>
        )
    }
}


Header.propTypes = {
    dispatch: PropTypes.func.isRequired,
    indicator: PropTypes.string.isRequired,
    breadcrumb: PropTypes.array.isRequired
};

function mapStateToProps(state) {
    const {windowHandler, menuHandler} = state;

    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: {}
    }

    const {
        indicator
    } = windowHandler || {
        indicator: ""
    }

    return {
      indicator,
      breadcrumb
    }
}

Header = connect(mapStateToProps)(Header)

export default Header
