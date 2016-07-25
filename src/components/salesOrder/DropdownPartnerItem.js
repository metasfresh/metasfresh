import React, { Component } from 'react';

class DropdownPartnerItem extends Component {
    constructor(props) {
        super(props);
    }
    renderSubitems = (items) => {
        return items.map((item, index) => <p key={index} className="input-dropdown-item-subtitle">{item}</p>);
    }
    render() {
        const {data} = this.props;

        return (
            <div className="input-dropdown-list-option" onClick={(e) => this.props.onClick(e,data)}>
                <p className="input-dropdown-item-title">{data['n']}</p>
                {this.renderSubitems([data['n'], data['n']])}
            </div>
        )
    }
}

export default DropdownPartnerItem
