import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    patch,
    updateProperty,
    findRowByPropName
} from '../actions/WindowActions';

import {
    setFilter,
    updateFiltersParameters,
    initFiltersParameters,
    deleteFiltersParameters
} from '../actions/ListActions';

import Datetime from 'react-datetime';
import Lookup from './widget/Lookup';
import List from './widget/List';
import ActionButton from './widget/ActionButton';
import RawWidget from './RawWidget';

class FilterWidget extends Component {
    constructor(props) {
        super(props);

        this.state = {
            cachedValue: null,
            updated: false
        }
    }

    handlePatch = (property, value, paramId) => {
        const {dispatch, updateDocList, windowType, closeFilterMenu, setSelectedItem, filterId, filter} = this.props;

        dispatch(updateFiltersParameters(filterId, property, value));
    }

    handleFocus = (e, value) => {
        e.preventDefault();

        this.setState(Object.assign({}, this.state, {
            cachedValue: value
        }));
    }

    validatePrecision = (value) => {
        const {widgetType} = this.props;
        let {precision} = this.props;

        if(widgetType === "Integer" || widgetType === "Quantity"){
            precision = 0;
        }

        if(precision < (value.split('.')[1] || []).length){
            return false;
        }else{
            return true;
        }
    }


    componentWillReceiveProps(nextProps) {
        const {updateCell, selectedItem} = this.props;
        if(updateCell){
            updateCell();
        }

        if(this.props.widgetData.value!==nextProps.widgetData.value) {
            let th = this;
            this.setState(
                Object.assign({}, this.state, {
                    updated: true
                }), () => {
                    setTimeout(function(){
                      th.setState(Object.assign({}, this.state, {
                        updated: false
                      }))
                    }, 1000);
                }
            );
        }
    }

    render() {
        const {
            caption, widgetType, parameters, windowType, type, noLabel, widgetData,
            icon, gridAlign, isModal, filterId, setSelectedItem, selectedItem, id,
            item, filter
        } = this.props;

        const {updated} = this.state;

        if(widgetData){
            return (
                <div className="form-group row">
                    <div className="col-xs-12">
                        <div className={"form-group row"}>
                            <div key="title" className={"form-control-label col-sm-3"} title={caption}>{item.caption}</div>
                            <div className="col-sm-9 ">
                                <RawWidget
                                    handlePatch={this.handlePatch}
                                    widgetType={widgetType}
                                    fields={widgetData}
                                    windowType={windowType}
                                    type={type}
                                    widgetData={widgetData}
                                    filterWidget={true}
                                    filterId={filterId}
                                    parameterName={widgetData.parameterName}
                                    setSelectedItem={setSelectedItem}
                                    selectedItem={filter.parameters.length? ( filter.parameters[id].value  ? filter.parameters[id].value : '' ) : ''}
                                    handleFocus={this.handleFocus}
                                    id={id}
                                    handleChange={this.handleChange}
                                />
                            </div>
                        </div>
                    </div>
                </div>
            )
        }else{
            return false;
        }
    }
}

FilterWidget.propTypes = {
    dispatch: PropTypes.func.isRequired,
    filter: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {listHandler} = state;
    const {
        filter
    } = listHandler || {
        filter: {}
    }

    return {
        filter
    }
}

FilterWidget = connect(mapStateToProps)(FilterWidget)

export default FilterWidget
