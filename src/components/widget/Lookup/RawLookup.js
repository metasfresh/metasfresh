import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import LookupList from './LookupList';

import {
    autocompleteRequest
} from '../../../actions/GenericActions';

import {
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

        if(defaultValue){
            this.inputSearch.value =
                    defaultValue[Object.keys(defaultValue)[0]];
        }
    }

    componentDidUpdate(prevProps) {
        this.handleValueChanged();

        const {
            autoFocus, defaultValue, fireClickOutside, handleInputEmptyStatus,
            filterWidget, initialFocus, lookupEmpty, localClearing,
            resetLocalClearing, fireDropdownList
        } = this.props;

        if(localClearing && !defaultValue) {
            this.inputSearch.value ='';
            resetLocalClearing();
        }

        const {shouldBeFocused} = this.state;
        if(autoFocus && !this.inputSearch.value && shouldBeFocused){
            this.inputSearch.focus();
            this.setState({
                shouldBeFocused: false
            });
        }

        if(initialFocus && !this.inputSearch.value){
            this.inputSearch.focus();
        }

        defaultValue && prevProps.defaultValue !== defaultValue &&
        handleInputEmptyStatus(false);

        if(fireClickOutside && prevProps.fireClickOutside !== fireClickOutside)
         {
            if(
                defaultValue && defaultValue[Object.keys(defaultValue)[0]] !==
                this.inputSearch.value
            ){
                this.inputSearch.value =
                    defaultValue[Object.keys(defaultValue)[0]];
            }
        }

        if(filterWidget && defaultValue === null && lookupEmpty){
            this.inputSearch.value = defaultValue;
        }

        if(fireDropdownList && prevProps.fireDropdownList !==
            fireDropdownList) {
            this.handleChange('', true);
        }
    }

    handleSelect = (select) => {
        const {
            onChange, handleInputEmptyStatus, mainProperty, setNextProperty,
            filterWidget, subentity
        } = this.props;

        this.setState({
            selected: null
        }, () => {

            if(filterWidget){
                const promise = onChange(mainProperty[0].parameterName, select);

                if(promise) {
                    promise.then(()=> {
                        setNextProperty(mainProperty[0].parameterName);
                    })
                } else {
                    setNextProperty(mainProperty[0].parameterName);
                }

            } else {
                if(subentity === 'quickInput'){
                    onChange(
                        mainProperty[0].field, select,
                        () => setNextProperty(mainProperty[0].field)
                    );
                } else {
                    const promise = onChange(mainProperty[0].field, select);
                    if(promise){
                        promise.then(()=> {
                            setNextProperty(mainProperty[0].field);
                        })
                    } else {
                        setNextProperty(mainProperty[0].field);
                    }
                }

            }

            this.inputSearch.value = select[Object.keys(select)[0]];
            handleInputEmptyStatus(false);
            this.handleBlur();
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

    handleChange = (handleChangeOnFocus, allowEmpty) => {
        const {
            recent, windowType, dataId, filterWidget, parameterName,
            tabId, rowId, entity, subentity, subentityId, viewId, mainProperty,
            handleInputEmptyStatus
        } = this.props;

        if(this.inputSearch.value != '' || allowEmpty){

            !allowEmpty && handleInputEmptyStatus(false);

            this.setState({
                isInputEmpty: false,
                loading: true,
                query: this.inputSearch.value,
                isOpen: true
            });

            autocompleteRequest(
                windowType,
                (filterWidget ? parameterName : mainProperty[0].field),
                this.inputSearch.value,
                (filterWidget ? viewId : dataId), tabId, rowId, entity,
                subentity, subentityId
            ).then((response)=>{
                this.setState({
                    list: response.data.values,
                    loading: false,
                    selected: 0,
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

            handleInputEmptyStatus(true);
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
        const { isModal, align, newRecordCaption,
                placeholder, readonly, disabled, tabIndex
            } = this.props;

        const {
            isInputEmpty, list, query, loading, selected,
            isOpen
        } = this.state;

        return (
        <div
            onKeyDown={this.handleKeyDown}
            className={'raw-lookup-wrapper raw-lookup-wrapper-bcg'+
            (disabled ? ' raw-lookup-disabled':'') +
            (readonly ? ' input-disabled':'')
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
