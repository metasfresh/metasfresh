import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

import {
    autocompleteRequest,
    dropdownRequest,
} from '../../actions/AppActions';

class Lookup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            query: "",
            list: [],
            isInputEmpty: true,
            selected: null,
            model: null,
            property: "",
            properties: {},
            loading: false
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
            onChange,
            dataId,
            fields
        } = this.props;

        //removing selection
        this.setState({selected: null});
        let propertiesCopy = this.getItemsByProperty(properties, "source", "list")
        let mainProperty = this.getItemsByProperty(properties, "source", "lookup")

        //
        // Handling selection when main is not set or set.
        //
        if(this.state.property === ""){
            this.inputSearch.value = select && select[Object.keys(select)[0]];
            onChange(mainProperty[0].field, select);
            //call for more properties
            // - first will generate choice dropdown
            // - second should be chosen automatically

            let batchArray = [];
            if(propertiesCopy.length > 1){
                let batch = new Promise((resolve, reject) => {
                    propertiesCopy.map((item) => {
                        dispatch(dropdownRequest(143, item.field, dataId)).then((response)=>{
                            this.setState({
                                properties: {
                                    [item.field]: response.data
                                }
                            });
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
        } else {
            //
            // We cannot mutate state here, but we need to update
            // the properties in model, to update whole model in store
            //
            this.setState({
                properties: Object.keys(this.state.properties).reduce((previous, current) => {
                    if(current == this.state.property){
                        previous[current] = [select];
                    }else{
                        previous[current] = this.state.properties[current];
                    }
                    return previous;
                }, {})
            }, () => {
                this.generatingPropsSelection();
            });
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
        const modelProps = this.state.properties;
        const modelPropsKeys = Object.keys(modelProps);

        //iteration over rest of unselected props
        for(let i=0; i < modelPropsKeys.length; i++){
            if(modelProps[modelPropsKeys[i]].length === 1){
                // Selecting props that have no choice
                const noChoiceProp = modelProps[modelPropsKeys[i]][0];
                this.inputSearchRest.innerHTML += " " + noChoiceProp[Object.keys(noChoiceProp)[0]];
            }else if(modelProps[modelPropsKeys[i]].length > 1){
                // Generating list of props choice
                this.setState({
                    list: modelProps[modelPropsKeys[i]],
                    property: modelPropsKeys[i]
                });
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
        if(this.inputSearch.value !== this.state.query){
            this.handleChange();
        }
        if(this.inputSearch.value === ""){
            this.setState({property: "", list: recent});
        }
        this.dropdown.classList.add("input-dropdown-focused");
    }
    handleChange = () => {
        const {dispatch, recent, windowType, properties, dataId} = this.props;
        this.inputSearchRest.innerHTML = "";
        this.dropdown.classList.add("input-dropdown-focused");
        this.setState({selected: null, property: ""});

        const lookupProps = this.getItemsByProperty(properties, "source", "lookup")[0];

        if(this.inputSearch.value != ""){
            this.setState({isInputEmpty: false, loading: true});

            dispatch(autocompleteRequest(windowType, lookupProps.field, this.inputSearch.value, dataId)).then((response)=>{
                this.setState({list: response.data, loading: false});
            })
        }else{
            this.setState({
                isInputEmpty: true,
                list: recent});
        }
    }

    handleClear = (e) => {
        const {onChange} = this.props;
        e.preventDefault();
        this.inputSearch.value = "";
        this.handleChange();
        this.handleSelect(null);
    }
    handleKeyDown = (e) => {
        const {dispatch} = this.props;
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
                    this.handleSelect(this.state.list[this.state.selected]);
                }
                break;
            case "Escape":
                e.preventDefault();
                this.handleBlur();
                break;
        }
    }
    navigate = (reverse) => {
        if(this.state.selected != null){
            const selectTarget = this.state.selected + (reverse ? (-1) : (1));
            if (typeof this.state.list[selectTarget] != "undefined") {
                this.setState({selected: selectTarget});
            }
        }else if(typeof this.state.list[0] != "undefined"){
            this.setState({selected: 0})
        }
    }
    getItemsByProperty = (arr, prop, value) => {
        let ret = [];
        arr.map((item, index) => {
            if(item[prop] === value){
                ret.push(item);
            }
        });
        return ret;
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

    renderLookup = () => {
        return this.state.list.map((item, index) => this.getDropdownComponent(index, item) );
    }

    render() {
        const {rank, readonly} = this.props;
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
                            disabled={readonly}
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
                        {this.state.loading === false && (
                            (this.state.list.length > 0 ) ?
                                (this.state.query.length !== 0 ? "Are you looking for..." : "Recent lookups") :
                                "There's no matching items."
                            )
                        }
                        {(this.state.loading && this.state.list.length === 0) && (
                            <div className="input-dropdown-list-header">
                                <ReactCSSTransitionGroup transitionName="rotate" transitionEnterTimeout={1000} transitionLeaveTimeout={1000}>
                                    <div className="rotate icon-rotate">
                                        <i className="meta-icon-settings"/>
                                    </div>
                                </ReactCSSTransitionGroup>
                            </div>
                        )}
                    </div>
                    <div ref={(c) => this.items = c}>
                        {this.renderLookup()}
                    </div>
                </div>
            </div>
        )
    }
}


Lookup.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    return {
    }
}

Lookup = connect(mapStateToProps)(Lookup)

export default Lookup
