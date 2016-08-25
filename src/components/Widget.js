import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    patch,
    updateDataProperty,
    findRowByPropName
} from '../actions/WindowActions';

import Datetime from 'react-datetime';
import Lookup from './widget/Lookup';
import List from './widget/List';

class Widget extends Component {
    constructor(props) {
        super(props);
    }
    handlePatch = (property, value) => {
        const {widgetData,dataId, windowType, dispatch, rowId, tabId} = this.props;
        //check if we should update store

        if(widgetData.value !== value ){
            dispatch(updateDataProperty(property, value));
        }
        dispatch(patch(windowType, dataId, tabId, rowId, property, value));
    }
    handleChange = (e, property) => {
        const {dispatch} = this.props;
        e.preventDefault();
        dispatch(updateDataProperty(property, e.target.value));
    }
    renderWidget = (widgetType, fields, windowType, dataId, type, data, mandatory) => {
        switch(widgetType){
            case "Date":
                return (
                    <div className={"input-icon-container input-block " +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "") +
                        (type === "primary" ? "input-primary " : "input-secondary ")
                    }>
                        <Datetime
                            timeFormat={false}
                            dateFormat={true}
                            locale="de"
                            inputProps={{placeholder: "(none)", disabled: data.readonly}}
                            value={data.value ? new Date(data.value) : null}
                            onChange={(date) => this.handlePatch(fields[0].field, date)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "DateTime":
                return (
                    <div className={"input-icon-container input-block " +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "") +
                        (type === "primary" ? "input-primary " : "input-secondary ")
                    }>
                        <Datetime
                            timeFormat={true}
                            dateFormat={true}
                            locale="de"
                            inputProps={{placeholder: "(none)", disabled: data.readonly}}
                            value={data.value ? new Date(data.value) : null}
                            onChange={(date) => this.handlePatch(fields[0].field, date)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Time":
                return (
                    <div className={"input-icon-container input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                    }>
                        <Datetime
                            timeFormat={true}
                            dateFormat={false}
                            locale="de"
                            inputProps={{placeholder: "(none)", disabled: data.readonly}}
                            value={data.value ? new Date(data.value) : null}
                            onChange={(date) => this.handlePatch(fields[0].field, date)}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Lookup":
                return (
                    <Lookup
                        recent={[]}
                        dataId={dataId}
                        properties={fields}
                        windowType={windowType}
                        value={data.value}
                        readonly={data.readonly}
                        mandatory={data.mandatory}
                        rank={type}
                        onChange={this.handlePatch}
                    />
                )
            case "List":
                return (
                    <List
                        dataId={dataId}
                        defaultValue="(none)"
                        selected={data.value}
                        properties={fields}
                        readonly={data.readonly}
                        mandatory={data.mandatory}
                        onChange={(option) => this.handlePatch(fields[0].field, option)}
                    />
                )
            case "Text":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                    }>
                        <input
                            type="text"
                            className="input-field"
                            value={data.value}
                            disabled={data.readonly}
                            onChange={(e) => this.handleChange(e, fields[0].field)}
                            onBlur={(e) => this.handlePatch(fields[0].field, e.target.value)}
                        />
                    </div>
                )
            case "LongText":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                    }>
                        <textarea
                            className="input-field"
                            value={data.value}
                            disabled={data.readonly}
                            onChange={(e) => this.handleChange(e, fields[0].field)}
                            onBlur={(e) => this.handlePatch(fields[0].field, e.target.value)}
                        />
                    </div>
                )
            case "Integer":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            value={data.value}
                            disabled={data.readonly}
                            onChange={(e) => this.handleChange(e, fields[0].field)}
                            onBlur={(e) => this.handlePatch(fields[0].field, e.target.value)}
                        />
                    </div>
                )
            case "Number":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            value={data.value}
                            disabled={data.readonly}
                            onChange={(e) => this.handleChange(e, fields[0].field)}
                            onBlur={(e) => this.handlePatch(fields[0].field, e.target.value)}
                        />
                    </div>
                )
            case "Amount" :
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            value={data.value}
                            disabled={data.readonly}
                            onChange={(e) => this.handleChange(e, fields[0].field)}
                            onBlur={(e) => this.handlePatch(fields[0].field, e.target.value)}
                        />
                    </div>
                )
            case "Quantity":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            min="0"
                            step="1"
                            value={data.value}
                            disabled={data.readonly}
                            onChange={(e) => this.handleChange(e, fields[0].field)}
                            onBlur={(e) => this.handlePatch(fields[0].field, e.target.value)}
                        />
                    </div>
                )
            case "CostPrice":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (data.readonly ? "input-disabled " : "") +
                        (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                    }>
                        <input
                            type="number"
                            className="input-field"
                            value={data.value}
                            disabled={data.readonly}
                            onChange={(e) => this.handleChange(e, fields[0].field)}
                            onBlur={(e) => this.handlePatch(fields[0].field, e.target.value)}
                        />
                    </div>
                )
            case "YesNo":
                return (
                    <label
                        className={
                            "input-checkbox"
                        }>
                        <input
                            type="checkbox"
                            value={data.value}
                            disabled={data.readonly}
                            onClick={(e) => this.handlePatch(fields[0].field, e.target.value)}
                        />
                        <div className="input-checkbox-tick"/>
                    </label>
                )
            case "Switch":
                return (
                    <label
                        className={
                            "input-switch" +
                            (data.readonly ? "input-disabled " : "") +
                            (data.mandatory && data.value.length === 0 ? "input-mandatory " : "")
                        }>
                        <input
                            type="checkbox"
                            checked={data.value}
                            disabled={data.readonly}
                            onChange={(e) => this.handlePatch(fields[0].field, e.target.value)}
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
                    <button className="btn btn-sm btn-meta-primary">{data.value}</button>
                )
            default:
                return (
                    <div>{widgetType}</div>
                )
        }
    }
    render() {
        const {caption, widgetType, description, fields, windowType, data, type, noLabel, widgetData, dataId, rowId} = this.props;
        if(widgetData.displayed){
            return (
                <div className="form-group row">
                    <div className="col-xs-12">
                        <div className={"form-group row " + (type === "primary" ? "" : "")}>
                            {!noLabel && <div key="title" className={"form-control-label " + ((type === "primary") ? "col-sm-12 panel-title" : "col-sm-3")}>{caption}</div>}
                            <div className={(type === "primary") ? "col-sm-12 " : "col-sm-9 "}>
                                {this.renderWidget(widgetType, fields, windowType, dataId, type, widgetData)}
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

Widget.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    return {
    }
}

Widget = connect(mapStateToProps)(Widget)

export default Widget
