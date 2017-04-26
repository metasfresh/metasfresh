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

import LookupInput from './LookupInput';

import LookupList from './LookupList';

class Lookup extends Component {
    constructor(props) {
        super(props);

        const {properties} = this.props;

        this.state = {
            query: '',
            list: [],
            isInputEmpty: true,
            selected: null,
            model: null,
            property: '',
            properts: {},
            loading: false,
            propertiesCopy: getItemsByProperty(properties, 'source', 'list'),
            mainProperty: getItemsByProperty(properties, 'source', 'lookup'),
            oldValue: '',
            isOpen: false,
            shouldBeFocused: true,
            validLocal: true
        }

    }

    componentDidMount() {
     
    }

    componentDidUpdate() {
        
    }

    handleSelect = (select) => {
        
    }

 




    render() {
        const {
            rank, readonly, defaultValue, placeholder, align, isModal, updated,
            filterWidget, mandatory, rowId, tabIndex, validStatus,
            newRecordCaption
        } = this.props;

        const {
            propertiesCopy, isInputEmpty, list, query, loading, selected,
            isOpen, validLocal
        } = this.state;

        return (
            <div
                onKeyDown={this.handleKeyDown}
                onClick={()=> this.inputSearch.focus()}
                ref={(c) => this.dropdown = c}
                className={
                    'input-dropdown-container ' +
                    (isOpen ? 'input-focused ' : '') +
                    (readonly ? 'input-disabled ' : '') +
                    (rowId ? 'input-dropdown-container-static ' : '') +
                    ((rowId && !isModal)? 'input-table ' : '')
                }
            >
                <div className={
                    'input-dropdown input-block input-' +
                    (rank ? rank : 'primary') +
                    (updated ? ' pulse-on' : ' pulse-off') +
                    (filterWidget ? ' input-full' : '') +
                    (mandatory && (isInputEmpty ||
                        (validStatus.initialValue && !validStatus.valid)) ?
                        ' input-mandatory ' : '') +
                    ((validStatus &&
                        (
                            (!validStatus.valid && !validStatus.initialValue) ||
                             !validLocal
                        )
                    ) ? ' input-error ' : '')
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
                            disabled={readonly}
                            tabIndex={tabIndex}
                        />
                    </div>

                    <LookupInput/>

                 

                    
                </div>
                
                
            </div>
        )
    }
}

export default Lookup
