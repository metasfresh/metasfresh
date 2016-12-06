import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import ProductAttributesDropdown from './ProductAttributesDropdown';

class ProductAttributes extends Component {
    constructor(props) {
        super(props);

        this.state = {
            dropdown: false,
            layout: null,
            data: null
        }
    }

    handleToggle = (option) => {
        const {handleBackdropLock} = this.props;
        this.setState(Object.assign({}, this.state, {
            dropdown: !!option
        }), () => {
            //Method is disabling outside click in parents
            //elements if there is some
            handleBackdropLock && handleBackdropLock(!!option);
        })
    }

    render() {
        const {widgetData,fields, dispatch, rowId, patch} = this.props;
        const {dropdown} = this.state;
        const {value} = widgetData;
        const tmpId = Object.keys(value)[0];
        const label = value[tmpId];

        return (
            <div className={
                "product-attributes " +
                (rowId ? "product-attributes-in-table " : "")
            }>
                <div
                    onClick={() => this.handleToggle(!dropdown)}
                    className="tag tag-lg tag-block tag-secondary pointer"
                >
                    {label ? label : "Edit attributes"}
                </div>
                {dropdown &&
                    <ProductAttributesDropdown
                        tmpId={tmpId}
                        toggle={this.handleToggle}
                        patch={patch}
                    />
                }
            </div>
        )
    }
}

ProductAttributes.propTypes = {
    dispatch: PropTypes.func.isRequired
};

ProductAttributes = connect()(ProductAttributes)

export default ProductAttributes
