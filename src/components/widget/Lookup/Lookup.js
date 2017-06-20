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

        const {properties} = this.props;

        this.state = {
            isInputEmpty: true,
            propertiesCopy: getItemsByProperty(properties, 'source', 'list'),
            property: '',
            fireClickOutside: false,
            initialFocus: false,
            localClearing: false,
            fireDropdownList: false
        }
    }

    componentDidMount() {
        this.checkIfDefaultValue();
    }

    handleClickOutside = () => {
        this.setState({
            fireClickOutside: true,
            property: ''
        }, () => {
            this.setState({
                fireClickOutside: false
            })
        })
    }

    handleInputEmptyStatus = (isEmpty) => {
        this.setState({
            isInputEmpty: isEmpty
        })
    }

    setNextProperty = (prop)=> {
        const {defaultValue, properties} = this.props;

        defaultValue.map((item, index)=>{
            const nextIndex = index+1;
            if(nextIndex<defaultValue.length &&
                defaultValue[index].field === prop){
                this.setState({
                    property: properties[nextIndex].field
                })
                return;
            } else if(defaultValue[defaultValue.length-1].field === prop){
                this.setState({
                    property: ''
                })
            }
        })
    }

    checkIfDefaultValue = () => {
        const {defaultValue} = this.props;
        defaultValue.map(item => {
            if(item.value){
                this.setState({
                    isInputEmpty: false
                })
            }
        });
    }

    openDropdownList = () => {
        this.setState({
            fireDropdownList: true
        }, () => {
            this.setState({
                fireDropdownList: false
            })
        })
    }

    resetLocalClearing = () => {
        this.setState({
            localClearing: false
        })
    }

    handleClear = () => {
        const {onChange, properties} = this.props;
        onChange(properties, null, false);
        this.setState({
            isInputEmpty: true,
            property: '',
            initialFocus: true,
            localClearing: true
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
            localClearing, fireDropdownList
        } = this.state;

        return (
            <div
                ref={(c) => this.dropdown = c}
                className={
                    'input-dropdown-container lookup-wrapper input-' +
                    (rank ? rank : 'primary') +
                    (updated ? ' pulse-on' : ' pulse-off') +
                    (filterWidget ? ' input-full' : '') +
                    (mandatory && (isInputEmpty ||
                        (validStatus && validStatus.initialValue &&
                        !validStatus.valid)) ?
                        ' input-mandatory ' : '') +
                    ((validStatus &&
                        (
                            (!validStatus.valid && !validStatus.initialValue)
                        )
                    ) ? ' input-error ' : '')
                }
            >

            {
                properties && properties.map((item, index) => {
                        const disabled = isInputEmpty && index != 0;
                        if(item.source === 'lookup' ||
                            item.widgetType === 'Lookup'){
                            return <RawLookup
                                key={index}
                                defaultValue={
                                    getItemsByProperty(defaultValue,
                                            'field', item.field)[0].value
                                }
                                mainProperty={[item]}
                                handleInputEmptyStatus={
                                    this.handleInputEmptyStatus
                                }
                                resetLocalClearing={this.resetLocalClearing}
                                initialFocus={index===0 ? initialFocus : false}
                                setNextProperty={this.setNextProperty}
                                lookupEmpty={isInputEmpty}
                                fireDropdownList={fireDropdownList}
                                {...{placeholder, readonly, tabIndex,
                                windowType, parameterName, entity, dataId,
                                isModal, recent, rank, updated, filterWidget,
                                mandatory, validStatus, align, onChange, item,
                                disabled, fireClickOutside, viewId, subentity,
                                subentityId, autoFocus, tabId, rowId,
                                newRecordCaption, newRecordWindowId,
                                localClearing}}
                            />

                        } else if (item.source === 'list') {

                            const objectValue = getItemsByProperty(
                                defaultValue, 'field', item.field
                            )[0].value;

                            return <div
                                className={
                                    'raw-lookup-wrapper ' +
                                    'raw-lookup-wrapper-bcg ' +
                                    (disabled ? 'raw-lookup-disabled':'')
                                    }
                                key={index}>
                                    <List
                                        {...{dataId, entity, windowType,
                                            filterWidget, tabId, rowId,
                                            subentity, subentityId, viewId,
                                            onChange, isInputEmpty, property
                                        }}
                                        properties={[item]}
                                        lookupList={true}
                                        autofocus={
                                            item.field == property ?
                                            true : false
                                        }
                                        defaultValue={
                                            objectValue ?
                                            objectValue : ''
                                        }
                                        initialFocus={
                                            index===0 ? initialFocus : false
                                        }
                                        setNextProperty={this.setNextProperty}
                                        mainProperty={[item]}
                                        blur={!property?true:false}
                                        readonly={disabled || readonly}
                                    />
                                </div>
                        }
                })
            }
            {isInputEmpty ?
                <div
                    className="input-icon input-icon-lg raw-lookup-wrapper"
                    onClick={this.openDropdownList}
                >
                    <i className="meta-icon-preview" />
                </div> :
                <div className="input-icon input-icon-lg raw-lookup-wrapper">
                    {!readonly && <i
                        onClick={this.handleClear}
                        className="meta-icon-close-alt"
                    />}
                </div>
            }
            </div>
        )
    }
}

Lookup = connect()(onClickOutside(Lookup))

export default Lookup
