import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import update from 'react-addons-update';

import {
    autocompleteRequest,
    dropdownRequest,
    filterAutocompleteRequest
} from '../../actions/AppActions';

import {
    getItemsByProperty
} from '../../actions/WindowActions';

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
            loading: false,
            propertiesCopy: getItemsByProperty(this.props.properties, "source", "list"),
            mainProperty: getItemsByProperty(this.props.properties, "source", "lookup"),
            oldValue: ''

        }

    }

    componentDidMount() {
        this.handleValueChanged();
        const {selected, filterWidget} = this.props;

        if(filterWidget && selected) {
            this.inputSearch.value = selected[Object.keys(selected)[0]];
        }
        
    }

    componentDidUpdate() {
        this.handleValueChanged();
    }

    componentWillReceiveProps() {
        
    }

    handleClickOutside = () => {
        this.handleBlur();
    }

    handleSelect = (select) => {
        const {
            dispatch,
            properties,
            onChange,
            dataId,
            fields,
            filterWidget,
            parameterName,
            setSelectedItem
        } = this.props;

        const {
            mainProperty,
            propertiesCopy
        } = this.state;



        // removing selection
        this.setState(
            Object.assign({}, this.state, {
                selected: null
            })
        );
        if(filterWidget) {
            onChange(parameterName, select);
            setSelectedItem(select[Object.keys(select)[0]]);

            this.inputSearch.value = select[Object.keys(select)[0]];

            this.handleBlur();


        } else {
            // handling selection when main is not set or set.
            if(this.state.property === "") {
                const promise = onChange(mainProperty[0].field, select);
                promise && promise.then(() => {
                    this.inputSearch.value = select[Object.keys(select)[0]];
                    // call for more properties
                    let batchArray = [];
                    if(propertiesCopy.length > 0){

                        let batch = new Promise((resolve, reject) => {
                            propertiesCopy.map((item) => {
                                dispatch(dropdownRequest(143, item.field, dataId)).then((response)=>{

                                    this.setState(update(this.state, {
                                        properties: {
                                            [item.field]: {$set: response.data.values}
                                        }
                                    }), () => {
                                        batchArray.push('0');

                                        if(batchArray.length === propertiesCopy.length){
                                            resolve();
                                        }
                                    });

                                });
                            });
                        });

                        batch.then(()=>{
                            this.setState(Object.assign({}, this.state, {
                                model: select
                            }), () => {
                                this.generatingPropsSelection();
                            });
                        });
                    }else{
                        this.handleBlur();
                    }
                })
            } else {
                onChange(this.state.property, select);

                this.setState(
                    update(this.state, {
                        properties:  {$apply: item => {
                            delete item[this.state.property];
                            return item;
                        }}
                    }),
                    () => {
                        this.generatingPropsSelection();
                    });
            }

        }


    }

    generatingPropsSelection = () => {
        const {dispatch, onChange} = this.props;
        const {properties} = this.state;
        const propertiesKeys = Object.keys(properties);

        // Chcecking properties model if there is some
        // unselected properties and handling further
        // selection
        if(propertiesKeys.length === 0){
            this.setState(Object.assign({}, this.state, {
                property: ""
            }));

            this.handleBlur();
            return;
        }

        for(let i=0; i < propertiesKeys.length; i++){
            if(properties[propertiesKeys[i]].length > 1){
                // Generating list of props choice
                this.setState(Object.assign({}, this.state, {
                    list: properties[propertiesKeys[i]],
                    property: propertiesKeys[i]
                }));
                break;
            }else{
                this.handleBlur();
            }
        }
    }

    handleBlur = () => {
        this.dropdown.classList.remove("input-dropdown-focused");
    }

    handleFocus = () => {
        this.dropdown.classList.add("input-dropdown-focused");
    }

    handleChange = () => {
        const {dispatch, recent, windowType, properties, dataId, filterWidget, filterId, parameterName} = this.props;
        const {mainProperty} = this.state;

        this.dropdown.classList.add("input-dropdown-focused");

        if(this.inputSearch.value != ""){

            this.setState(Object.assign({}, this.state, {
                isInputEmpty: false,
                loading: true,
                query: this.inputSearch.value
            }));


            if(filterWidget){
                dispatch(filterAutocompleteRequest(windowType, filterId, parameterName,  this.inputSearch.value ))
                .then((response)=>{
                    this.setState(Object.assign({}, this.state, {
                        list: response.data.values,
                        loading: false
                    }));
                })
            }else {
                dispatch(autocompleteRequest(windowType, mainProperty[0].field, this.inputSearch.value, dataId))
                .then((response)=>{
                    this.setState(Object.assign({}, this.state, {
                        list: response.data.values,
                        loading: false
                    }));
                })
            }



        }else{

            this.setState(Object.assign({}, this.state, {
                isInputEmpty: true,
                list: recent
            }));
        }
    }

    handleClear = (e) => {
        const {onChange, properties} = this.props;
        e.preventDefault();
        this.inputSearch.value = "";

        properties.map(item => {
            onChange(item.field, "");
        })

        this.setState(Object.assign({}, this.state, {
            list: [],
            isInputEmpty: true,
            selected: null,
            model: null,
            property: "",
            loading: false,
            propertiesCopy: []
        }));

        this.handleBlur();
    }

    handleKeyDown = (e) => {
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
        if(this.state.selected !== null){
            const selectTarget = this.state.selected + (reverse ? (-1) : (1));
            if (typeof this.state.list[selectTarget] != "undefined") {
                this.setState(Object.assign({}, this.state, {
                    selected: selectTarget
                }));
            }
        }else if(typeof this.state.list[0] != "undefined"){
            this.setState(Object.assign({}, this.state, {
                selected: 0
            }))
        }
    }

    getDropdownComponent = (index, item) => {
        const name = item[Object.keys(item)[0]];
        const key = Object.keys(item)[0];
        return (
            <div
                key={key}
                className={"input-dropdown-list-option " + (this.state.selected === index ? 'input-dropdown-list-option-key-on' : "") }
                onClick={() => this.handleSelect(item)}
            >
                <p className="input-dropdown-item-title">{name}</p>
            </div>
        )
    }

    handleValueChanged = () => {

        const {defaultValue, filterWidget, selected} = this.props;
        const {oldValue} = this.state;

        if(!filterWidget) {
            if(!!defaultValue[0].value && this.inputSearch) {
                const init = defaultValue[0].value;
                let inputValue = init[Object.keys(init)[0]];
                if(inputValue !== oldValue){
                    this.inputSearch.value = inputValue
                    this.setState(Object.assign({}, this.state, {
                        oldValue: inputValue,
                        isInputEmpty: false
                    }));
                }
            }

        }

        
    }

    renderLookup = () => {
        return this.state.list.map((item, index) => this.getDropdownComponent(index, item) );
    }

    render() {
        const {rank, readonly, properties, defaultValue, placeholder, align, isModal, updated, selected, oldValue, filterWidget} = this.props;
        const {propertiesCopy,isInputEmpty} = this.state;

        return (
            <div
                onKeyDown={this.handleKeyDown}
                tabIndex="0"
                onFocus={()=>this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                className={"input-dropdown-container"}
            >
                <div className={"input-dropdown input-block input-" + (rank ? rank : "primary") + (updated ? " pulse-on" : " pulse-off") + (filterWidget ? " input-full" : "")}>
                    <div className={
                        "input-editable " +
                        (align ? "text-xs-" + align + " " : "") 
                    }>
                        <input
                            type="text"
                            className="input-field js-input-field font-weight-semibold"
                            onChange={this.handleChange}
                            onFocus={this.handleFocus}
                            ref={(c) => this.inputSearch = c}
                            placeholder={placeholder}
                            disabled={readonly}
                        />
                    </div>
                    <div className="input-rest">
                        {propertiesCopy.map((item, index) => {
                            const objectValue = getItemsByProperty(defaultValue, "field", item.field)[0].value;
                            return (!!objectValue && <span key={index}>{objectValue[Object.keys(objectValue)[0]]}</span>)
                        })}
                    </div>
                    {isInputEmpty ?
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
                        {(this.state.list.length > 0 ) ?
                                (this.state.query.length !== 0 ? "Are you looking for..." : "Recent lookups") :
                                "There's no matching items."
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
}

Lookup = connect()(onClickOutside(Lookup))

export default Lookup
