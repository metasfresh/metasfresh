import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    updateFiltersParameters
} from '../actions/ListActions';

import RawWidget from './RawWidget';

class FilterWidget extends Component {
    constructor(props) {
        super(props);
    }

    handlePatch = (property, value, paramId) => {
        const {dispatch, updateDocList, windowType, closeFilterMenu, setSelectedItem, filterId, filter} = this.props;
        dispatch(updateFiltersParameters(filterId, property, value));
    }

    render() {
        const {
            caption, widgetType, parameters, windowType, type, noLabel, widgetData,
            icon, gridAlign, isModal, filterId, setSelectedItem, selectedItem, id,
            item, filter
        } = this.props;


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
                                    range={item.range}
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
