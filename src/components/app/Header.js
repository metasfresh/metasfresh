import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import '../../assets/css/header.css';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import Subheader from './SubHeader';
import Widget from '../Widget';
import OrderList from '../app/OrderList';

import {
    changeOrderStatus
} from '../../actions/AppActions';

class Header extends Component {
    constructor(props){
        super(props);

        this.status = [
            "Draft",
            "Completed",
            "In progress",
            "Void"
        ];

        this.state = {
            isSubheaderShow: false,
            isOrderListShow: false
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
    handleChangeStatus = (status) => {
        this.props.dispatch(changeOrderStatus(status));
        this.statusDropdown.blur();
    }
    handleDropdownBlur = () => {
        this.statusDropdown.classList.remove('dropdown-status-open');
    }
    handleDropdownFocus = () => {
        this.statusDropdown.classList.add('dropdown-status-open');
    }
    getOrderStatus = (id) => {
        return this.status[id];
    }
    getStatusClassName = (id) => {
        if(id == 0){
            return "dropdown-status-item-def";
        }else if (id == 1){
            return "dropdown-status-item-def-1";
        }else{
            return "";
        }
    }
    getStatusContext = (id) => {
        if(id == 0){
            return "primary"
        }else if (id == 1){
            return "success"
        }else {
            return "default"
        }
    }
    renderStatusList = () => {
        return this.status.map((item, index) => {
            if(index != this.props.orderStatus){
                return <li
                    key={index}
                    className={
                        "dropdown-status-item " +
                        this.getStatusClassName(index)
                    }
                    onClick={() => this.handleChangeStatus(index)}
                >
                    {item}
                </li>
            }
        })
    }
    render() {
        const {orderStatus, data} = this.props;
        const {isSubheaderShow, isOrderListShow} = this.state;
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
                                    <div className="input-icon-container header-input-id header-input-sm">
                                        <input defaultValue="docNo" readonly className="form-control form-control-meta" type="text"/>
                                        <i className="meta-icon-edit input-icon-right"></i>
                                    </div>
                                </div>
                            </div>
                            <div className="header-center">
                                <img src={logo} className="header-logo"/>
                            </div>
                            <div className="header-right-side">
                                <div className="meta-dropdown-toggle dropdown-status-toggler" tabIndex="0" ref={(c) => this.statusDropdown = c} onBlur={this.handleDropdownBlur} onFocus={this.handleDropdownFocus}>
                                    <div className={"tag tag-" + this.getStatusContext(orderStatus)}>{this.getOrderStatus(orderStatus)}</div>
                                    <i className={"meta-icon-chevron-1 meta-icon-" + this.getStatusContext(orderStatus)}/>
                                    <ul className="dropdown-status-list">
                                        {this.renderStatusList()}
                                    </ul>
                                </div>

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
                <Subheader open={isSubheaderShow} />
                <OrderList open={isOrderListShow} />

            </div>
        )
    }
}


Header.propTypes = {
    orderStatus: PropTypes.number.isRequired,
    data: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { windowHandler } = state;
    const {
        orderStatus,
        data
    } = windowHandler || {
        orderStatus: 0,
        data: []
    }

    return {
        orderStatus,
        data
    }
}

Header = connect(mapStateToProps)(Header)

export default Header
