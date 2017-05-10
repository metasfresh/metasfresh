import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import update from 'immutability-helper';

import {
    autocompleteRequest,
    dropdownRequest
} from '../../../actions/GenericActions';

import {
    getItemsByProperty,
    openModal
} from '../../../actions/WindowActions';

import RawLookup from './RawLookup';

import LookupList from './LookupList';
import List from '../List/List';

class Lookup extends Component {
    constructor(props) {
        super(props);

        const {properties} = this.props;

        this.state = {
            isInputEmpty: true,
            propertiesCopy: getItemsByProperty(properties, 'source', 'list'),
            property: ''
        }
    }

    componentDidMount() {
        const {defaultValue, properties} = this.props;

        this.checkIfDefaultValue();
    }

    componentDidUpdate(prevProps) {
        const {defaultValue, properties} = this.props;
        const {property} = this.state;

         const objectValue = property && getItemsByProperty(
                                defaultValue, 'field', property
                            )[0].value;

        if(objectValue) {
            this.setNextProperty(property);
        }
     
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
                if(nextIndex<defaultValue.length && defaultValue[index].field === prop){
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

    handleClear = (e) => {
        const {onChange, properties} = this.props;
        onChange(properties, null, false);
        this.setState({
            isInputEmpty: true,
            property: ''
        });
    }

    render() {
        const {
            rank, readonly, defaultValue, placeholder, align, isModal, updated,
            filterWidget, mandatory, rowId, tabIndex, validStatus, recent, onChange,
            newRecordCaption, properties, windowType, parameterName, entity, dataId, tabId,
            subentity, subentityId, viewId
        } = this.props;

        const {isInputEmpty, property} = this.state;

        return (
            <div
                onKeyDown={this.handleKeyDown}
                ref={(c) => this.dropdown = c}
                className={
                    'input-dropdown-container lookup-wrapper input-' +
                    (rank ? rank : 'primary') +
                    (updated ? ' pulse-on' : ' pulse-off') +
                    (filterWidget ? ' input-full' : '')+
                    (mandatory && (
                        (validStatus.initialValue && !validStatus.valid)) ?
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
                        if(item.source === 'lookup'){
                            return <RawLookup
                                key={index}
                                newRecordCaption={newRecordCaption}
                                defaultValue={getItemsByProperty(
                                            defaultValue, 'field', item.field)
                                            [0].value}
                                mainProperty={[item]}
                                placeholder={placeholder}
                                readonly={readonly}
                                tabIndex={tabIndex}
                                windowType={windowType}
                                parameterName={parameterName}
                                entity={entity}
                                dataId={dataId}
                                isModal={isModal}
                                recent={recent}
                                rank={rank}
                                updated={updated}
                                filterWidget={filterWidget}
                                mandatory={mandatory}
                                validStatus={validStatus}
                                align={align}
                                onChange={onChange}
                                item={item}
                                handleInputEmptyStatus={this.handleInputEmptyStatus}
                                setNextProperty={this.setNextProperty}
                            />

                        } else if (item.source === 'list') {

                            const objectValue = getItemsByProperty(
                                defaultValue, 'field', item.field
                            )[0].value;

                            return <div className="raw-lookup-wrapper raw-lookup-wrapper-bcg" key={index}>
                                    <List
                                        dataId={dataId}
                                        entity={entity}
                                        windowType={windowType}
                                        filterWidget={filterWidget}
                                        properties={[item]}
                                        tabId={tabId}
                                        rowId={rowId}
                                        subentity={subentity}
                                        subentityId={subentityId}
                                        viewId={viewId}
                                        onChange={onChange}
                                        lookupList={true}
                                        autofocus={item.field == property ? true : false}
                                        defaultValue={objectValue ? objectValue[Object.keys(objectValue)[0]] : ''}
                                        setNextProperty={this.setNextProperty}
                                        mainProperty={[item]}
                                        blur={!property?true:false}
                                    />
                                </div>
                        }
                })
            }
    
            {isInputEmpty ?
                <div className="input-icon input-icon-lg raw-lookup-wrapper">
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

export default Lookup
