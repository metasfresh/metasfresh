import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
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

class RawWidget extends Component {
    constructor(props) {
        super(props);

        this.state = {
            textValue: props.selectedItem,
            isEdited: false,
            cachedValue: null
        }
    }

    handleSelectedValue = (item) => {
        const {setSelectedItem} = this.props
        this.setState(Object.assign({}, this.state, {
            textValue: item
        }))

        setSelectedItem(item);
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
        this.setState(Object.assign({}, this.state, {
            isEdited: false
        }));

        this.handlePatch(widgetField, value, id);
    }

    renderWidget = (widgetType, fields, windowType, dataId, type, data, rowId, tabId, icon, align) => {
        const {
            handleChange, handleFocus, updated, isModal, filterWidget,
            filterId, parameterName, setSelectedItem, selectedItem, selectedItemTo, id, range, entity,
            isShown, isHidden, handleBackdropLock, subentity, subentityId, tabIndex, viewId,
            autoFocus
        } = this.props;

        const {textValue, isEdited} = this.state;
        const widgetData = data[0];

        let widgetField = "";
        let selectedField = "";
        let selectedFieldTo = "";
        let widgetFields = "";


        if (filterWidget) {
            widgetField = parameterName;
            selectedField = selectedItem;
            selectedFieldTo = selectedItemTo;
            widgetFields = fields;
        } else {
            widgetField = fields[0].field;
            selectedField = data[0].value;
            widgetFields = fields[0];
        }

        switch(widgetType){
            case "Date":
                if(range){
                    //Watch out! The datetimerange widget as exception, is non-controlled
                    //input! For further usage, needs upgrade.
                    return (
                        <DatetimeRange
                            onChange={(value, valueTo) => this.handlePatch(widgetField, value, valueTo)}
                            mandatory={widgetData.mandatory}
                            isShown={isShown}
                            isHidden={isHidden}
                            value={selectedField}
                            valueTo={selectedFieldTo}
                            tabIndex={tabIndex}
                         />
                    )
                }else{
                    return (
                        <div className={"input-icon-container input-block " +
                            (widgetData.readonly ? "input-disabled " : "") +
                            (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                            (align ? "text-xs-" + align + " " : "") +
                            (type === "primary" ? "input-primary " : "input-secondary ") +
                            (updated ? "pulse-on " : "pulse-off ") +
                            (rowId && !isModal ? "input-table " : "")
                        }>
                            <DatePicker
                                timeFormat={false}
                                dateFormat={true}
                                inputProps={{
                                    placeholder: widgetFields.emptyText,
                                    disabled: widgetData.readonly,
                                    tabIndex: tabIndex
                                }}
                                value={selectedField}
                                onChange={(date) => handleChange(widgetField, date)}
                                patch={(date) => this.handlePatch(widgetField, Moment(date).format('YYYY-MM-DDTHH:mm:ss.SSSZ'))}
                                ref={c => {(c && autoFocus) && c.focus()}}
                            />
                            <i className="meta-icon-calendar input-icon-right"></i>
                        </div>
                    )
                }
            case "DateTime":
                return (
                    <div className={"input-icon-container input-block " +
                        (widgetData.readonly ? "input-disabled " : "") +
                        (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                        (align ? "text-xs-" + align + " " : "") +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        (((rowId && !isModal)) ? "input-table " : "")
                    }>
                        <DatePicker
                            timeFormat={true}
                            dateFormat={true}
                            inputProps={{
                                placeholder: widgetFields.emptyText,
                                disabled: widgetData.readonly,
                                tabIndex: tabIndex
                            }}
                            value={selectedField}
                            onChange={(date) => handleChange(widgetField, date)}
                            patch={(date) => this.handlePatch(widgetField, date ? Moment(date).format('YYYY-MM-DDTHH:mm:ss.SSSZ') : null)}
                            tabIndex={tabIndex}
                            ref={c => {(c && autoFocus) && c.focus()}}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Time":
                return (
                    <div className={"input-icon-container input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (align ? "text-xs-" + align + " " : "") +
                        (widgetData.readonly ? "input-disabled " : "") +
                        (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "")
                    }>
                        <DatePicker
                            timeFormat={true}
                            dateFormat={false}
                            inputProps={{
                                placeholder: widgetFields.emptyText,
                                disabled: widgetData.readonly,
                                tabIndex: tabIndex
                            }}
                            value={selectedField}
                            onChange={(date) => handleChange(widgetField, date)}
                            patch={(date) => this.handlePatch(widgetField, Moment(date).format('YYYY-MM-DDTHH:mm:ss.SSSZ'))}
                            tabIndex={tabIndex}
                            ref={c => {(c && autoFocus) && c.focus()}}
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
                        defaultValue={data}
                        placeholder={widgetFields.emptyText}
                        readonly={widgetData.readonly}
                        mandatory={widgetData.mandatory}
                        rank={type}
                        onChange={this.handlePatch}
                        align={align}
                        isModal={isModal}
                        updated={updated}
                        filterWidget={filterWidget}
                        filterId={filterId}
                        parameterName={parameterName}
                        setSelectedItem={setSelectedItem}
                        selected={selectedField}
                        tabId={tabId}
                        rowId={rowId}
                        tabIndex={tabIndex}
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
                        defaultValue={widgetFields.emptyText}
                        selected={selectedField}
                        properties={fields}
                        readonly={widgetData.readonly}
                        mandatory={widgetData.mandatory}
                        windowType={windowType}
                        rowId={rowId}
                        tabId={tabId}
                        onChange={(option) => this.handlePatch(widgetField, option, id)}
                        align={align}
                        updated={updated}
                        filterWidget={filterWidget}
                        filterId={filterId}
                        parameterName={parameterName}
                        setSelectedItem={setSelectedItem}
                        emptyText={widgetFields.emptyText}
                        tabIndex={tabIndex}
                        viewId={viewId}
                        autoFocus={autoFocus}
                    />
                )
            case "Text":
                return (
                    <div className={
                            "input-block input-icon-container " +
                            (type === "primary" ? "input-primary " : "input-secondary ") +
                            (widgetData.readonly ? "input-disabled " : "") +
                            (align ? "text-xs-" + align + " " : "") +
                            (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                            (updated ? "pulse-on " : "pulse-off ") +
                            ((rowId && !isModal) ? "input-table " : "") +
                            (isEdited ? "input-focused " : "")
                        }
                    >
                        <input
                            type="text"
                            ref={c => this.input = c}
                            className="input-field js-input-field"
                            value={selectedField}
                            placeholder={widgetFields.emptyText}
                            disabled={widgetData.readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={tabIndex}
                            ref={c => {(c && autoFocus) && c.focus()}}
                        />
                        {icon && <i className="meta-icon-edit input-icon-right"></i>}
                    </div>
                )
            case "LongText":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (align ? "text-xs-" + align + " " : "") +
                        (widgetData.readonly ? "input-disabled " : "") +
                        (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <textarea
                            className="input-field js-input-field"
                            value={filterWidget ? textValue : selectedField}
                            disabled={widgetData.readonly}
                            placeholder={widgetFields.emptyText}
                            onFocus={this.handleFocus}
                            onChange={filterWidget ? (e) => this.handleSelectedValue(e.target.value) : (e) => handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={tabIndex}
                            ref={c => {(c && autoFocus) && c.focus()}}
                        />
                    </div>
                )
            case "Integer":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (align ? "text-xs-" + align + " " : "") +
                        (widgetData.readonly ? "input-disabled " : "") +
                        (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                        (updated ? " pulse-on" : " pulse-off") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            type="number"
                            className="input-field js-input-field"
                            min="0"
                            step="1"
                            value={selectedField}
                            disabled={widgetData.readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={tabIndex}
                            ref={c => {autoFocus && c.focus()}}
                        />
                    </div>
                )
            case "Number":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (align ? "text-xs-" + align + " " : "") +
                        (widgetData.readonly ? "input-disabled " : "") +
                        (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            type="number"
                            className="input-field js-input-field"
                            value={selectedField}
                            disabled={widgetData.readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange(widgetFields.field, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={tabIndex}
                            ref={c => {(c && autoFocus) && c.focus()}}
                        />
                    </div>
                )
            case "Amount" :
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (align ? "text-xs-" + align + " " : "") +
                        (widgetData.readonly ? "input-disabled " : "") +
                        (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            type="number"
                            className="input-field js-input-field"
                            min="0"
                            step="1"
                            value={selectedField}
                            disabled={widgetData.readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={tabIndex}
                            ref={c => {(c && autoFocus) && c.focus()}}
                        />
                    </div>
                )
            case "Quantity":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (align ? "text-xs-" + align + " " : "") +
                        (widgetData.readonly ? "input-disabled " : "") +
                        (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            type="number"
                            className="input-field js-input-field"
                            min="0"
                            step="1"
                            value={selectedField}
                            disabled={widgetData.readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={tabIndex}
                            ref={c => {(c && autoFocus) && c.focus()}}
                        />
                    </div>
                )
            case "CostPrice":
                return (
                    <div className={
                        "input-block " +
                        (type === "primary" ? "input-primary " : "input-secondary ") +
                        (align ? "text-xs-" + align + " " : "") +
                        (widgetData.readonly ? "input-disabled " : "") +
                        (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "") +
                        (updated ? "pulse-on " : "pulse-off ") +
                        ((rowId && !isModal) ? "input-table " : "") +
                        (isEdited ? "input-focused " : "")
                    }>
                        <input
                            type="number"
                            className="input-field js-input-field"
                            value={selectedField}
                            disabled={widgetData.readonly}
                            onFocus={this.handleFocus}
                            onChange={(e) => handleChange(widgetField, e.target.value)}
                            onBlur={(e) => this.handleBlur(widgetField, e.target.value, id)}
                            tabIndex={tabIndex}
                            ref={c => {(c && autoFocus) && c.focus()}}
                        />
                    </div>
                )
            case "YesNo":
                return (
                    <label
                        className={
                            "input-checkbox " +
                            (widgetData.readonly ? "input-disabled " : "")
                        }
                        tabIndex={tabIndex}
                        ref={c => this.input = c}
                        onKeyDown={e => {
                            if(e.key === " "){
                                e.preventDefault();
                                this.checkbox.click();
                            }
                        }}
                    >
                        <input
                            type="checkbox"
                            checked={selectedField}
                            disabled={widgetData.readonly}
                            onChange={(e) => this.handlePatch(widgetField, e.target.checked, id)}
                            tabIndex="-1"
                            ref={c => {(c && autoFocus) && c.focus()}}
                        />
                        <div className={"input-checkbox-tick"}/>
                    </label>
                )
            case "Switch":
                return (
                    <label
                        className={
                            "input-switch " +
                            (widgetData.readonly ? "input-disabled " : "") +
                            (widgetData.mandatory && widgetData.value.length === 0 ? "input-mandatory " : "")
                        }
                        tabIndex={tabIndex}
                        ref={c => {(c && autoFocus) && c.focus()}}
                    >
                        <input
                            type="checkbox"
                            checked={selectedField}
                            disabled={widgetData.readonly}
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
                            (align ? "text-xs-" + align + " " : "")
                        }
                        tabIndex={tabIndex}
                        ref={c => {(c && autoFocus) && c.focus()}}
                    >
                        {widgetData.value}
                    </div>
                )
            case "Button":
                return (
                    <button
                        className={
                            "btn btn-sm btn-meta-primary " +
                            (align ? "text-xs-" + align + " " : "")
                        }
                        onClick={(e) => this.handlePatch(widgetField)}
                        tabIndex={tabIndex}
                        ref={c => {(c && autoFocus) && c.focus()}}
                    >
                        {widgetData.value[Object.keys(widgetData.value)[0]]}
                    </button>
                )
            case "ActionButton":
                return (
                    <ActionButton
                        data={widgetData}
                        windowType={windowType}
                        fields={fields}
                        dataId={dataId}
                        onChange={(option) => this.handlePatch(fields[1].field, option)}
                        tabIndex={tabIndex}
                        ref={c => {(c && autoFocus) && c.focus()}}
                    />
                )
            case "ProductAttributes":
                return (
                    <Attributes
                        attributeType='pattribute'
                        fields={fields}
                        dataId={dataId}
                        widgetData={widgetData}
                        docType={windowType}
                        tabId={tabId}
                        rowId={rowId}
                        fieldName={widgetField}
                        handleBackdropLock={handleBackdropLock}
                        patch={(option) => this.handlePatch(widgetField, option)}
                        tabIndex={tabIndex}
                        autoFocus={autoFocus}
                    />
                )
            case "Address":
                return (
                    <Attributes
                        attributeType='address'
                        fields={fields}
                        dataId={dataId}
                        widgetData={widgetData}
                        docType={windowType}
                        tabId={tabId}
                        rowId={rowId}
                        fieldName={widgetField}
                        handleBackdropLock={handleBackdropLock}
                        patch={(option) => this.handlePatch(widgetField, option)}
                        tabIndex={tabIndex}
                        autoFocus={autoFocus}
                    />
                )
            default:
                return (
                    <div>{widgetType}</div>
                )
        }
    }
    render() {
        const {
            caption, widgetType, description, fields, windowType, type, noLabel,
            widgetData, dataId, rowId, tabId, icon, gridAlign, updated, isModal,
            tabIndex
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
                        className={(type === "primary" || noLabel) ? "col-sm-12 " : "col-sm-9 "}
                    >
                        {this.renderWidget(
                            widgetType, fields, windowType, dataId, type, widgetData,
                            rowId, tabId, icon, gridAlign
                        )}
                    </div>
                </div>
            )
        }else{
            return false;
        }
    }
}

RawWidget.propTypes = {
    dispatch: PropTypes.func.isRequired
};

RawWidget = connect()(RawWidget)

export default RawWidget
