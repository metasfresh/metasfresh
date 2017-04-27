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

    render() {
        const {
            rank, readonly, defaultValue, placeholder, align, isModal, updated,
            filterWidget, mandatory, rowId, tabIndex, validStatus, recent, onChange,
            newRecordCaption, properties, windowType, parameterName, entity, dataId
        } = this.props;

        const {isInputEmpty} = this.props;

        return (
            <div
                onKeyDown={this.handleKeyDown}
                ref={(c) => this.dropdown = c}
                className={
                    'input-dropdown-container input-flex input-' +
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
                properties && properties.map(item => {
                    console.log('asa');
                    {
                        <RawLookup
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
                    />
                    }
                })
            }

            <RawLookup
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
                    />

                    {isInputEmpty ?
                        <div className="input-icon input-icon-lg">
                            <i className="meta-icon-preview" />
                        </div> :
                        <div className="input-icon input-icon-lg">
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
