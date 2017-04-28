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
            mainProperty: getItemsByProperty(properties, 'source', 'lookup')
        }

        // console.log(this.state.propertiesCopy);
        // console.log(this.state.mainProperty);
        // console.log(properties);
    }

    handleClear = (e) => {
        const {onChange, properties} = this.props;
        e && e.preventDefault();

        onChange(properties, null, false);

        this.handleBlur(this.clearState);
    }

    render() {
        const {
            rank, readonly, defaultValue, placeholder, align, isModal, updated,
            filterWidget, mandatory, rowId, tabIndex, validStatus, recent, onChange,
            newRecordCaption, properties, windowType, parameterName, entity, dataId, tabId,
            subentity, subentityId, viewId
        } = this.props;

        const {isInputEmpty} = this.props;

        console.log('defaultValue');
        console.log(defaultValue);

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
                    {
                        if(item.source === 'lookup'){
                            return <RawLookup
                                key={index}
                                newRecordCaption={newRecordCaption}
                                defaultValue={defaultValue}
                                properties={properties}
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
                            />

                        } else if (item.source === 'list') {

                            const objectValue = getItemsByProperty(
                                defaultValue, 'field', item.field
                            )[0].value;

                            console.log('objectValue');
                            console.log(objectValue);


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
                                        defaultValue={objectValue[Object.keys(objectValue)[0]]}
                                    />
                                </div>
                        }
                        
                        
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
