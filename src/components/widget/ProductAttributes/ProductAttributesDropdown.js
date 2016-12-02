import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import RawWidget from '../RawWidget';

import {
    getPattributeLayout,
    getPattributeInstance
} from '../../../actions/AppActions';

class ProductAttributesDropdown extends Component {
    constructor(props) {
        super(props);

        this.state = {
            layout: null,
            data: null
        }

        const {dispatch, tmpId} = this.props;

        dispatch(
            getPattributeInstance(tmpId)
        ).then(response => {
            const {id, fields} = response.data;


            this.setState(Object.assign({}, this.state, {
                data: fields
            }));

            return dispatch(getPattributeLayout(id));
        }).then(response => {
            const {elements} = response.data;

            this.setState(Object.assign({}, this.state, {
                layout: elements
            }));
        });
    }

    handleClickOutside = () => {
        const {toggle} = this.props;
        toggle(false);
    }

    render() {
        const {data,layout} = this.props;

        return (
            <div className="product-attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced">
                {
                    data && data.map((item, id) =>
                        <span>Item</span>
                    )
                }
            </div>
        )
    }
}

ProductAttributesDropdown.propTypes = {
    dispatch: PropTypes.func.isRequired
};

ProductAttributesDropdown = connect()(onClickOutside(ProductAttributesDropdown))

export default ProductAttributesDropdown
