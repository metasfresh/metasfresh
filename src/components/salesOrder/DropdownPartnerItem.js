import React, { Component } from 'react';

class DropdownPartnerItem extends Component {
    constructor(props) {
        super(props);
    }
    renderSubitems = (items) => {
        return items.map((item) => <p className="input-dropdown-item-subtitle">{item}</p>);
    }
    render() {
        const {data} = this.props;
        return (
            <div className="input-dropdown-list-option" onClick={(e) => this.props.onClick(e, partner)}>
                <p className="input-dropdown-item-title">{data[0]}</p>
                {this.renderSubitems(data.shift())}
            </div>
        )
    }
}

export default DropdownPartnerItem
