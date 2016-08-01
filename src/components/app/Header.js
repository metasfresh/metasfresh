import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import './Header.css';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import Subheader from './SubHeader';

import {
    showSubHeader,
    hideSubHeader,
    toggleOrderList
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
    render() {
        const {isSubheaderShow, isOrderListShow} = this.props;

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
                            <div className="meta-dropdown-toggle dropdown-status-toggler dropdown-status-open" tabIndex="0">
                                <div className="tag tag-success">Completed</div>
                                <i className="meta-icon-chevron"/>
                                <ul className="dropdown-status-list">
                                    <li className="dropdown-status-item">Complete</li>
                                    <li className="dropdown-status-item">In progress</li>
                                    <li className="dropdown-status-item">Void</li>
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
    isOrderListShow: PropTypes.bool.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler, routing } = state;
    const {
        isSubheaderShow,
        isOrderListShow
    } = salesOrderStateHandler || {
        isSubheaderShow: false,
        isOrderListShow: false
    }
    return {
        isSubheaderShow,
        isOrderListShow
    }
}

Header = connect(mapStateToProps)(Header)

export default Header
