import React, { Component, PropTypes, cloneElement } from 'react';
import {connect} from 'react-redux';

import {
    initLayout,
    getData,
    patchRequest
} from '../actions/GenericActions';

import {
    findRowByPropName,
    parseToDisplay
} from '../actions/WindowActions';

class DataLayoutWrapper extends Component {
    constructor(props){
        super(props);

        this.state = {
            layout: [],
            data: [],
            dataId: null
        }
    }

    handleChange = (field, value) => {
        const {data} = this.state;

        this.setState(Object.assign({}, this.state, {
            data: data.map(item => {
                if(item.field === field){
                    return Object.assign({}, item, {
                        value: value
                    })
                }else{
                    return item;
                }
            })
        }))
    }

    handlePatch = (prop, value) => {
        const {dispatch, entity, windowType, viewId} = this.props;
        const {dataId} = this.state;

        dispatch(patchRequest(
            entity, windowType, dataId, null, null, prop, value, null, null, null, viewId
        )).then(response => {
            response.data[0].fields.map(item => {
                this.setState(Object.assign({}, this.state, {
                    data: this.state.data.map(field => {
                        if(field.field === item.field){
                            return Object.assign({}, field, item);
                        }else{
                            return field;
                        }
                    })
                }));
            })
        })
    }

    setData = (data, dataId, cb) => {
        this.setState({
            data: parseToDisplay(data),
            dataId: dataId
        }, () => {
            cb && cb();
        });
    }

    setLayout = (layout, cb) => {
        this.setState({
            layout: layout
        }, () => {
            cb && cb();
        });
    }

    render() {
        const {
            layout, data, dataId
        } = this.state;

        const {
            children, className
        } = this.props;

        return (
            <div
                className={className}
            >{
                // The nameing of props has a significant prefix
                // to suggest dev that these props are from wrapper
                cloneElement(children, Object.assign({}, this.props, {
                    DLWrapperData: data,
                    DLWrapperDataId: dataId,
                    DLWrapperLayout: layout,

                    DLWrapperSetData: this.setData,
                    DLWrapperSetLayout: this.setLayout,
                    DLWrapperHandleChange: this.handleChange,
                    DLWrapperHandlePatch: this.handlePatch
                }))
            }</div>
        )
    }
}

DataLayoutWrapper = connect()(DataLayoutWrapper)

export default DataLayoutWrapper;
