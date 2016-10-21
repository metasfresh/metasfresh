import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    updateFiltersParameters,
    initFiltersParameters,
    deleteFiltersParameters
} from '../actions/AppActions';

import {
    patch,
    updateProperty,
    findRowByPropName
} from '../actions/WindowActions';

import {
    setFilter,
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
        const {dispatch, updateDocList, windowType, closeFilterMenu, setSelectedItem, filterId, filters} = this.props;
        // console.log('filters-----------');
        // console.log(filters);


        // dispatch(updateFiltersParameters(filterId, property, value));


        let filter =
          [{
            filterId: filterId,
            parameters: [
              {
                parameterName: property,
                value: value
              }
            ]
          }]


        // this.setSelectedItem(value);

        // setSelectedItem(value);


        // dispatch(setFilter(filter));
        // updateDocList('grid', windowType);
        // closeFilterMenu();


        dispatch(updateFiltersParameters(filterId, property, value));


        
    }

    //
    // This method may looks like a redundant for this one above,
    // but is need to handle controlled components if
    // they patch on other event than onchange
    //
    handleChange = (e, property) => {
        // const {dispatch, tabId, rowId, isModal, relativeDocId, precision} = this.props;
        // let currRowId = rowId;

        // if(!this.validatePrecision(e.target.value)){
        //     return;
        // }

        // if(rowId === "NEW"){
        //     currRowId = relativeDocId;
        // }

        // e.preventDefault();
        // dispatch(updateProperty(property, e.target.value, tabId, currRowId, isModal));
        // console.log('handle change');
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
        const {updateCell} = this.props;
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
                    }, 250);
                }
            );
        }



        // console.log('Filter widget props filters');
        // console.log(this.props.filters);
    }

    render() {
        const {caption, widgetType, parameters, windowType, type, noLabel, widgetData, icon, gridAlign, isModal, filterId, setSelectedItem, selectedItem, id, item, filters} = this.props;
        const {updated} = this.state;

        // console.log('selectedItem');
        // console.log(filters.parameters[id].value);
        // console.log(filters);
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
                                    selectedItem={filters.parameters.length? ( filters.parameters[id].value  ? filters.parameters[id].value : '' ) : ''}
                                    handleFocus={this.handleFocus}
                                    id={id}
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
    filters: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {appHandler} = state;
    const {
        filters
    } = appHandler || {
        filters: []
    }

    return {
        filters
    }
}

FilterWidget = connect(mapStateToProps)(FilterWidget)

export default FilterWidget
