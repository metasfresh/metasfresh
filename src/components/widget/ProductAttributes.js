import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import RawWidget from '../RawWidget';

class ProductAttributes extends Component {
    constructor(props) {
        super(props);

        this.state = {
            dropdown: false
        }
    }

    handleToggle = (option) => {
        this.setState(Object.assign({}, this.state, {
            dropdown: !!option
        }))
    }

    handleClickOutside = () => {
        this.handleToggle(false);
    }

    render() {
        const {dropdown, fields} = this.state;

        return (
            <div className="product-attributes">
                <div
                    onClick={() => this.handleToggle(!dropdown)}
                    className="tag tag-lg tag-secondary pointer"
                >
                    Edit attributes
                </div>
                {dropdown && <div
                    className="product-attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced"
                >
                    {
                        fields && fields.map((item, id) =>
                            <RawWidget
                                fields={item}
                            />
                        )
                    }
                </div>}
            </div>
        )
    }
}

ProductAttributes.propTypes = {
    dispatch: PropTypes.func.isRequired
};

ProductAttributes = connect()(onClickOutside(ProductAttributes))

export default ProductAttributes
