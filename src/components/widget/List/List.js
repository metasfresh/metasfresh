import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import RawList from './RawList';

import {
    dropdownRequest
} from '../../../actions/GenericActions';

class List extends Component {
    constructor(props) {
        super(props);
        this.state = {
            list: [],
            loading: false,
            selectedItem: '',
            prevValue: ''
        }
    }

    componentDidMount(){
        const {defaultValue} = this.props;

        if(defaultValue) {
            this.setState({
                prevValue: defaultValue
            });
        }
    }

    componentDidUpdate(prevProps){
        const {isInputEmpty} = this.props;

        if(isInputEmpty && prevProps.isInputEmpty !== isInputEmpty) {

            this.setState({
                prevValue: ''
            });

        }
    }

    handleFocus = () => {
        const {
            properties, dataId, rowId, tabId, windowType,
            filterWidget, entity, subentity, subentityId, viewId, attribute
        } = this.props;

        this.setState({
            loading: true
        });

        dropdownRequest(
            windowType,
            filterWidget ? properties[0].parameterName: properties[0].field,
            dataId, tabId, rowId, entity, subentity, subentityId, viewId,
            attribute
        ).then((res) => {
            this.setState({
                list: res.data.values,
                loading: false
            });
        });
    }

    handleSelect = (option) => {
        const {
            onChange, lookupList, properties, setNextProperty, mainProperty
        } = this.props;
        const {prevValue} = this.state;

         if( prevValue !== option[Object.keys(option)[0]] ) {
             if(lookupList){
                    onChange(properties[0].field, option);

                    this.setState({
                        selectedItem: option,
                        prevValue: option[Object.keys(option)[0]]
                    });
                    setNextProperty(mainProperty[0].field);
            } else {
                onChange(option);
            }
         }
    }

    handleAutoSelect = (option) => {
        const {
            onChange, properties, setNextProperty, mainProperty
        } = this.props;

        onChange(properties[0].field, option);

        this.setState({
            selectedItem: option,
            prevValue: option[Object.keys(option)[0]]
        });
        setNextProperty(mainProperty[0].field);
    }

    render() {
        const {
            rank, readonly, defaultValue, selected, align, updated, rowId,
            emptyText, tabIndex, mandatory, validStatus, lookupList, autofocus,
            blur
        } = this.props;
        const {list, loading, selectedItem} = this.state;

        return (
            <RawList
                list={list}
                loading={loading}
                onFocus={this.handleFocus}
                onSelect={option => this.handleSelect(option)}
                autoSelect={option => this.handleAutoSelect(option)}
                rank={rank}
                readonly={readonly}
                defaultValue={defaultValue}
                selected={lookupList ? selectedItem : selected}
                align={align}
                updated={updated}
                rowId={rowId}
                emptyText={emptyText}
                tabIndex={tabIndex}
                mandatory={mandatory}
                validStatus={validStatus}
                autofocus={autofocus}
                lookupList={lookupList}
                blur={blur}
            />
        )
    }
}

List.propTypes = {
    dispatch: PropTypes.func.isRequired
};

List = connect()(List)

export default List
