import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import RawList from './RawList';

import {
    dropdownRequest
} from '../../../actions/AppActions';

class List extends Component {
    constructor(props) {
        super(props);
        this.state = {
            list: [],
            loading: false
        }
    }

    handleFocus = () => {
        const {
            properties, dispatch, dataId, rowId, tabId, windowType, filterWidget,
            filterId, parameterName, entity, subentity, subentityId
        } = this.props;

        this.setState(Object.assign({}, this.state, {
            loading: true
        }));

        dispatch(dropdownRequest(
            windowType, properties[0].field, dataId, tabId, rowId, entity,
            subentity, subentityId
        )).then((res) => {
            this.setState(Object.assign({}, this.state, {
                list: res.data.values,
                loading: false
            }));
        });
    }

    handleSelect = (option) => {
        const {onChange, setSelectedItem, filterWidget} = this.props;
        onChange(option);
        if(filterWidget){
            setSelectedItem(option);
        }
    }

    render() {
        const {
            rank, readonly, defaultValue, selected, align, updated, rowId,
            emptyText, tabIndex
        } = this.props;
        const {list, loading} = this.state;

        return (
            <RawList
                list={list}
                loading={loading}
                onFocus={this.handleFocus}
                onSelect={option => this.handleSelect(option)}
                rank={rank}
                readonly={readonly}
                defaultValue={defaultValue}
                selected={selected}
                align={align}
                updated={updated}
                rowId={rowId}
                emptyText={emptyText}
                tabIndex={tabIndex}
            />
        )
    }
}

List.propTypes = {
    dispatch: PropTypes.func.isRequired
};

List = connect()(List)

export default List
