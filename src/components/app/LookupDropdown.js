import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    autocomplete,
    autocompleteRequest,
    autocompleteSuccess,
    dropdownRequest,
    getPropertyValue,
} from '../../actions/SalesOrderActions';

class LookupDropdown extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isInputEmpty: true,
            selected: null,
            model: null,
            property: ""
        }

    }
    componentDidMount() {
        const {defaultValue} = this.props;
        if(defaultValue){
            this.handleSelect(this.props.defaultValue);
        }
    }
    handleSelect = (select) => {
        const {
            dispatch,
            properties,
            autocomplete,
            onObjectChange,
            onPropertyChange,
            dataId
        } = this.props;

        //removing selection
        this.setState({selected: null});
        let propertiesCopy = Object.assign([], properties);

        //
        // Handling selection when main is not set or set.
        //
        if(this.state.property === ""){
            this.inputSearch.value = select[Object.keys(select)[0]];
            //call for more properties
            //mocked properties for testing
            // - first will generate choice dropdown
            // - second should be chosen automatically
            select.properties = {};
            let batchArray = [];
            if(propertiesCopy.length > 1){

                propertiesCopy.shift();
                let batch = new Promise((resolve, reject) => {
                    propertiesCopy.map((item) => {
                        dispatch(dropdownRequest(143, item.field, dataId)).then((response)=>{
                            select.properties[item.field] = response.data;
                            batchArray.push('0');

                            if(batchArray.length === propertiesCopy.length){
                                resolve();
                            }
                        });
                    });
                });

                batch.then(()=>{
                    this.setState({model: select}, () => {
                        this.generatingPropsSelection();
                    });
                });

            }else{
                this.handleBlur();
            }

            // onObjectChange(select);
        } else {
            //
            // We cannot mutate state here, but we need to update
            // the properties in model, to update whole model in store
            //
            this.setState({
                model: Object.assign({}, this.state.model, {
                    properties: Object.keys(this.state.model.properties).reduce((previous, current) => {
                        if(current == this.state.property){
                            previous[current] = [select];
                        }else{
                            previous[current] = this.state.model.properties[current];
                        }
                        return previous;
                    }, {})
                })
            }, () => {
                this.generatingPropsSelection();
            });
            // onPropertyChange(this.state.model);
            this.handleBlur();
        }
    }

    generatingPropsSelection = () => {
        const {dispatch} = this.props;
        //
        // Chcecking properties model if there is some
        // unselected properties and handling further
        // selection
        //
        const modelProps = this.state.model.properties;
        const modelPropsKeys = Object.keys(modelProps);

        //iteration over rest of unselected props
        for(let i=0; i< modelPropsKeys.length; i++){
            if(modelProps[modelPropsKeys[i]].length === 1){
                // Selecting props that have no choice
                const noChoiceProp = modelProps[modelPropsKeys[i]][0];
                this.inputSearchRest.innerHTML += " " + noChoiceProp[Object.keys(noChoiceProp)[0]];
            }else if(modelProps[modelPropsKeys[i]].length > 1){
                // Generating list of props choice
                dispatch(autocompleteSuccess(modelProps[modelPropsKeys[i]]));
                this.setState({property: modelPropsKeys[i]});
                break;
            }else{
                this.handleBlur();
            }
        }
    }

    handleBlur = () => {
        this.dropdown.classList.remove("input-dropdown-focused");
        this.state.property = "";
    }

    handleFocus = (e) => {
        const {dispatch,recent} = this.props;
        e.preventDefault();
        this.setState({selected: null});
        if(this.inputSearch.value !== this.props.autocomplete.query){
            this.handleChange();
        }
        if(this.inputSearch.value === ""){
            dispatch(autocompleteSuccess(recent));
            this.setState({property: ""});
        }
        this.dropdown.classList.add("input-dropdown-focused");
    }
    handleChange = () => {
        const {dispatch, recent, windowType, properties, dataId} = this.props;
        this.inputSearchRest.innerHTML = "";
        this.dropdown.classList.add("input-dropdown-focused");
        dispatch(autocomplete(this.inputSearch.value));
        this.setState({selected: null});
        this.setState({property: ""});

        if(this.inputSearch.value != ""){
            dispatch(autocompleteRequest(windowType, properties[0].field, this.inputSearch.value, dataId));
            this.setState({isInputEmpty: false});
        }else{
            this.setState({isInputEmpty: true});
            dispatch(autocompleteSuccess(recent));
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
                this.handleChange();
                break;
            case "Enter":
                e.preventDefault();
                if(this.state.selected != null){
                    this.handleSelect(autocomplete.results[this.state.selected]);
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

        if(this.state.selected != null){
            const selectTarget = this.state.selected + (reverse ? (-1) : (1));
            if(typeof autocomplete.results[selectTarget] != "undefined"){
                this.setState({selected: selectTarget});
            }
        }else if(typeof autocomplete.results[0] != "undefined"){
            this.setState({selected: 0})
        }
    }

    renderLookup = () => {
        const {autocomplete} = this.props;
        return autocomplete.results.map((item, index) => this.getDropdownComponent(index, item) );
    }

    getDropdownComponent = (index, item) => {
        const name = item[Object.keys(item)[0]];
        const key = Object.keys(item)[0];
        return (
            <div
                key={key}
                className={"input-dropdown-list-option " + (this.state.selected == key ? 'input-dropdown-list-option-key-on' : "") }
                onClick={() => this.handleSelect(item)}
            >
                <p className="input-dropdown-item-title">{name}</p>
            </div>
        )
    }
    render() {
        const {autocomplete, rank} = this.props;
        return (
            <div
                onKeyDown={this.handleKeyDown}
                tabIndex="0"
                onFocus={()=>this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                onBlur={this.handleBlur}
                className={"input-dropdown-container"}
            >
                <div className={"input-dropdown input-block input-" + (rank ? rank : "primary")}>
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
                            <i className="meta-icon-preview" />
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
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        autocomplete,
    } = salesOrderStateHandler || {
        autocomplete: {
            query: "",
            results:[]
        }
    }
    return {
        autocomplete
    }
}

LookupDropdown = connect(mapStateToProps)(LookupDropdown)

export default LookupDropdown
