import React, { Component } from 'react';

class DropdownPartnerItem extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {partner} = this.props;
        return (
            <div className="input-dropdown-list-option" onClick={(e) => this.props.onClick(e, partner)}>
                <p className="input-dropdown-item-title">{partner.n}</p>
                <p className="input-dropdown-item-subtitle">{partner.n}</p>
                <p className="input-dropdown-item-subtitle">VAT {partner.n}</p>
            </div>
        )
    }
}

export default DropdownPartnerItem
