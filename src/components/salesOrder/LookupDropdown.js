import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import DropdownPartnerItem from './DropdownPartnerItem';
import {
    autocomplete,
    autocompleteRequest,
    autocompleteSelect,
    autocompleteSuccess,
    getPropertyValue,
    purchaserChanged,
    purchaserPropertyChanged
} from '../../actions/SalesOrderActions';

class LookupDropdown extends Component {
    constructor(props) {
        super(props);
        this.state = {isInputEmpty: true}
    }
    handleSelect = (select) => {
        const {dispatch, properties, purchaser, autocomplete} = this.props;
        //removing selection
        dispatch(autocompleteSelect(null));
        //
        // Handling selection when main is not set or set.
        //
        if(autocomplete.property === ""){
            //call for more properties
            //mocked properties for testing
            // - first will generate choice dropdown
            // - second should be chosen automatically
            select.properties = {
                property: [{id: '123', n: "opt1prop1"}, {id: '1234', n: "opt2prop2"}],
                property2: [{id: '1231', n: "opt1prop2"}]
            };
            this.inputSearch.value = select.n;
            purchaser.purchaser = select;
            dispatch(purchaserChanged(select));
        } else {
            purchaser.purchaser.properties[autocomplete.property] = [select];
            dispatch(purchaserPropertyChanged(purchaser.purchaser.properties));
            this.handleBlur();
        }

        //
        // Chcecking properies model if there is some
        // unselected properties and handling further
        // selection
        //
        const purPro = purchaser.purchaser.properties;
        const purProKeys = Object.keys(purPro);

        //iteration over rest of unselected props
        for(let i=0; i< purProKeys.length; i++){
            if(purPro[purProKeys[i]].length === 1){
                // Selecting props that have no choice
                this.inputSearchRest.innerHTML += " " + purPro[purProKeys[i]][0].n;
            }else if(purPro[purProKeys[i]].length > 1){
                // Generating list of props choice
                dispatch(autocompleteSuccess(purPro[purProKeys[i]], purProKeys[i]));
                break;
            }
        }

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
            dispatch(autocompleteSuccess(recent, ""));
        }
        this.dropdown.classList.add("input-dropdown-focused");
    }
    handleChange = () => {
        const {dispatch, recent} = this.props;
        this.inputSearchRest.innerHTML = "";
        this.dropdown.classList.add("input-dropdown-focused");
        dispatch(autocomplete(this.inputSearch.value));
        dispatch(autocompleteSelect(null));

        if(this.inputSearch.value != ""){
            dispatch(autocompleteRequest(this.inputSearch.value, this.props.properties[0]));
            this.setState({isInputEmpty: false});
        }else{
            this.setState({isInputEmpty: true});
            dispatch(autocompleteSuccess(recent, ""));
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
        const {autocomplete, className} = this.props;
        return (
            <div
                onKeyDown={this.handleKeyDown}
                tabIndex="0"
                onFocus={()=>this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                onBlur={this.handleBlur}
                className={"input-dropdown-container"}
            >
                <div className={"input-dropdown input-block input-" + className}>
                    <div className="input-editable">
                        <input
                            type="text"
                            className="input-field font-weight-bold"
                            onFocus={this.handleFocus}
                            onChange={this.handleChange}
                            ref={(c) => this.inputSearch = c}
                            placeholder="(none)"
                        />
                    </div>
                    <div ref={c => this.inputSearchRest = c} className="input-rest" />

                    {this.state.isInputEmpty ?
                        <div className="input-icon input-icon-lg">
                            <i className="meta-icon-preview-1" />
                        </div> :
                        <div className="input-icon input-icon-lg" tabIndex="0">
                            <i onClick={this.handleClear} className="meta-icon-close-alt"/>
                        </div>
                    }
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


LookupDropdown.propTypes = {
    autocomplete: PropTypes.object.isRequired,
    purchaser: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        autocomplete,
        purchaser
    } = salesOrderStateHandler || {
        autocomplete: {
            query: "",
            selected: null,
            results:[],
            property: ""
        },
        purchaser: null
    }
    return {
        autocomplete,
        purchaser
    }
}

LookupDropdown = connect(mapStateToProps)(LookupDropdown)

export default LookupDropdown
