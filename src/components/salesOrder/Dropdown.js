import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import DropdownPartnerItem from './DropdownPartnerItem';
import {autocomplete, autocompleteRequest, autocompleteSuccess} from '../../actions/SalesOrderActions';

class Dropdown extends Component {
    constructor(props) {
        super(props);
    }
    handleSelect = (e, select) => {
        e.preventDefault();
        this.inputSearch.value = select.n;
        this.inputSearchRest.innerHTML = select.n;
        this.handleBlur();
    }
    handleBlur = () => {
        this.dropdown.classList.remove("input-dropdown-focused");
    }
    handleFocus = (e) => {
        e.preventDefault();
        this.handleChange();
        this.dropdown.classList.add("input-dropdown-focused");
    }
    handleChange = (e) => {
        this.inputSearchRest.innerHTML = "";
        this.dropdown.classList.add("input-dropdown-focused");
        this.props.dispatch(autocomplete(this.inputSearch.value));

        if(this.inputSearch.value == ""){
            this.props.dispatch(autocompleteSuccess([]));
        }else{
            this.props.dispatch(autocompleteRequest(this.inputSearch.value, this.props.property));
        }
    }
    handleClear = (e) => {
        e.preventDefault();
        this.inputSearchRest.innerHTML = "";
        this.inputSearch.value = "";
        this.props.dispatch(autocomplete(""));
        this.props.dispatch(autocompleteSuccess([]));
    }
    renderRecent = () => {
        const {recent} = this.props;
        return recent.map(item => <DropdownPartnerItem key={item.id} data={item} onClick={this.handleSelect}/> );
    }
    renderLookup = () => {
        return this.props.autocomplete.results.map(partner => <DropdownPartnerItem key={partner.id} data={partner} onClick={this.handleSelect}/> );
    }
    render() {
        const {autocomplete} = this.props;
        return (
            <div
                tabIndex="0"
                ref={(c) => this.dropdown = c}
                onFocus={()=>this.inputSearch.focus()}
                onBlur={this.handleBlur}
                className="input-dropdown"
            >
                <div className="input-toggled">
                    <div className="input-toggled-editable">
                        <input
                            type="text"
                            className="input-dropdown-field font-weight-bold"
                            onFocus={this.handleFocus}
                            onChange={this.handleChange}
                            ref={(c) => this.inputSearch = c}
                        />
                    </div>
                    <div ref={c => this.inputSearchRest = c} className="input-toggled-rest">
                    </div>
                    <div className="input-toggled-icon">
                        <i onClick={this.handleClear} className="icon-rounded icon-rounded-space">x</i>
                    </div>
                </div>
                <div className="clearfix" />
                <div className="input-dropdown-list">
                    <div className="input-dropdown-list-header">
                        {autocomplete.results.length > 0 ? "Are you looking for..." : "Recent lookups"}
                    </div>
                    {autocomplete.results.length <= 0 && <div>{this.renderRecent()}</div> }
                    {autocomplete.results.length > 0 && <div>{this.renderLookup()}</div> }
                </div>
            </div>
        )
    }
}


Dropdown.propTypes = {
    autocomplete: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        autocomplete
    } = salesOrderStateHandler || {
        autocomplete: {
            query: ""
        }
    }


    return {
        autocomplete
    }
}

Dropdown = connect(mapStateToProps)(Dropdown)

export default Dropdown
