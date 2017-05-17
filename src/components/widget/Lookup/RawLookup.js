import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import LookupList from './LookupList';

import {
    autocompleteRequest,
    dropdownRequest
} from '../../../actions/GenericActions';

import {
    getItemsByProperty,
    openModal
} from '../../../actions/WindowActions';

class RawLookup extends Component {
    constructor(props) {
        super(props);

        this.state = {
            query: '',
            list: [],
            isInputEmpty: true,
            selected: null,
            loading: false,
            oldValue: '',
            isOpen: false,
            shouldBeFocused: true,
            validLocal: true
        }
    }

    componentDidMount() {
        const {selected, defaultValue} = this.props;

        this.handleValueChanged();

        if(selected) {
            this.inputSearch.value = selected[Object.keys(selected)[0]];
        }else{
            this.handleBlur(this.clearState);
        }

        
    }

    componentDidUpdate(prevProps) {
        this.handleValueChanged();

        const {autoFocus, defaultValue, fireClickOutside, handleInputEmptyStatus} = this.props;
        const {isInputEmpty, shouldBeFocused} = this.state;

        if(autoFocus && isInputEmpty && shouldBeFocused){
            this.inputSearch.focus();
            this.setState({
                shouldBeFocused: false
            });
        }

        defaultValue && prevProps.defaultValue !== defaultValue && handleInputEmptyStatus(false);

        if(fireClickOutside && prevProps.fireClickOutside !==  fireClickOutside) {
            if(defaultValue && defaultValue[Object.keys(defaultValue)[0]] !== this.inputSearch.value){
                this.inputSearch.value = defaultValue[Object.keys(defaultValue)[0]];
            }
        }
    }

    handleSelect = (select) => {
        const {
            onChange, filterWidget, parameterName, subentity, handleInputEmptyStatus, mainProperty,
            getNextDropdown, setNextProperty, children
        } = this.props;

        this.setState({
            selected: null
        }, () => {
            if(filterWidget) {
                onChange(children, null);
                onChange(parameterName, select);

                this.inputSearch.value = select[Object.keys(select)[0]];
                handleInputEmptyStatus(false);
                setNextProperty(mainProperty[0].field);

                this.handleBlur();
            } else {
                // handling selection when main is not set or set.
                onChange(children, null);
                onChange(mainProperty[0].field, select);

                this.inputSearch.value = select[Object.keys(select)[0]];
                handleInputEmptyStatus(false);
                setNextProperty(mainProperty[0].field);

                this.handleBlur();
                
            }
        });
    }

    handleAddNew = () => {
        const {
            dispatch, newRecordWindowId, newRecordCaption, filterWidget,
            parameterName, mainProperty
        } = this.props;

        dispatch(openModal(
            newRecordCaption, newRecordWindowId, 'window', null, null, null,
            null, null, 'NEW',
            filterWidget ? parameterName : mainProperty[0].field
        ));
    }

    handleBlur = (callback) => {
        this.setState({
            isOpen: false
        }, () => {
            if(callback){
                callback();
            }
        });
    }

    handleFocus = () => {
        this.setState({
            isOpen: true
        })
    }

    handleChange = (handleChangeOnFocus) => {
        const {
            dispatch, recent, windowType, dataId, filterWidget, parameterName,
            tabId, rowId, entity, subentity, subentityId, viewId, mainProperty
        } = this.props;

        if(this.inputSearch.value != ''){

            this.setState({
                isInputEmpty: false,
                loading: true,
                query: this.inputSearch.value,
                isOpen: true
            });

            dispatch(autocompleteRequest(
                windowType,
                (filterWidget ? parameterName : mainProperty[0].field),
                this.inputSearch.value,
                (filterWidget ? viewId : dataId), tabId, rowId, entity,
                subentity, subentityId
            )).then((response)=>{
                this.setState({
                    list: response.data.values,
                    loading: false,
                    validLocal: response.data.values.length === 0 &&
                                handleChangeOnFocus!==true ? false : true
                });
            });

        }else{
            this.setState({
                isInputEmpty: true,
                query: this.inputSearch.value,
                list: recent
            });
        }
    }

    clearState = () => {
        this.setState({
            list: [],
            isInputEmpty: true,
            selected: null,
            loading: false,
            query: ''
        });
    }

    handleKeyDown = (e) => {
        const {listenOnKeys, listenOnKeysFalse} = this.props;
        const {selected, list, query} = this.state;

        //need for prevent fire event onKeyDown 'Enter' from TableItem
        listenOnKeys && listenOnKeysFalse && listenOnKeysFalse();

        switch(e.key){
            case 'ArrowDown':
                e.preventDefault();
                this.navigate();
                break;
            case 'ArrowUp':
                e.preventDefault();
                this.navigate(true);
                break;
            case 'ArrowLeft':
                e.preventDefault();
                this.handleChange();
                break;
            case 'Enter':
                e.preventDefault();
                if(selected === 'new'){
                    this.handleAddNew(query);
                }else if(selected != null){
                    this.handleSelect(list[selected]);
                }
                break;
            case 'Escape':
                e.preventDefault();
                this.handleBlur();
                break;
            case 'Tab':
                this.handleBlur();
                break;
        }
    }

    navigate = (reverse) => {
        const {selected, list} = this.state;

        if(list.length === 0){
            // Case of selecting row for creting new instance
            this.setState({
                selected: 'new'
            });
        }else{
            // Case of selecting regular list items
            if(typeof selected === 'number'){
                const selectTarget = selected + (reverse ? (-1) : (1));
                if (typeof list[selectTarget] != 'undefined') {
                    this.setState({
                        selected: selectTarget
                    });
                }
            }else if(typeof list[0] != 'undefined'){
                this.setState({
                    selected: 0
                });
            }
        }
    }

    handleValueChanged = () => {
        const {defaultValue, filterWidget} = this.props;
        const {oldValue, isInputEmpty} = this.state;

        if(!filterWidget && !!defaultValue && this.inputSearch) {
            const init = defaultValue;
            const inputValue = init[Object.keys(init)[0]];

            if(inputValue !== oldValue){
                this.inputSearch.value = inputValue;

                this.setState({
                    oldValue: inputValue,
                    isInputEmpty: false,
                    validLocal: true,
                    list: [init]
                });
            } else if(isInputEmpty){
                this.setState({
                    isInputEmpty: false,
                    list: [init]
                });
            }

        } else if(oldValue && !defaultValue && this.inputSearch) {
            const inputEmptyValue = defaultValue;

            if(inputEmptyValue !== oldValue){
                this.inputSearch.value = inputEmptyValue;
                this.setState({
                    oldValue: inputEmptyValue,
                    isInputEmpty: true
                });
            }
        }
    }

    render() {
        const { handleAddNew, onClickOutside, disableOnClickOutside, isModal, rank, updated, filterWidget, mandatory, validStatus, align,
        creatingNewDisabled, newRecordCaption, placeholder, readonly, disabled, tabIndex, item, mainProperty} = this.props;

        const {
            propertiesCopy, isInputEmpty, list, query, loading, selected,
            isOpen, validLocal
        } = this.state;

        return (
        <div 
            onKeyDown={this.handleKeyDown}
            className={"raw-lookup-wrapper raw-lookup-wrapper-bcg "+
            (disabled ? 'raw-lookup-disabled':'')
        }  
                                
        >
            <div className={
                    'input-dropdown input-block' 
                    
                }>
                    <div className={
                        'input-editable ' +
                        (align ? 'text-xs-' + align + ' ' : '')
                    }>
                       
                            <input
                                type="text"
                                className="input-field js-input-field font-weight-semibold"
                                onChange={this.handleChange}
                                onFocus={this.handleFocus}
                                ref={(c) => this.inputSearch = c}
                                placeholder={placeholder}
                                disabled={readonly && !disabled}
                                tabIndex={tabIndex}
                                
                            />

                    </div>

                    
                    
                </div>
                {   isOpen && !isInputEmpty &&
                    <LookupList
                        selected={selected}
                        list={list}
                        loading={loading}
                        handleSelect={this.handleSelect}
                        handleAddNew={this.handleAddNew}
                        isInputEmpty={isInputEmpty}
                        onClickOutside={this.handleBlur}
                        disableOnClickOutside={!isOpen}
                        query={query}
                        creatingNewDisabled={isModal}
                        newRecordCaption={newRecordCaption}
                    />
                }

            </div>
            
        )
    }
}

RawLookup.propTypes = {
    dispatch: PropTypes.func.isRequired
}

RawLookup = connect()(RawLookup)

export default RawLookup
