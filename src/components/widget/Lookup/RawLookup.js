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
        const { selected, defaultValue, initialFocus } = this.props;

        this.handleValueChanged();

        if (selected) {
            let selectedKey = Object.keys(selected)[0];
            this.inputSearch.value = selected[selectedKey];
        } else {
            this.handleBlur(this.clearState);
        }

        if (defaultValue) {
            let defaultKey = Object.keys(defaultValue)[0];
            this.inputSearch.value = defaultValue[defaultKey];
        }

        if (initialFocus && !this.inputSearch.value) {
            this.inputSearch.focus();
        }
    }

    componentDidUpdate(prevProps) {
        this.handleValueChanged();

        const {
            autoFocus, defaultValue, fireClickOutside, handleInputEmptyStatus,
            filterWidget, lookupEmpty, localClearing, resetLocalClearing, fireDropdownList
        } = this.props;

        const { shouldBeFocused } = this.state;

        if (localClearing && !defaultValue) {
            this.inputSearch.value ='';
            resetLocalClearing();
        }

        if (autoFocus && !this.inputSearch.value && shouldBeFocused) {
            this.inputSearch.focus();
            this.setState({
                shouldBeFocused: false
            });
        }

        defaultValue && prevProps.defaultValue !== defaultValue &&
        handleInputEmptyStatus(false);

        if (fireClickOutside && (prevProps.fireClickOutside !== fireClickOutside)) {
            if (
                (defaultValue !== null) && (typeof defaultValue !== 'undefined')
            ) {
                let defaultValueKey = Object.keys(defaultValue)[0];
                if (defaultValue[defaultValueKey] !== this.inputSearch.value) {
                    this.inputSearch.value = defaultValue[defaultValueKey] || '';
                }
            }
        }

        if (filterWidget && lookupEmpty && (defaultValue === null)) {
            this.inputSearch.value = defaultValue;
        }

        if (fireDropdownList && (prevProps.fireDropdownList !== fireDropdownList)) {
            this.handleChange('', true);
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

    navigate = (reverse) => {
        const { selected, list } = this.state;

        if (list.length === 0) {
            // Case of selecting row for creting new instance
            this.setState({
                selected: 'new'
            });
        } else {
            // Case of selecting regular list items
            if (typeof selected === 'number') {
                const selectTarget = selected + (reverse ? (-1) : (1));

                if (typeof list[selectTarget] !== 'undefined') {
                    this.setState({
                        selected: selectTarget
                    });
                }
            } else if (typeof list[0] !== 'undefined') {
                this.setState({
                    selected: 0
                });
            }
        }
    }

    handleSelect = (select) => {
        const {
            onChange, handleInputEmptyStatus, mainProperty, setNextProperty,
            filterWidget, subentity
        } = this.props;

        let mainProp = mainProperty[0];

        this.setState({
            selected: null
        }, () => {

            if (filterWidget) {
                const promise = onChange(mainProp.parameterName, select);

                if (promise) {
                    promise.then( ()=> {
                        setNextProperty(mainProp.parameterName);
                    })
                } else {
                    setNextProperty(mainProp.parameterName);
                }

            } else {
                if (subentity === 'quickInput') {
                    onChange(
                        mainProperty[0].field, select,
                        () => setNextProperty(mainProp.field)
                    );
                } else {
                    const promise = onChange(mainProp.field, select);

                    if (promise) {
                        promise.then( ()=> {
                            setNextProperty(mainProp.field);
                        })
                    } else {
                        setNextProperty(mainProp.field);
                    }
                }

            }

            if (select) {
                this.inputSearch.value = select[Object.keys(select)[0]];
            }

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
            if (callback) {
                callback();
            }
        });
    }

    handleChange = (handleChangeOnFocus, allowEmpty) => {
        const {
            recent, windowType, dataId, filterWidget, parameterName,
            tabId, rowId, entity, subentity, subentityId, viewId, mainProperty,
            handleInputEmptyStatus, enableAutofocus
        } = this.props;

        enableAutofocus();

        if (this.inputSearch.value || allowEmpty) {

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
            ).then( (response) => {
                let values = response.data.values || [];

                this.setState({
                    list: values,
                    loading: false,
                    selected: 0,
                    validLocal: (values.length === 0) &&
                                (handleChangeOnFocus !== true) ? false : true
                });
            });
        } else {
            this.setState({
                isInputEmpty: true,
                query: this.inputSearch.value,
                list: recent
            });

            handleInputEmptyStatus(true);
        }
    }

    handleKeyDown = (e) => {
        const { listenOnKeys, listenOnKeysFalse } = this.props;
        const { selected, list, query } = this.state;

        //need for prevent fire event onKeyDown 'Enter' from TableItem
        listenOnKeys && listenOnKeysFalse && listenOnKeysFalse();

        switch (e.key) {
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

                if (selected === 'new') {
                    this.handleAddNew(query);
                } else if (selected !== null) {
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

    handleValueChanged = () => {
        const {defaultValue, filterWidget} = this.props;
        const {oldValue, isInputEmpty} = this.state;

        if (!filterWidget && !!defaultValue && this.inputSearch) {
            const init = defaultValue;
            const inputValue = init[Object.keys(init)[0]];

            if (inputValue !== oldValue) {
                this.inputSearch.value = inputValue;

                this.setState({
                    oldValue: inputValue,
                    isInputEmpty: false,
                    validLocal: true,
                    list: [init]
                });
            } else if (isInputEmpty) {
                this.setState({
                    isInputEmpty: false,
                    list: [init]
                });
            }

        } else if (oldValue && !defaultValue && this.inputSearch) {
            const inputEmptyValue = defaultValue;

            if (inputEmptyValue !== oldValue) {
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
                className={
                    'raw-lookup-wrapper raw-lookup-wrapper-bcg' +
                    (disabled ? ' raw-lookup-disabled' : '') + (readonly ? ' input-disabled' : '')
                }
                onKeyDown={this.handleKeyDown}
            >
                <div className={'input-dropdown input-block'}>
                    <div className={'input-editable' + (align ? ' text-xs-' + align : '')}>
                        <input
                            ref={ (c) => this.inputSearch = c }
                            type="text"
                            className="input-field js-input-field font-weight-semibold"
                            readOnly={readonly}
                            disabled={readonly && !disabled}
                            tabIndex={tabIndex}
                            placeholder={placeholder}
                            onChange={this.handleChange}
                        />
                    </div>
                </div>

                {isOpen && !isInputEmpty && (
                    <LookupList
                        loading={loading}
                        selected={selected}
                        list={list}
                        query={query}
                        isInputEmpty={isInputEmpty}
                        disableOnClickOutside={!isOpen}
                        creatingNewDisabled={isModal}
                        newRecordCaption={newRecordCaption}
                        handleSelect={this.handleSelect}
                        handleAddNew={this.handleAddNew}
                        onClickOutside={this.handleBlur}
                    />
                )}
            </div>
        )
    }
}

RawLookup.propTypes = {
    dispatch: PropTypes.func.isRequired
}

RawLookup = connect()(RawLookup)

export default RawLookup
