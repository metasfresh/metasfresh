import React, { Component } from 'react';
import {connect} from 'react-redux';

import onClickOutside from 'react-onclickoutside';

import {
    getItemsByProperty
} from '../../../actions/WindowActions';

import RawLookup from './RawLookup';
import List from '../List/List';

class Lookup extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isInputEmpty: true,
            propertiesCopy: getItemsByProperty(this.props.properties, 'source', 'list'),
            property: '',
            fireClickOutside: false,
            initialFocus: false,
            localClearing: false,
            fireDropdownList: false,
            autofocusDisabled: false
        }
    }

    componentDidMount() {
        this.checkIfDefaultValue();
    }

    checkIfDefaultValue = () => {
        const { defaultValue } = this.props;

        if (defaultValue) {
            defaultValue.map( (item) => {
                if (item.value) {
                    this.setState({
                        isInputEmpty: false
                    });
                }
            });
        }
    }

    setNextProperty = (prop) => {
        const { defaultValue, properties, onBlurWidget } = this.props;

        if (defaultValue) {
            defaultValue.map( (item, index) => {
                const nextIndex = index + 1;

                if (
                    (nextIndex < defaultValue.length) &&
                    (defaultValue[index].field === prop)
                ) {
                    let nextProp = properties[nextIndex];

                    if (nextProp.source === 'list') {
                        this.linkedList.map( (listComponent) => {
                            if (listComponent && listComponent.props) {
                                let listProp = listComponent.props.mainProperty;

                                if (
                                    listProp && Array.isArray(listProp) &&
                                    (listProp.length > 0)
                                ) {
                                    const listPropField = listProp[0].field;

                                    if (
                                        listComponent.activate &&
                                        (listPropField === nextProp.field)
                                    ) {
                                        listComponent.requestListData(
                                            true,
                                            true
                                        );
                                        listComponent.activate();
                                    }
                                }
                            }
                        });

                        this.setState({
                            property: nextProp.field
                        });
                    } else {
                        this.setState({
                            property: nextProp.field
                        });
                    }
                }
                else if (defaultValue[defaultValue.length - 1].field === prop) {
                    this.setState({
                        property: ''
                    }, () => {
                        onBlurWidget && onBlurWidget();
                    });
                }
            });
        }
    }

    openDropdownList = () => {
        this.setState({
            fireDropdownList: true
        }, () => {
            this.setState({
                fireDropdownList: false
            })
        });
    }

    resetLocalClearing = () => {
        this.setState({
            localClearing: false
        });
    }

    handleClickOutside = () => {
        this.setState({
            fireClickOutside: true,
            property: ''
        }, () => {
            this.setState({
                fireClickOutside: false
            });
        });
    }

    handleInputEmptyStatus = (isEmpty) => {
        this.setState({
            isInputEmpty: isEmpty
        });
    }

    handleClear = () => {
        const { onChange, properties } = this.props;

        if (onChange) {
            onChange(properties, null, false);
        }

        this.setState({
            isInputEmpty: true,
            property: '',
            initialFocus: true,
            localClearing: true,
            autofocusDisabled: false
        });
    }

    disableAutofocus= () => {
        this.setState({
            autofocusDisabled: true
        });
    }

    enableAutofocus = () => {
        this.setState({
            autofocusDisabled: false
        });
    }

    render() {
        const {
            rank, readonly, defaultValue, placeholder, align, isModal, updated,
            filterWidget, mandatory, rowId, tabIndex, validStatus, recent,
            onChange, newRecordCaption, properties, windowType, parameterName,
            entity, dataId, tabId, subentity, subentityId, viewId, autoFocus,
            newRecordWindowId
        } = this.props;

        const {
            isInputEmpty, property, fireClickOutside, initialFocus,
            localClearing, fireDropdownList, autofocusDisabled
        } = this.state;

        this.linkedList = [];

        return (
            <div
                ref={ (c) => this.dropdown = c }
                className={
                    'input-dropdown-container lookup-wrapper input-' + (rank ? rank : 'primary') +
                    (updated ? ' pulse-on' : ' pulse-off') + (filterWidget ? ' input-full' : '') +
                    (mandatory && (isInputEmpty ||
                        (validStatus && validStatus.initialValue &&
                        !validStatus.valid)) ?
                        ' input-mandatory' : '') +
                    ((validStatus &&
                        (
                            (!validStatus.valid && !validStatus.initialValue)
                        )
                    ) ? ' input-error' : '') +
                    (readonly ? ' lookup-wrapper-disabled' : '')
                }
            >

                {properties && properties.map( (item, index) => {
                    const disabled = isInputEmpty && (index !== 0);
                    const itemByProperty = getItemsByProperty(defaultValue, 'field', item.field)[0];

                    if ((item.source === 'lookup') || (item.widgetType === 'Lookup')) {
                        return (
                            <RawLookup
                                key={index}
                                defaultValue={itemByProperty.value}
                                initialFocus={(index === 0) ? initialFocus : false}
                                mainProperty={[item]}
                                resetLocalClearing={this.resetLocalClearing}
                                setNextProperty={this.setNextProperty}
                                lookupEmpty={isInputEmpty}
                                fireDropdownList={fireDropdownList}
                                handleInputEmptyStatus={this.handleInputEmptyStatus}
                                enableAutofocus={this.enableAutofocus}
                                {...{
                                    placeholder, readonly, tabIndex,
                                    windowType, parameterName, entity, dataId,
                                    isModal, recent, rank, updated, filterWidget,
                                    mandatory, validStatus, align, onChange, item,
                                    disabled, fireClickOutside, viewId, subentity,
                                    subentityId, autoFocus, tabId, rowId,
                                    newRecordCaption, newRecordWindowId,
                                    localClearing
                                }}
                            />
                        )

                    } else if (item.source === 'list') {

                        const isFirstProperty = (index === 0);
                        const isCurrentProperty = (item.field === property) && !autofocusDisabled;

                        return (
                            <div
                                key={index}
                                className={
                                    'raw-lookup-wrapper raw-lookup-wrapper-bcg ' +
                                    (disabled || readonly ? 'raw-lookup-disabled' : '')
                                }
                            >
                                <List
                                    ref={ (c) => {
                                        if (c) {
                                            this.linkedList.push(c.getWrappedInstance());
                                        }
                                    }}
                                    readonly={disabled || readonly}
                                    lookupList={true}
                                    autofocus={isCurrentProperty}
                                    properties={[item]}
                                    mainProperty={[item]}
                                    defaultValue={itemByProperty.value ? itemByProperty.value : ''}
                                    initialFocus={isFirstProperty ? initialFocus : false}
                                    blur={!property ? true : false}
                                    setNextProperty={this.setNextProperty}
                                    disableAutofocus={this.disableAutofocus}
                                    enableAutofocus={this.enableAutofocus}
                                    {...{
                                        dataId, entity, windowType,
                                        filterWidget, tabId, rowId,
                                        subentity, subentityId, viewId,
                                        onChange, isInputEmpty, property
                                    }}
                                />
                            </div>
                        )
                    }
                })}

                {!readonly && (isInputEmpty ? (
                    <div
                        className="input-icon input-icon-lg raw-lookup-wrapper"
                        onClick={this.openDropdownList}
                    >
                        <i className="meta-icon-preview" />
                    </div>
                ) : (
                    <div className="input-icon input-icon-lg raw-lookup-wrapper">
                        <i className="meta-icon-close-alt" onClick={this.handleClear} />
                    </div>
                ))}
            </div>
        )
    }
}

Lookup = connect()(onClickOutside(Lookup))

export default Lookup
