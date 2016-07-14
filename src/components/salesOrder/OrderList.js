import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

class OrderList extends Component {
    constructor(props) {
        super(props);
    }
    handleClose = (e) => {
        e.preventDefault();
        this.panel.classList.remove('order-list-panel-open');
    }
    renderTableBody = () => {
        return this.props.orderList.map((order) => {
            return (
                <tr key={order.id}>
                    <td>{order.id}</td>
                    <td>{order.purchaser}</td>
                    <td>{order.amount + " EUR"}</td>
                    <td>{order.ordered}</td>
                    <td>{order.status}</td>
                </tr>
            )
        });
    }
    render() {
        return (
            <div ref={(c)=>this.panel=c} className="order-list-panel order-list-panel-open">
                <div className="order-list-panel-header">
                    <div
                        onClick={this.handleClose}
                        className="btn-square btn-meta-primary"
                    >
                        <i className="meta-icon-close" />
                    </div>

                    <a href="#" className="btn-icon order-list-panel-icon pull-xs-right">
                        <i className="meta-icon-preview-1" />
                    </a>
                    <a href="#" className="btn-icon order-list-panel-icon pull-xs-right">
                        <i className="meta-icon-calendar" />
                    </a>
                </div>
                <div className="order-list-panel-body">
                <table className="table table-bordered-horizontally">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Purchaser</th>
                            <th>Total amount</th>
                            <th>Ordered</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderTableBody()}
                    </tbody>
                </table>
                </div>
            </div>
        )
    }
}

OrderList.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        orderList
    } = salesOrderStateHandler || {
        orderList: []
    }
    return {
        orderList
    }
}

OrderList = connect(mapStateToProps)(OrderList)

export default OrderList
