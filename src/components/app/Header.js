import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import './Header.css';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import Subheader from './SubHeader';

import {
    showSubHeader,
    hideSubHeader,
    toggleOrderList,
    changeOrderStatus
} from '../../actions/SalesOrderActions';

class Header extends Component {
    constructor(props){
        super(props);
    }
    handleSubheaderOpen = () => {
        const {dispatch, isSubheaderShow} = this.props;

        if(isSubheaderShow){
            dispatch(hideSubHeader());
        }else{
            dispatch(showSubHeader());
        }
    }
    handleOrderListToggle = () => {
        const {dispatch, isOrderListShow} = this.props;
        dispatch(toggleOrderList(!isOrderListShow));
    }
    handleBackdropClick = () => {
        this.props.dispatch(hideSubHeader());
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
        switch (id) {
            case 0:
                return "Complete";
                break;
            case 1:
                return "In progress";
                break;
            case 2:
                return "Void";
                break;
        }
    }
    render() {
        const {isSubheaderShow, isOrderListShow, orderStatus} = this.props;

        return (
            <div>
            {(isSubheaderShow || isOrderListShow) ? <div className="backdrop" onClick={this.handleBackdropClick}></div> : null}
            <nav className="header header-super-faded">
                <div className="container">
                    <div className="header-container">
                        <div className="header-left-side">
                            <div
                                onClick={this.handleSubheaderOpen}
                                className={"btn-square " + (isSubheaderShow ? "btn-meta-default btn-subheader-open" : "btn-meta-primary")}
                            >
                                <i className="meta-icon-hamburger" />
                            </div>

                            <div className="header-breadcrumb">
                                <div>Home / Sales orders</div>
                                <div className="input-icon-container header-input-id header-input-sm">
                                    <input className="form-control form-control-meta" type="text"/>
                                    <i className="meta-icon-edit input-icon-right"></i>
                                </div>
                            </div>
                        </div>
                        <div className="header-center">
                            <img src={logo} className="header-logo"/>
                        </div>
                        <div className="header-right-side">
                            <div className="meta-dropdown-toggle dropdown-status-toggler" tabIndex="0" ref={(c) => this.statusDropdown = c} onBlur={this.handleDropdownBlur} onFocus={this.handleDropdownFocus}>
                                <div className="tag tag-success">{this.getOrderStatus(orderStatus)}</div>
                                <i className="meta-icon-chevron"/>
                                <ul className="dropdown-status-list">
                                    <li className="dropdown-status-item" onClick={() => this.handleChangeStatus(0)}>Complete</li>
                                    <li className="dropdown-status-item" onClick={() => this.handleChangeStatus(1)}>In progress</li>
                                    <li className="dropdown-status-item" onClick={() => this.handleChangeStatus(2)}>Void</li>
                                </ul>
                            </div>

                            <div className="btn-square btn-meta-primary side-panel-toggle" onClick={this.handleOrderListToggle}>
                                {isOrderListShow ? <i className="meta-icon-close" />: <i className="meta-icon-list" />}
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
            <Subheader open={isSubheaderShow} />
            </div>
        )
    }
}


Header.propTypes = {
    isSubheaderShow: PropTypes.bool.isRequired,
    orderStatus: PropTypes.number.isRequired,
    isOrderListShow: PropTypes.bool.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler, routing } = state;
    const {
        isSubheaderShow,
        isOrderListShow,
        orderStatus
    } = salesOrderStateHandler || {
        isSubheaderShow: false,
        isOrderListShow: false,
        orderStatus: 0
    }
    return {
        isSubheaderShow,
        isOrderListShow,
        orderStatus
    }
}

Header = connect(mapStateToProps)(Header)

export default Header
