import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import DropdownPartnerItem from './DropdownPartnerItem';
import {autocomplete} from '../../actions/SalesOrderActions';

class Dropdown extends Component {
    constructor(props) {
        super(props);
    }
    handleSelect = (e, select) => {
        this.inputSearch.value = select.name;
        this.inputSearchRest.innerHTML = select.address + " VAT " + select.vat;
        this.handleBlur();
    }
    handleBlur = () => {
        this.dropdown.classList.remove("input-dropdown-focused");
    }
    handleFocus = (e) => {
        e.preventDefault();
        this.props.dispatch(autocomplete(''));
        this.dropdown.classList.add("input-dropdown-focused");
    }
    handleChange = (e) => {
        e.preventDefault();
        this.props.dispatch(autocomplete(this.inputSearch.value));
    }
    renderRecent = () => {
        return this.props.recentPartners.map(partner => <DropdownPartnerItem key={partner.id} partner={partner} onClick={this.handleSelect}/> );
    }
    render() {
        const {autocomplete} = this.props;
        return (
            <div
                tabIndex="0"
                ref={(c) => this.dropdown = c}
                onFocus={()=>this.inputSearch.focus()}
                className="input-dropdown"
            >
                <div className="input-toggled">
                    <div className="input-toggled-editable">
                        <input
                            type="text"
                            className="input-dropdown-field"
                            onFocus={this.handleFocus}
                            onChange={this.handleChange}
                            ref={(c) => this.inputSearch = c}
                        />
                    </div>
                    <div ref={c => this.inputSearchRest = c} className="input-toggled-rest">

                    </div>
                </div>
                <div className="clearfix" />
                <div className="input-dropdown-list">
                    <div className="input-dropdown-list-header">
                        {autocomplete.query ? "Are you looking for..." : "Recent partners"}
                    </div>
                    <div>
                        {this.renderRecent()}
                    </div>
                </div>
            </div>
        )
    }
}


Dropdown.propTypes = {
    recentPartners: PropTypes.array.isRequired,
    autocomplete: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        recentPartners,
        autocomplete
    } = salesOrderStateHandler || {
        recentPartners: [],
        autocomplete: {
            query: ""
        }
    }

    return {
        recentPartners,
        autocomplete
    }
}

Dropdown = connect(mapStateToProps)(Dropdown)

export default Dropdown
