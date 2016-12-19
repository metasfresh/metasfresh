import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    updateFiltersParameters
} from '../../actions/ListActions';

import RawWidget from './RawWidget';

class FilterWidget extends Component {
    constructor(props) {
        super(props);
    }

    handlePatch = (property, value, valueTo) => {
        const {dispatch, windowType, closeFilterMenu, setSelectedItem, filterId, filter} = this.props;
        dispatch(updateFiltersParameters(filterId, property, value, valueTo));
    }

    render() {
        const {
            caption, widgetType, parameters, windowType, type, noLabel, widgetData,
            icon, gridAlign, isModal, filterId, setSelectedItem, selectedItem, id,
            item, filter, isShown, isHidden
        } = this.props;

        if(widgetData){
            return (
                <RawWidget
                    handlePatch={this.handlePatch}
                    widgetType={widgetType}
                    fields={[widgetData]}
                    windowType={windowType}
                    type={type}
                    widgetData={[widgetData]}
                    filterWidget={true}
                    filterId={filterId}
                    parameterName={widgetData.parameterName}
                    setSelectedItem={setSelectedItem}
                    selectedItem={filter.parameters.length ? ( filter.parameters[id].value  ? filter.parameters[id].value : '' ) : ''}
                    selectedItemTo={filter.parameters.length ? ( filter.parameters[id].valueTo  ? filter.parameters[id].valueTo : '' ) : ''}
                    handleFocus={this.handleFocus}
                    id={id}
                    handleChange={this.handleChange}
                    range={item.range}
                    isShown={isShown}
                    isHidden={isHidden}
                    caption={item.caption}
                    noLabel={false}
                />
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
