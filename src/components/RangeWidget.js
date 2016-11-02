import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import RawWidget from './RawWidget';

class RangeWidget extends Component {
    constructor(props) {
        super(props);
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

RangeWidget.propTypes = {
    dispatch: PropTypes.func.isRequired
};

RangeWidget = connect()(RangeWidget)

export default RangeWidget
