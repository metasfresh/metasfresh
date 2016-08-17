import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import Datetime from 'react-datetime';
import LookupDropdown from '../components/app/LookupDropdown';
import Dropdown from '../components/app/Dropdown';

class Widget extends Component {
    constructor(props) {
        super(props);
    }
    renderWidget = (widgetType, fields, windowType, dataId, type, data) => {
        switch(widgetType){
            case "Date":
                return (
                    <div className="input-icon-container input-secondary input-block">
                        <Datetime
                            timeFormat={false}
                            dateFormat={true}
                            locale="de"
                            inputProps={{placeholder: "(none)", disabled: true}}

                            defaultValue={new Date(data.value)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "DateTime":
                return (
                    <div className="input-icon-container input-secondary input-block">
                        <Datetime
                            timeFormat={true}
                            dateFormat={true}
                            locale="de"
                            inputProps={{placeholder: "(none)", readonly: data.readonly}}
                            defaultValue={new Date(data.value)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Time":
                return (
                    <div className="input-icon-container input-secondary input-block">
                        <Datetime
                            timeFormat={true}
                            dateFormat={false}
                            locale="de"
                            inputProps={{placeholder: "(none)", readonly: data.readonly}}
                            defaultValue={new Date(data.value)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Lookup":
                return (
                    <LookupDropdown
                        recent={[]}
                        dataId={dataId}
                        properties={fields}
                        windowType={windowType}
                        defaultValue={data.value}
                        readonly={data.readonly}
                        rank={type}
                    />
                )
            case "List":
                return (
                    <Dropdown
                        options={['1','2','3']}
                        dataId={dataId}
                        defaultValue="(none)"
                        properties={fields}
                        readonly={data.readonly}
                    />
                )
            case "Text":
                return (
                    <div className={"input-block input-"+ type}>
                        <input
                            type="text"
                            className="input-field"
                            defaultValue={data.value}
                            readonly={data.readonly}
                        />
                    </div>
                )
            case "LongText":
                return (
                    <div className={"input-block input-" + type}>
                        <textarea
                            className="input-field"
                            defaultValue={data.value}
                            readonly={data.readonly}
                        />
                    </div>
                )
            case "Integer":
                return (
                    <div className={"input-block input-" + type}>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            defaultValue={data.value}
                            readonly={data.readonly}
                        />
                    </div>
                )
            case "Number":
                return (
                    <div className={"input-block input-" + type}>
                        <input
                            type="number"
                            className="input-field"
                            defaultValue={data.value}
                            readonly={data.readonly}
                        />
                    </div>
                )
            case "Amount" :
                return (
                    <div className={"input-block input-" + type}>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            defaultValue={data.value}
                            readonly={data.readonly}
                        />
                    </div>
                )
            case "Quantity":
                return (
                    <div className={"input-block input-" + type}>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            defaultValue={data.value}
                            readonly={data.readonly}
                        />
                    </div>
                )
            case "CostPrice":
                return (
                    <div className={"input-block input-" + type}>
                        <input
                            type="number"
                            className="input-field"
                            defaultValue={data.value}
                            readonly={data.readonly}
                        />
                    </div>
                )
            case "YesNo":
                return (
                    <label className="input-checkbox">
                        <input
                            type="checkbox"
                            checked={data.value}
                            readonly={data.readonly}
                        />
                        <div className="input-checkbox-tick"/>
                    </label>
                )
            case "Switch":
                return (
                    <label className="input-switch">
                        <input
                            type="checkbox"
                            checked={data.value}
                            readonly={data.readonly}
                        />
                        <div className="input-slider" />
                    </label>
                )
            case "Label":
                return (
                    <div className="tag tag-warning">{data.value}</div>
                )
            case "Button":
                return (
                    <button className="btn btn-sm btn-meta-primary">Add</button>
                )
            default:
                return (
                    <div>{widgetType}</div>
                )
        }
    }
    findRowByPropName = (arr, name) => {
        let ret = -1;
        for(let i = 0; i < arr.length; i++){
            if(arr[i].field === name){
                ret = arr[i];
                break;
            }
        }

        return ret;
    }
    render() {
        const {caption, widgetType, description, fields, windowType, data, type} = this.props;
        const dataId = data[0].value;
        const widgetData = this.findRowByPropName(data, fields[0].field);
        return (
            <div className={"form-group row " + (type === "primary" ? "" : "")}>
                <div key="title" className={"form-control-label " + ((type === "primary") ? "col-sm-12 panel-title" : "col-sm-3")}>{caption}</div>
                <div className={(type === "primary") ? "col-sm-12 " : "col-sm-9 "}>
                    {this.renderWidget(widgetType, fields, windowType, dataId, type, widgetData)}
                </div>
            </div>
        )
    }
}

Widget.propTypes = {
    dispatch: PropTypes.func.isRequired,
    data: PropTypes.array.isRequired,
};

function mapStateToProps(state) {
    const {windowHandler} = state;
    const {
        data
    } = windowHandler || {
        data: []
    }

    return {
        data
    }
}

Widget = connect(mapStateToProps)(Widget)

export default Widget
