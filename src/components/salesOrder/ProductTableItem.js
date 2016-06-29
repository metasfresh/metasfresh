import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

class ProductTableItem extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <tr>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
                <td>1</td>
            </tr>
        )
    }
}


ProductTableItem.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler } = state;
    const {

    } = salesOrderStateHandler || {

    }

    return {

    }
}

ProductTableItem = connect(mapStateToProps)(ProductTableItem)

export default ProductTableItem
