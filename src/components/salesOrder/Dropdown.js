import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import DropdownPartnerItem from './DropdownPartnerItem';
import {autocomplete, autocompleteRequest, autocompleteSelect, autocompleteSuccess} from '../../actions/SalesOrderActions';

class Dropdown extends Component {
    constructor(props) {
        super(props);
    }
    handleSelect = (select) => {
        this.inputSearch.value = select.n;
        this.inputSearchRest.innerHTML = select.n;
        this.handleBlur();
    }
    handleBlur = () => {
        this.dropdown.classList.remove("input-dropdown-focused");
    }
    handleFocus = (e) => {
        e.preventDefault();
        if(this.inputSearch.value !== this.props.autocomplete.query){
            this.handleChange();
        }
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
        this.inputSearch.value = "";
        this.handleChange();
    }
    handleKeyDown = (e) => {
        const {dispatch} = this.props;
        switch(e.key){
            case "ArrowDown":
                if(!!autocomplete.selected){
                    //next
                }else{
                    console.log(autocomplete.results);
                    dispatch(autocompleteSelect(autocomplete.results[0].id));
                }
                break;
            case "ArrowUp":
                console.log("Up");
                break;
            case "ArrowLeft":
                console.log("Up");
                break;
            case "ArrowRight":
                console.log("Up");
                break;
            case "Enter":
                this.handleSelect()
                break;
        }

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
                onKeyDown={this.handleKeyDown}
                tabIndex="0"
                onFocus={()=>this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
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
                    <div ref={c => this.inputSearchRest = c} className="input-toggled-rest" />

                    <div className="input-toggled-icon" tabIndex="0">
                        <i onClick={this.handleClear} className="icon-rounded icon-rounded-space">x</i>
                    </div>
                </div>
                <div className="clearfix" />
                <div className="input-dropdown-list">
                    <div className="input-dropdown-list-header">
                        {autocomplete.results.length > 0 ? "Are you looking for..." : "Recent lookups"}
                    </div>
                    <div ref={(c) => this.items = c}>
                        {autocomplete.results.length > 0 ? this.renderLookup() : this.renderRecent() }
                    </div>
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
            query: "",
            selected: null,
            results:[]
        }
    }


    return {
        autocomplete
    }
}

Dropdown = connect(mapStateToProps)(Dropdown)

export default Dropdown
