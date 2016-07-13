import React, { Component } from 'react';

class DropdownPartnerItem extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {partner} = this.props;
        return (
            <div className="input-dropdown-list-option" onClick={(e) => this.props.onClick(e, partner)}>
                <p className="input-dropdown-item-title">{partner.name}</p>
                <p className="input-dropdown-item-subtitle">{partner.address}</p>
                <p className="input-dropdown-item-subtitle">VAT {partner.vat}</p>
            </div>
        )
    }
}

export default DropdownPartnerItem
