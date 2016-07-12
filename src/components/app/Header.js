import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import './Header.css';

import SubHeader from './SubHeader';

import {
    showSubHeader,
    hideSubHeader
} from '../../actions/SalesOrderActions'

class Header extends Component {
    constructor(props){
        super(props);
    }
    handleSubheaderOpen = () => {
        const {dispatch, isSubheaderShow} = this.props;

        dispatch((isSubheaderShow ? hideSubHeader() : showSubHeader()));
    }
    render() {
        const {isSubheaderShow} = this.props;
        return (
            <div>
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
                            <div className="pull-xs-right btn-square btn-meta-primary">
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
            <SubHeader open={isSubheaderShow} />
            </div>
        )
    }
}


Header.propTypes = {
    isSubheaderShow: PropTypes.bool.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler } = state;
    const {
        isSubheaderShow
    } = salesOrderStateHandler || {
        isSubheaderShow: false
    }

    return {
        isSubheaderShow
    }
}

Header = connect(mapStateToProps)(Header)

export default Header
