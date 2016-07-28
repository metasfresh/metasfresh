import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import DropdownPartnerItem from './DropdownPartnerItem';
import {
    autocomplete,
    autocompleteRequest,
    autocompleteSelect,
    autocompleteSuccess,
    getPropertyValue
} from '../../actions/SalesOrderActions';

class Dropdown extends Component {
    constructor(props) {
        super(props);
    }
    handleSelect = (select) => {
        const {dispatch, properties} = this.props;
        this.inputSearch.value = select.n;
        properties.splice(0,1);
        for (let i=0; i<properties.length; i++) {
            //dispatch get value
            //if value.length 1 then
            // this.inputSearchRest.innerHTML = select.n;
            //if value.length more
            //render list with localisation
        }
        this.handleBlur();
    }
    handleBlur = () => {
        this.dropdown.classList.remove("input-dropdown-focused");
    }
    handleFocus = (e) => {
        const {dispatch,recent} = this.props;
        e.preventDefault();
        dispatch(autocompleteSelect(null));
        if(this.inputSearch.value !== this.props.autocomplete.query){
            this.handleChange();
        }
        if(this.inputSearch.value === ""){
            dispatch(autocompleteSuccess(recent));
        }
        this.dropdown.classList.add("input-dropdown-focused");
    }
    handleChange = (e) => {
        const {dispatch, recent} = this.props;

        this.inputSearchRest.innerHTML = "";
        this.dropdown.classList.add("input-dropdown-focused");
        dispatch(autocomplete(this.inputSearch.value));
        dispatch(autocompleteSelect(null));

        if(this.inputSearch.value == ""){
            dispatch(autocompleteSuccess(recent));
        }else{
            dispatch(autocompleteRequest(this.inputSearch.value, this.props.properties[0]));
        }
    }
    handleClear = (e) => {
        e.preventDefault();
        this.inputSearch.value = "";
        this.handleChange();
    }
    handleKeyDown = (e) => {
        const {dispatch, autocomplete} = this.props;
        switch(e.key){
            case "ArrowDown":
                e.preventDefault();
                this.navigate();
                break;
            case "ArrowUp":
                e.preventDefault();
                this.navigate(true);
                break;
            case "ArrowLeft":
                e.preventDefault();
                break;
            case "Enter":
                e.preventDefault();
                if(autocomplete.selected != null){
                    this.handleSelect(autocomplete.results[autocomplete.selected]);
                }
                break;
            case "Escape":
                e.preventDefault();
                this.handleBlur();
                break;
        }
    }
    navigate = (reverse) => {
        const {dispatch, autocomplete} = this.props;

        if(autocomplete.selected != null){
            const selectTarget = autocomplete.selected + (reverse ? (-1) : (1));
            if(typeof autocomplete.results[selectTarget] != "undefined"){
                dispatch(autocompleteSelect(selectTarget));
            }
        }else if(typeof autocomplete.results[0] != "undefined"){
            dispatch(autocompleteSelect(0));
        }
    }
    renderLookup = () => {
        const {autocomplete} = this.props;
        return autocomplete.results.map((partner, index) => this.getDropdownComponent(index, partner) );
    }
    getDropdownComponent = (index, item) => {
        return <DropdownPartnerItem key={item.id} itemIndex={index} data={item} onClick={this.handleSelect}/>
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
                        {autocomplete.results.length > 0 ?
                            (autocomplete.query.length !== 0 ? "Are you looking for..." : "Recent lookups") :
                            "There's no matching items."
                        }
                    </div>
                    <div ref={(c) => this.items = c}>
                        {this.renderLookup()}
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
