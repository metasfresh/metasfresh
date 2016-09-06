import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

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
        if(this.state.isSubheaderShow){
            this.setState({isSubheaderShow: false});
        }else{
            this.setState({isSubheaderShow: true});
        }
    }
    handleOrderListToggle = () => {
        if(this.state.isOrderListShow){
            this.setState({isOrderListShow: false});
        }else{
            this.setState({isOrderListShow: true});
        }
    }
    handleBackdropClick = () => {
        this.setState({isSubheaderShow: false});
    }

    render() {
        const {docNoData, docNo, docStatus, docStatusData, windowType, dataId} = this.props;
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

                                <div className="header-breadcrumb">
                                    <div>Home / Sales orders</div>
                                    {docNo && <div className="input-icon-container header-input-id header-input-sm">
                                        <Widget
                                            windowType={windowType}
                                            dataId={dataId}
                                            widgetData={[docNoData]}
                                            noLabel={true}
                                            {...docNo}
                                        />
                                        <i className="meta-icon-edit input-icon-right"></i>
                                    </div>}
                                </div>
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
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { indicator } = state.windowHandler;
    return {
      indicator
    }
}

Header = connect(mapStateToProps)(Header)

export default Header
