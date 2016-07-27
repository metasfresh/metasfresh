import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

class DropdownPartnerItem extends Component {
    constructor(props) {
        super(props);
    }
    renderSubitems = (items) => {
        return items.map((item, index) => <p key={index} className="input-dropdown-item-subtitle">{item}</p>);
    }
    render() {
        const {data, autocomplete} = this.props;

        return (
            <div className={"input-dropdown-list-option " + (autocomplete.selected == data['id'] ? 'input-dropdown-list-option-key-on' : "") } onClick={(e) => this.props.onClick(e,data)}>
                <p className="input-dropdown-item-title">{data['n']}</p>
                {this.renderSubitems([data['n'], data['n']])}
            </div>
        )
    }
}


DropdownPartnerItem.propTypes = {
    autocomplete: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        autocomplete
    } = salesOrderStateHandler || {
        autocomplete: {
            selected: null
        }
    }


    return {
        autocomplete
    }
}

DropdownPartnerItem = connect(mapStateToProps)(DropdownPartnerItem)

export default DropdownPartnerItem
