import React, { Component } from 'react';
import Moment from 'moment';

import {
    patch,
    updateProperty,
    findRowByPropName
} from '../../actions/WindowActions';

import DatePicker from './DatePicker';
import Attributes from './Attributes/Attributes';
import Lookup from './Lookup/Lookup';
import DatetimeRange from './DatetimeRange';
import List from './List/List';
import ActionButton from './ActionButton';
import Image from './Image';
import DevicesWidget from './Devices/DevicesWidget';

class RawWidget extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isEdited: false,
            cachedValue: null
        }
    }

    componentDidMount(){
        const {autoFocus} = this.props
        if(this.rawWidget && autoFocus){
            this.rawWidget.focus();
        }
    }

    handleFocus = (e) => {
        const {handleFocus} = this.props;

        this.setState(Object.assign({}, this.state, {
            isEdited: true,
            cachedValue: e.target.value
        }));

        handleFocus && handleFocus();
    }

    handlePatch = (property, value, id) => {
        const {handlePatch, widgetData} = this.props;
        const {cachedValue} = this.state;
        let ret = null;

        //do patch only when value is not equal state
        //or cache is set and it is not equal value

        if( JSON.stringify(widgetData[0].value) !== JSON.stringify(value) ||
            (cachedValue !== null && (JSON.stringify(cachedValue) !== JSON.stringify(value)))){

            if(handlePatch) {
                ret = handlePatch(property, value, id);
            }
        }

        if(ret){
            this.setState(Object.assign({}, this.state, {
                cachedValue: null
            }));
        }

        return ret;
    }

    handleBlur = (widgetField, value, id) => {
        const {handleBlur} = this.props;

        handleBlur && handleBlur();

        this.setState(Object.assign({}, this.state, {
            isEdited: false
        }));

        this.handlePatch(widgetField, value, id);
    }

    renderWidget = () => {
        const {
            handleChange, handleFocus, updated, isModal, filterWidget, filterId,
            id, range, entity, onShow,
            onHide, handleBackdropLock, subentity, subentityId, tabIndex, viewId,
            dropdownOpenCallback, autoFocus, fullScreen, widgetType, fields,
            windowType, dataId, type, widgetData, rowId, tabId, icon, gridAlign
        } = this.props;

        const {isEdited} = this.state;

        // TODO: API SHOULD RETURN THE SAME PROPERTIES FOR FILTERS
        const widgetField = filterWidget ? fields[0].parameterName : fields[0].field;

        switch(widgetType){
            case "Date":
                if(range){
                    //Watch out! The datetimerange widget as exception, is non-controlled
                    //input! For further usage, needs upgrade.
                    return (
                        <DatetimeRange
                            onChange={(value, valueTo) =>
                                this.handlePatch(widgetField, value, valueTo)
                            }
                            mandatory={widgetData[0].mandatory}
                            onShow={onShow}
                            onHide={onHide}
                            value={widgetData[0].value}
                            valueTo={widgetData[0].valueTo}
                            tabIndex={fullScreen ? -1 : tabIndex}
                         />
                    )
                }else{
                    return (
                        <div className={"input-icon-container input-block " +
                            (widgetData[0].readonly ? "input-disabled " : "") +
                            (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                            (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                            (type === "primary" ? "input-primary " : "input-secondary ") +
                            (updated ? "pulse-on " : "pulse-off ") +
                            (rowId && !isModal ? "input-table " : "")
                        }>
                            <DatePicker
                                ref={c => this.rawWidget = c}
                                timeFormat={false}
                                dateFormat={true}
                                inputProps={{
                                    placeholder: fields[0].emptyText,
                                    disabled: widgetData[0].readonly,
                                    tabIndex: fullScreen ? -1 : tabIndex
                                }}
                                value={widgetData[0].value}
                                onChange={(date) => handleChange(widgetField, date)}
                                patch={(date) => this.handlePatch(widgetField, date ? Moment(date).format('YYYY-MM-DDTHH:mm:ss.SSSZ') : null)}
                                handleBackdropLock={handleBackdropLock}
                            />
                            <i className="meta-icon-calendar input-icon-right"></i>
                        </div>
                    )
                }
            case "DateTime":
                return (
                    <div className={"input-icon-container input-block " +
                        (widgetData[0].readonly ? "input-disabled " : "") +
                        (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                        (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        (((rowId && !isModal)) ? "input-table " : "")
                    }>
                        <DatePicker
                            ref={c => this.rawWidget = c}
                            timeFormat={true}
                            dateFormat={true}
                            inputProps={{
                                placeholder: fields[0].emptyText,
                                disabled: widgetData[0].readonly,
                                tabIndex: fullScreen ? -1 : tabIndex
                            }}
                            value={widgetData[0].value}
                            onChange={(date) => handleChange(widgetField, date)}
                            patch={(date) => this.handlePatch(widgetField, date ? Moment(date).format('YYYY-MM-DDTHH:mm:ss.SSSZ') : null)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                            handleBackdropLock={handleBackdropLock}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Time":
                return (
                    <div className={"input-icon-container input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                        (widgetData[0].readonly ? "input-disabled " : "") +
                        (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "")
                    }>
                        <DatePicker
                            ref={c => this.rawWidget = c}
                            timeFormat={true}
                            dateFormat={false}
                            inputProps={{
                                placeholder: fields[0].emptyText,
                                disabled: widgetData[0].readonly,
                                tabIndex: fullScreen ? -1 : tabIndex
                            }}
                            value={widgetData[0].value}
                            onChange={(date) => handleChange(widgetField, date)}
                            patch={(date) => this.handlePatch(widgetField, date ? Moment(date).format('YYYY-MM-DDTHH:mm:ss.SSSZ') : null)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                            handleBackdropLock={handleBackdropLock}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Lookup":
                return (
                    <Lookup
                        entity={entity}
                        subentity={subentity}
                        subentityId={subentityId}
                        recent={[]}
                        dataId={dataId}
                        properties={fields}
                        windowType={windowType}
                        defaultValue={widgetData}
                        placeholder={fields[0].emptyText}
                        readonly={widgetData[0].readonly}
                        mandatory={widgetData[0].mandatory}
                        rank={type}
                        onChange={this.handlePatch}
                        align={gridAlign}
                        isModal={isModal}
                        updated={updated}
                        filterWidget={filterWidget}
                        filterId={filterId}
                        parameterName={fields[0].parameterName}
                        selected={widgetData[0].value}
                        tabId={tabId}
                        rowId={rowId}
                        tabIndex={fullScreen ? -1 : tabIndex}
                        viewId={viewId}
                        autoFocus={autoFocus}
                    />
                )
            case "List":
                return (
                    <List
                        dataId={dataId}
                        entity={entity}
                        subentity={subentity}
                        subentityId={subentityId}
                        defaultValue={fields[0].emptyText}
                        selected={widgetData[0].value}
                        properties={fields}
                        readonly={widgetData[0].readonly}
                        mandatory={widgetData[0].mandatory}
                        windowType={windowType}
                        rowId={rowId}
                        tabId={tabId}
                        onChange={(option) => this.handlePatch(widgetField, option, id)}
                        align={gridAlign}
                        updated={updated}
                        filterWidget={filterWidget}
                        filterId={filterId}
                        parameterName={fields[0].parameterName}
                        emptyText={fields[0].emptyText}
                        tabIndex={fullScreen ? -1 : tabIndex}
                        viewId={viewId}
                        autoFocus={autoFocus}
                    />
                )
            case "Text":
                return (
                    <div className={
                            "input-block input-icon-container " +
                            (type === "primary" ? "input-primary " : "input-secondary ") +
                            (widgetData[0].readonly ? "input-disabled " : "") +
                            (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                            (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                            (updated ? "pulse-on " : "pulse-off ") +
                            ((rowId && !isModal) ? "input-table " : "") +
                            (isEdited ? "input-focused " : "")
                        }
                    >
                        <input
                            type="text"
                            ref={c => this.rawWidget = c}
                            className="input-field js-input-field"
                            value={widgetData[0].value}
                            placeholder={fields[0].emptyText}
                            disabled={widgetData[0].readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange && handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                        />
                        {icon && <i className="meta-icon-edit input-icon-right"></i>}
                    </div>
                )
            case "LongText":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                        (widgetData[0].readonly ? "input-disabled " : "") +
                        (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <textarea
                            ref={c => this.rawWidget = c}
                            className="input-field js-input-field"
                            value={widgetData[0].value}
                            disabled={widgetData[0].readonly}
                            placeholder={fields[0].emptyText}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                        />
                    </div>
                )
            case "Integer":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                        (widgetData[0].readonly ? "input-disabled " : "") +
                        (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                        (updated ? " pulse-on" : " pulse-off") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            ref={c => this.rawWidget = c}
                            type="number"
                            className="input-field js-input-field"
                            min="0"
                            step="1"
                            value={widgetData[0].value}
                            disabled={widgetData[0].readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange && handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                        />
                    </div>
                )
            case "Number":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                        (widgetData[0].readonly ? "input-disabled " : "") +
                        (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            ref={c => this.rawWidget = c}
                            type="number"
                            className="input-field js-input-field"
                            value={widgetData[0].value}
                            disabled={widgetData[0].readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange && handleChange(fields[0].field, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                        />
                    </div>
                )
            case "Amount" :
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                        (widgetData[0].readonly ? "input-disabled " : "") +
                        (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            ref={c => this.rawWidget = c}
                            type="number"
                            className="input-field js-input-field"
                            min="0"
                            step="1"
                            value={widgetData[0].value}
                            disabled={widgetData[0].readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) =>  handleChange && handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                        />
                    </div>
                )
            case "Quantity":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                        (widgetData[0].readonly ? "input-disabled " : "") +
                        (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            ref={c => this.rawWidget = c}
                            type="number"
                            className="input-field js-input-field"
                            min="0"
                            step="1"
                            value={widgetData[0].value}
                            disabled={widgetData[0].readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) =>  handleChange && handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                        />
                    </div>
                )
            case "CostPrice":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                        (widgetData[0].readonly ? "input-disabled " : "") +
                        (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            ref={c => this.rawWidget = c}
                            type="number"
                            className="input-field js-input-field"
                            value={widgetData[0].value}
                            disabled={widgetData[0].readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) =>  handleChange && handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={fullScreen ? -1 : tabIndex}
                        />
                    </div>
                )
            case "YesNo":
                return (
                    <label
                        className={
                            "input-checkbox " +
                            (widgetData[0].readonly ? "input-disabled " : "")
                        }
                        tabIndex={fullScreen ? -1 : tabIndex}
                        ref={c => this.rawWidget = c}
                        onKeyDown={e => {
                            if(e.key === " "){
                                e.preventDefault();
                                this.checkbox.click();
                            }
                        }}
                    >
                        <input
                            ref={c => this.rawWidget = c}
                            type="checkbox"
                            checked={widgetData[0].value}
                            disabled={widgetData[0].readonly}
                            onChange={(e) => this.handlePatch(widgetField, e.target.checked, id)}
                            tabIndex="-1"
                        />
                        <div className={"input-checkbox-tick"}/>
                    </label>
                )
            case "Switch":
                return (
                    <label
                        className={
                            "input-switch " +
                            (widgetData[0].readonly ? "input-disabled " : "") +
                            (widgetData[0].mandatory && widgetData[0].value.length === 0 ? "input-mandatory " : "")
                        }
                        tabIndex={fullScreen ? -1 : tabIndex}
                        ref={c => {(c && autoFocus) && c.focus()}}
                    >
                        <input
                            type="checkbox"
                            checked={widgetData[0].value}
                            disabled={widgetData[0].readonly}
                            tabIndex="-1"
                            onChange={(e) => this.handlePatch(widgetField, e.target.checked, id)}
                        />
                        <div className={"input-slider"} />
                    </label>
                )
            case "Label":
                return (
                    <div
                        className={
                            "tag tag-warning " +
                            (gridAlign ? "text-xs-" + gridAlign + " " : "")
                        }
                        tabIndex={fullScreen ? -1 : tabIndex}
                        ref={c => {(c && autoFocus) && c.focus()}}
                    >
                        {widgetData[0].value}
                    </div>
                )
            case "Button":
                return (
                    <button
                        className={
                            "btn btn-sm btn-meta-primary " +
                            (gridAlign ? "text-xs-" + gridAlign + " " : "") +
                            (widgetData[0].readonly ? "tag-disabled disabled " : "")
                        }
                        onClick={(e) => this.handlePatch(widgetField)}
                        tabIndex={fullScreen ? -1 : tabIndex}
                        ref={c => this.rawWidget = c}
                    >
                        {widgetData[0].value[Object.keys(widgetData[0].value)[0]]}
                    </button>
                )
            case "ActionButton":
                return (
                    <ActionButton
                        data={widgetData[0]}
                        windowType={windowType}
                        fields={fields}
                        dataId={dataId}
                        onChange={(option) => this.handlePatch(fields[1].field, option)}
                        tabIndex={fullScreen ? -1 : tabIndex}
                        dropdownOpenCallback={dropdownOpenCallback}
                        ref={c => this.rawWidget = c}
                    />
                )
            case "ProductAttributes":
                return (
                    <Attributes
                        attributeType='pattribute'
                        fields={fields}
                        dataId={dataId}
                        widgetData={widgetData[0]}
                        docType={windowType}
                        tabId={tabId}
                        rowId={rowId}
                        fieldName={widgetField}
                        handleBackdropLock={handleBackdropLock}
                        patch={(option) => this.handlePatch(widgetField, option)}
                        tabIndex={fullScreen ? -1 : tabIndex}
                        autoFocus={autoFocus}
                        readonly={widgetData[0].readonly}
                    />
                )
            case "Address":
                return (
                    <Attributes
                        attributeType='address'
                        fields={fields}
                        dataId={dataId}
                        widgetData={widgetData[0]}
                        docType={windowType}
                        tabId={tabId}
                        rowId={rowId}
                        fieldName={widgetField}
                        handleBackdropLock={handleBackdropLock}
                        patch={(option) => this.handlePatch(widgetField, option)}
                        tabIndex={fullScreen ? -1 : tabIndex}
                        autoFocus={autoFocus}
                        readonly={widgetData[0].readonly}
                    />
                )
            case "Image":
                return <Image
                    fields={fields}
                    data={widgetData[0]}
                    handlePatch={this.handlePatch}
                />;
            default:
                return (
                    <div>{widgetType}</div>
                )
        }
    }

    render() {
        const {
            caption, widgetType, description, fields, windowType, type, noLabel,
            widgetData, dataId, rowId, tabId, icon, updated, isModal,
            tabIndex, handlePatch
        } = this.props;


        if(widgetData[0].displayed && widgetData[0].displayed === true){
            return (
                <div className={
                    "form-group row " +
                    ((rowId && !isModal) ? "form-group-table " : " ")
                }>
                    {(!noLabel && caption) &&
                        <div
                            key="title"
                            className={
                                "form-control-label " +
                                ((type === "primary") ? "col-sm-12 panel-title" : "col-sm-3")
                            }
                            title={caption}
                        >
                            {caption}
                        </div>
                    }
                    <div
                        className={
                            ((type === "primary" || noLabel) ? "col-sm-12 " : "col-sm-9 ") +
                            (fields[0].devices ? "form-group-flex ": "")
                        }
                    >
                        {this.renderWidget()}
                        
                        {fields[0].devices && !widgetData[0].readonly &&
                            <DevicesWidget
                                devices={fields[0].devices}
                                tabIndex={1}
                                handleChange={(value) =>
                                    handlePatch && handlePatch(fields[0].field, value)
                                }
                            />
                        }
                    </div>
                </div>
            )
        }else{
            return false;
        }
    }
}

export default RawWidget;
