import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import './Header.css';

import Subheader from './SubHeader';

import {
    showSubHeader,
    hideSubHeader,
    toggleOrderList
} from '../../actions/SalesOrderActions'

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
        const {isSubheaderShow} = this.props;

        return (
            <div>
            {isSubheaderShow ? <div className="backdrop" onClick={this.handleBackdropClick}></div> : null}
            <nav className="header header-super-faded">
                <div className="container">
                    <div className="row">
                        <div className="col-sm-4">
                            <div
                                onClick={this.handleSubheaderOpen}
                                className={"btn-square " + (isSubheaderShow ? "btn-meta-default btn-subheader-open" : "btn-meta-primary")}
                            >
                                <i className="meta-icon-hamburger" />
                            </div>
                            <div className="header-middle header-breadcrumb">
                                Home / Sales orders
                            </div>
                        </div>
                        <div className="col-sm-4 text-xs-center header-middle">
                            <span className="header-label">Sales Order</span>
                            <div className="input-icon-container header-input-sm">
                                <input className="form-control form-control-meta" type="text"/>
                                <i className="meta-icon-edit input-icon-right"></i>
                            </div>
                        </div>
                        <div className="col-sm-4">
                            <div className="pull-xs-right btn-square btn-meta-primary" onClick={this.handleOrderListToggle}>
                                <i className="meta-icon-list" />
                            </div>
                            <div className="header-middle pull-xs-right">
                                <div className="btn-group">
                                    <button type="button" className="btn btn-meta-secondary btn-sm">Draft</button>
                                    <button type="button" className="btn btn-meta-secondary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span className="sr-only">Toggle Dropdown</span>
                                    </button>
                                </div>
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
