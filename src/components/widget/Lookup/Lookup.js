import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import update from 'react-addons-update';

import {
    autocompleteRequest,
    dropdownRequest
} from '../../../actions/AppActions';

import {
    getItemsByProperty,
    openModal
} from '../../../actions/WindowActions';

import LookupList from './LookupList';

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
            properts: {},
            loading: false,
            propertiesCopy: getItemsByProperty(this.props.properties, "source", "list"),
            mainProperty: getItemsByProperty(this.props.properties, "source", "lookup"),
            oldValue: '',
            isOpen: false
        }
    }

    componentDidMount() {
        const {selected, filterWidget} = this.props;

        this.handleValueChanged();

        if(filterWidget || selected) {
            this.inputSearch.value = selected[Object.keys(selected)[0]];
        }else{
            this.handleClear();
        }
    }

    componentDidUpdate() {
        this.handleValueChanged();
    }

    handleSelect = (select) => {
        const {
            dispatch, properties, onChange, dataId, fields, filterWidget,
            parameterName, setSelectedItem, windowType
        } = this.props;

        const {
            mainProperty, propertiesCopy, property
        } = this.state;

        // removing selection
        this.setState(Object.assign({}, this.state, {
            selected: null
        }));

        if(filterWidget) {
            onChange(parameterName, select);
            setSelectedItem(select[Object.keys(select)[0]]);

            this.inputSearch.value = select[Object.keys(select)[0]];

            this.handleBlur();
        } else {
            // handling selection when main is not set or set.

            if(property === "") {
                const promise = onChange(mainProperty[0].field, select);

                this.inputSearch.value = select[Object.keys(select)[0]];

                // handle case when there is no call to Api
                // by the cached value
                if(!promise){
                    this.getAllDropdowns();
                }else{
                    promise.then(() => {
                        this.getAllDropdowns();
                    }
                )}
            } else {
                onChange(property, select);

                this.setState((prevState) => update(this.state, {
                    properts: {$apply: item => {
                        delete item[prevState.property];
                        return item;
                    }},
                    list: {$set: []},
                    property: {$set: ""}
                }), () => {
                    this.generatingPropsSelection();
                });
            }
        }
    }

    getAllDropdowns = () => {
        const {
            dispatch, windowType, item, dataId, newProps, select, tabId, rowId,
            entity, subentity, subentityId
        } = this.props;

        const {
            propertiesCopy
        } = this.state;


        // call for more properties
        if(propertiesCopy.length > 0){

            const batchArray = propertiesCopy.map((item) =>
                dispatch(dropdownRequest(
                    windowType, item.field, dataId, tabId, rowId, entity,
                    subentity, subentityId
                ))
            );

            Promise.all(batchArray).then(props => {
                const newProps = {};
                props.map((prop, index) => {
                    newProps[propertiesCopy[index].field] = prop.data.values;
                });

                this.setState(Object.assign({}, this.state, {
                    properts: newProps,
                    model: select
                }), () => {
                    this.generatingPropsSelection();
                });
            });
        }else{

            this.handleBlur();
        }
    }

    generatingPropsSelection = () => {
        const {dispatch, onChange} = this.props;
        const {properts} = this.state;
        const propertiesKeys = Object.keys(properts);

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

            if(properts[propertiesKeys[i]].length > 1){
                // Generating list of props choice
                this.setState(Object.assign({}, this.state, {
                    list: properts[propertiesKeys[i]],
                    property: propertiesKeys[i]
                }));
                break;
            }else{
                onChange(propertiesKeys[i], properts[propertiesKeys[i]][0]);
                this.handleBlur();
            }
        }
    }

    handleAddNew = () => {
        const {query} = this.state;
        const {dispatch, windowType} = this.props;

        //TODO: Waiting for windowType from API for the new instance of entity
        dispatch(openModal("Add new", windowType, "window"));
    }

    handleBlur = () => {
        this.setState(Object.assign({}, this.state, {
            isOpen: false
        }))
    }

    handleFocus = () => {
        const {isInputEmpty} = this.state;
        this.setState(Object.assign({}, this.state, {
            isOpen: true
        }))

        if(!isInputEmpty){
            this.handleChange();
        }
    }

    handleChange = () => {
        const {
            dispatch, recent, windowType, properties, dataId, filterWidget,
            filterId, parameterName, tabId, rowId, entity,subentity, subentityId,
            viewId
        } = this.props;

        const {mainProperty} = this.state;

        if(this.inputSearch.value != ""){

            this.setState(Object.assign({}, this.state, {
                isInputEmpty: false,
                loading: true,
                query: this.inputSearch.value,
                isOpen: true
            }));

            dispatch(autocompleteRequest(
                windowType, (filterWidget ? parameterName : mainProperty[0].field), this.inputSearch.value,
                (filterWidget ? viewId : dataId), tabId, rowId, entity, subentity, subentityId
            )).then((response)=>{
                this.setState(Object.assign({}, this.state, {
                    list: response.data.values,
                    loading: false
                }));
            });

        }else{
            this.setState(Object.assign({}, this.state, {
                isInputEmpty: true,
                query: this.inputSearch.value,
                list: recent
            }));
        }
    }

    handleClear = (e) => {
        const {onChange, properties} = this.props;
        e && e.preventDefault();
        this.inputSearch.value = "";

        properties.map(item => {
            onChange(item.field, null);
        });

        this.setState(Object.assign({}, this.state, {
            list: [],
            isInputEmpty: true,
            selected: null,
            model: null,
            property: "",
            loading: false
        }));

        this.handleBlur();
    }

    handleKeyDown = (e) => {
        const {selected, list, query} = this.state;
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
                if(selected === "new"){
                    this.handleAddNew(query);
                }else if(selected != null){
                    this.handleSelect(list[selected]);
                }
                break;
            case "Escape":
                e.preventDefault();
                this.handleBlur();
                break;
            case "Tab":
                this.handleBlur();
                break;
        }
    }

    navigate = (reverse) => {
        const {selected, list} = this.state;

        if(list.length === 0){
            // Case of selecting row for creting new instance
            this.setState(Object.assign({}, this.state, {
                selected: "new"
            }));
        }else{
            // Case of selecting regular list items
            if(typeof selected === "number"){
                const selectTarget = selected + (reverse ? (-1) : (1));
                if (typeof list[selectTarget] != "undefined") {
                    this.setState(Object.assign({}, this.state, {
                        selected: selectTarget
                    }));
                }
            }else if(typeof list[0] != "undefined"){
                this.setState(Object.assign({}, this.state, {
                    selected: 0
                }));
            }
        }
    }

    handleValueChanged = () => {
        const {defaultValue, filterWidget, selected} = this.props;
        const {oldValue} = this.state;

        if(!filterWidget && !!defaultValue[0].value && this.inputSearch) {
            const init = defaultValue[0].value;
            const inputValue = init[Object.keys(init)[0]];

            if(inputValue !== oldValue){
                this.inputSearch.value = inputValue;

                this.setState(Object.assign({}, this.state, {
                    oldValue: inputValue,
                    isInputEmpty: false
                }));
            }
        }
    }

    render() {
        const {
            rank, readonly, properties, defaultValue, placeholder, align, isModal,
            updated, oldValue, filterWidget, mandatory, rowId, tabIndex
        } = this.props;

        const {
            propertiesCopy, isInputEmpty, list, query, loading, selected, isOpen
        } = this.state;

        return (
            <div
                onKeyDown={this.handleKeyDown}
                onClick={()=> this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                className={
                    "input-dropdown-container " +
                    (isOpen ? "input-focused " : "") +
                    (readonly ? "input-disabled " : "") +
                    (rowId ? "input-dropdown-container-static " : "") +
                    ((rowId && !isModal)? "input-table " : "")
                }
            >
                <div className={
                    "input-dropdown input-block input-" + (rank ? rank : "primary") +
                    (updated ? " pulse-on" : " pulse-off") +
                    (filterWidget ? " input-full" : "") +
                    (mandatory && isInputEmpty ? " input-mandatory " : "")
                }>
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
                            tabIndex={tabIndex}
                        />
                    </div>
                    {(propertiesCopy.length > 0) && <div className="input-rest">
                        {propertiesCopy.map((item, index) => {
                            const objectValue = getItemsByProperty(defaultValue, "field", item.field)[0].value;
                            return (!!objectValue && <span key={index}>{objectValue[Object.keys(objectValue)[0]]}</span>)
                        })}
                    </div>}
                    {isInputEmpty ?
                        <div className="input-icon input-icon-lg">
                            <i className="meta-icon-preview" />
                        </div> :
                        <div className="input-icon input-icon-lg">
                            {!readonly && <i onClick={this.handleClear} className="meta-icon-close-alt"/>}
                        </div>
                    }
                </div>
                {isOpen &&
                    <LookupList
                        selected={selected}
                        list={list}
                        loading={loading}
                        handleSelect={this.handleSelect}
                        handleAddNew={this.handleAddNew}
                        isInputEmpty={isInputEmpty}
                        onClickOutside={this.handleBlur}
                        disableClickOutside={!isOpen}
                        query={query}
                    />
                }
            </div>
        )
    }
}


Lookup.propTypes = {
    dispatch: PropTypes.func.isRequired
}

Lookup = connect()(Lookup)

export default Lookup
