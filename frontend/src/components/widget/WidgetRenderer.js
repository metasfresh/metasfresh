import React, { ForwardRef, PureComponent } from 'react';
import Moment from 'moment';
import classnames from 'classnames';
import { get } from 'lodash';

import {
  DATE_FORMAT,
  TIME_FORMAT,
  DATE_TIMEZONE_FORMAT,
  DATE_FIELD_FORMATS,
} from '../../constants/Constants';
import { getClassNames, generateMomentObj } from '../../utils/widgetHelpers';

import ActionButton from './ActionButton';
import Attributes from './Attributes/Attributes';
import Checkbox from './Checkbox';
import DatePicker from './DatePicker';
import DatetimeRange from './DatetimeRange';
import DevicesWidget from './Devices/DevicesWidget';
import Image from './Image';
// import Tooltips from '../tooltips/Tooltips';
import Labels from './Labels';
import Link from './Link';
import CharacterLimitInfo from './CharacterLimitInfo';
import List from './List/List';
import Lookup from './Lookup/Lookup';

class WidgetRenderer extends PureComponent {
  constructor(props) {
    super(props);

    this.getClassNames = getClassNames.bind(this);
    this.generateMomentObj = generateMomentObj.bind(this);
  }

  render() {
    const {
      handleChange,
      updated,
      isModal,
      filterWidget,
      filterId,
      id,
      range,
      onHide,
      handleBackdropLock,
      subentity,
      widgetType,
      subentityId,
      dropdownOpenCallback,
      autoFocus,
      fullScreen,
      fields,
      windowType,
      dataId,
      type,
      widgetData,
      rowId,
      tabId,
      docId,
      activeTab,
      icon,
      gridAlign,
      entity,
      onShow,
      caption,
      viewId,
      listenOnKeys,
      listenOnKeysFalse,
      closeTableField,
      handleZoomInto,
      attribute,
      allowShowPassword,
      onBlurWidget,
      isOpenDatePicker,
      dateFormat,
      initialFocus,
      timeZone,
      fieldName,
      maxLength,
      updateHeight,
      rowIndex,

      // from `renderWidget`
      isMultiselect,
      widgetField,
      widgetProperties,
      selectedValue,
      showErrorBorder,
      isFocused,
      charsTyped,
      readonly,
      onPatch,
      onFocus,
      onBlur,
    } = this.props;
    const { tabIndex } = widgetProperties;
    const widgetValue = get(widgetProperties, ['value'], null);

    switch (widgetType) {
      case 'Date':
        if (range) {
          // TODO: Watch out! The datetimerange widget as exception,
          // is non-controlled input! For further usage, needs
          // upgrade.
          return (
            <DatetimeRange
              onChange={(value, valueTo) =>
                onPatch(
                  widgetField,
                  value ? Moment(value).format(DATE_FORMAT) : null,
                  null,
                  valueTo ? Moment(valueTo).format(DATE_FORMAT) : null
                )
              }
              field={widgetField}
              mandatory={widgetData[0].mandatory}
              validStatus={widgetData[0].validStatus}
              value={widgetData[0].value}
              valueTo={widgetData[0].valueTo}
              {...{
                tabIndex,
                onShow,
                onHide,
                timeZone,
              }}
            />
          );
        } else {
          return (
            <div className={this.getClassNames({ icon: true })}>
              <DatePicker
                key={1}
                field={widgetField}
                timeFormat={false}
                dateFormat={dateFormat || true}
                inputProps={{
                  placeholder: fields[0].emptyText,
                  disabled: readonly,
                  tabIndex: tabIndex,
                }}
                value={widgetValue || widgetData[0].value}
                onChange={(date) => handleChange(widgetField, date)}
                patch={(date) =>
                  onPatch(
                    widgetField,
                    this.generateMomentObj(date, DATE_FORMAT),
                    null,
                    null,
                    true
                  )
                }
                handleChange={handleChange}
                {...{
                  handleBackdropLock,
                  isOpenDatePicker,
                  timeZone,
                }}
              />
            </div>
          );
        }
      case 'ZonedDateTime':
        return (
          <div className={this.getClassNames({ icon: true })}>
            <DatePicker
              key={1}
              field={widgetField}
              timeFormat={true}
              dateFormat={dateFormat || true}
              hasTimeZone={true}
              inputProps={{
                placeholder: fields[0].emptyText,
                disabled: readonly,
                tabIndex: tabIndex,
              }}
              value={widgetValue || widgetData[0].value}
              onChange={(date) => handleChange(widgetField, date)}
              patch={(date) =>
                onPatch(
                  widgetField,
                  this.generateMomentObj(date, DATE_TIMEZONE_FORMAT),
                  null,
                  null,
                  true
                )
              }
              handleChange={handleChange}
              {...{
                handleBackdropLock,
                isOpenDatePicker,
                timeZone,
              }}
            />
          </div>
        );
      case 'Time':
        return (
          <div className={this.getClassNames({ icon: true })}>
            <DatePicker
              field={widgetField}
              timeFormat={TIME_FORMAT}
              dateFormat={false}
              inputProps={{
                placeholder: fields[0].emptyText,
                disabled: readonly,
                tabIndex: tabIndex,
              }}
              value={this.generateMomentObj(widgetValue, TIME_FORMAT)}
              onChange={(date) => handleChange(widgetField, date)}
              patch={(date) =>
                onPatch(
                  widgetField,
                  this.generateMomentObj(date, TIME_FORMAT),
                  null,
                  null,
                  true
                )
              }
              tabIndex={tabIndex}
              handleChange={handleChange}
              handleBackdropLock={handleBackdropLock}
            />
          </div>
        );
      case 'Timestamp':
        return (
          <div className={this.getClassNames({ icon: true })}>
            <DatePicker
              field={widgetField}
              timeFormat={false}
              dateFormat={DATE_FIELD_FORMATS[widgetType]}
              inputProps={{
                placeholder: fields[0].emptyText,
                disabled: readonly,
                tabIndex: tabIndex,
              }}
              value={widgetValue}
              onChange={(date) => handleChange(widgetField, date)}
              patch={(date) =>
                onPatch(
                  widgetField,
                  this.generateMomentObj(date, `x`),
                  null,
                  null,
                  true
                )
              }
              tabIndex={tabIndex}
              handleChange={handleChange}
              handleBackdropLock={handleBackdropLock}
            />
          </div>
        );
      case 'DateRange': {
        return (
          <DatetimeRange
            onChange={(value, valueTo) => {
              const val = Moment(value).format(DATE_FORMAT);
              const valTo = Moment(valueTo).format(DATE_FORMAT);

              onPatch(widgetField, {
                ...(val && { value: val }),
                ...(valTo && { valueTo: valTo }),
              });
            }}
            mandatory={widgetData[0].mandatory}
            validStatus={widgetData[0].validStatus}
            onShow={onShow}
            onHide={onHide}
            value={widgetData[0].value}
            valueTo={widgetData[0].valueTo}
            tabIndex={tabIndex}
          />
        );
      }
      case 'Lookup':
        return (
          <Lookup
            {...{
              attribute,
              // recent
            }}
            entity={entity}
            subentity={subentity}
            subentityId={subentityId}
            dataId={dataId}
            properties={fields}
            windowType={windowType}
            widgetData={widgetData}
            placeholder={
              this.props.emptyText
                ? this.props.emptyText
                : this.props.fields[0].emptyText
            }
            readonly={readonly}
            mandatory={widgetData[0].mandatory}
            rank={type}
            align={gridAlign}
            isModal={isModal}
            updated={updated}
            filterWidget={filterWidget}
            filterId={filterId}
            parameterName={fields[0].parameterName}
            selected={widgetValue}
            tabId={tabId}
            rowId={rowId}
            tabIndex={tabIndex}
            viewId={viewId}
            autoFocus={autoFocus}
            initialFocus={initialFocus}
            forceFullWidth={this.props.forceFullWidth}
            forceHeight={this.props.forceHeight}
            validStatus={widgetData[0].validStatus}
            newRecordCaption={fields[0].newRecordCaption}
            newRecordWindowId={fields[0].newRecordWindowId}
            listenOnKeys={listenOnKeys}
            listenOnKeysFalse={listenOnKeysFalse}
            closeTableField={closeTableField}
            onFocus={this.focus}
            onBlur={this.handleBlurWithParams}
            onChange={onPatch}
            onBlurWidget={onBlurWidget}
            onClickOutside={this.props.onClickOutside}
          />
        );
      case 'List':
        return (
          <List
            {...{
              attribute,
            }}
            widgetField={widgetField}
            dataId={dataId}
            entity={entity}
            subentity={subentity}
            subentityId={subentityId}
            defaultValue={fields[0].emptyText}
            selected={selectedValue}
            properties={fields[0]}
            readonly={readonly}
            mandatory={widgetData[0].mandatory}
            windowType={windowType}
            rowId={rowId}
            tabId={tabId}
            onFocus={this.focus}
            onBlur={this.handleBlurWithParams}
            onChange={onPatch}
            align={gridAlign}
            updated={updated}
            filterWidget={filterWidget}
            filterId={filterId}
            parameterName={fields[0].parameterName}
            emptyText={fields[0].emptyText}
            tabIndex={tabIndex}
            viewId={viewId}
            autoFocus={autoFocus}
            validStatus={widgetData[0].validStatus}
            isMultiselect={isMultiselect}
          />
        );

      case 'MultiListValue':
        return (
          <List
            {...{
              attribute,
            }}
            widgetField={widgetField}
            dataId={dataId}
            entity={entity}
            subentity={subentity}
            subentityId={subentityId}
            defaultValue={fields[0].emptyText}
            selected={widgetData[0].value || null}
            properties={fields[0]}
            readonly={readonly}
            mandatory={widgetData[0].mandatory}
            windowType={windowType}
            rowId={rowId}
            tabId={tabId}
            onFocus={this.focus}
            onBlur={this.handleBlurWithParams}
            onChange={onPatch}
            align={gridAlign}
            updated={updated}
            filterWidget={filterWidget}
            filterId={filterId}
            parameterName={fields[0].parameterName}
            emptyText={fields[0].emptyText}
            tabIndex={tabIndex}
            viewId={viewId}
            autoFocus={autoFocus}
            validStatus={widgetData[0].validStatus}
            isMultiselect={true}
          />
        );
      case 'Link':
        return (
          <Link
            getClassNames={() => this.getClassNames({ icon: true })}
            {...{
              isFocused,
              widgetProperties,
              icon,
              widgetData,
              tabIndex,
              fullScreen,
            }}
          />
        );
      case 'Text':
        return (
          <div>
            <div
              className={classnames(
                this.getClassNames({
                  icon: true,
                }),
                {
                  'input-focused': isFocused,
                  'border-danger': showErrorBorder,
                }
              )}
            >
              <input {...widgetProperties} type="text" />
              {icon && <i className="meta-icon-edit input-icon-right" />}
            </div>
            {charsTyped && charsTyped[fieldName] >= 0 && (
              <CharacterLimitInfo
                charsTyped={charsTyped[fieldName]}
                maxLength={maxLength}
              />
            )}
          </div>
        );
      case 'LongText':
        return (
          <div>
            <div
              className={classnames(
                this.getClassNames({
                  icon: false,
                  forcedPrimary: true,
                }),
                {
                  'input-focused': isFocused,
                  'border-danger': showErrorBorder,
                }
              )}
            >
              <textarea {...widgetProperties} />
            </div>
            {charsTyped && charsTyped[fieldName] >= 0 && (
              <CharacterLimitInfo
                charsTyped={charsTyped[fieldName]}
                maxLength={maxLength}
              />
            )}
          </div>
        );
      case 'Password':
        return (
          <div className="input-inner-container">
            <div
              className={classnames(
                this.getClassNames({
                  icon: true,
                }),
                {
                  'input-focused': isFocused,
                }
              )}
            >
              <input {...widgetProperties} type="password" ref={this.props.innerRef} />
              {icon && <i className="meta-icon-edit input-icon-right" />}
            </div>
            {allowShowPassword && (
              <div
                onMouseDown={() => {
                  this.rawWidget.type = 'text';
                }}
                onMouseUp={() => {
                  this.rawWidget.type = 'password';
                }}
                className="btn btn-icon btn-meta-outline-secondary btn-inline pointer btn-distance-rev btn-sm"
              >
                <i className="meta-icon-show" />
              </div>
            )}
          </div>
        );
      case 'Integer':
      case 'Amount':
      case 'Quantity':
        return (
          <div
            className={classnames(this.getClassNames(), 'number-field', {
              'input-focused': isFocused,
            })}
          >
            <input
              {...widgetProperties}
              type="number"
              min={0}
              precision={widgetField === 'CableLength' ? 2 : 1}
              step={subentity === 'quickInput' ? 'any' : 1}
            />
            {widgetData[0].devices && (
              <div className="device-widget-wrapper">
                <DevicesWidget
                  devices={widgetData[0].devices}
                  tabIndex={1}
                  handleChange={(value) =>
                    onPatch && onPatch(fields[0].field, value)
                  }
                />
              </div>
            )}
          </div>
        );
      case 'Number':
      case 'CostPrice':
        return (
          <div
            className={classnames(this.getClassNames(), 'number-field', {
              'input-focused': isFocused,
            })}
          >
            <input {...widgetProperties} type="number" />
          </div>
        );
      case 'YesNo':
        return (
          <Checkbox
            {...{
              widgetData,
              disabled: readonly,
              fullScreen,
              tabIndex,
              widgetField,
              id,
              filterWidget,
            }}
            handlePatch={onPatch}
          />
        );
      case 'Switch':
        return (
          <label
            className={classnames('input-switch', {
              'input-disabled': readonly,
              'input-mandatory':
                widgetData[0].mandatory && widgetData[0].value.length === 0,
              'input-error':
                widgetData[0].validStatus && !widgetData[0].validStatus.valid,
              'input-table': rowId && !isModal,
            })}
            tabIndex={tabIndex}
            ref={this.props.innerRef}
            onKeyDown={(e) => {
              e.key === ' ' &&
                onPatch(widgetField, !widgetData[0].value, id);
            }}
          >
            <input
              type="checkbox"
              checked={widgetData[0].value}
              disabled={readonly}
              tabIndex="-1"
              onChange={(e) =>
                onPatch(widgetField, e.target.checked, id)
              }
            />
            <div className="input-slider" />
          </label>
        );
      case 'Label':
        return (
          <div
            className={classnames('tag tag-warning ', {
              [`text-${gridAlign}`]: gridAlign,
            })}
            tabIndex={tabIndex}
            ref={this.props.innerRef}
          >
            {widgetData[0].value}
          </div>
        );
      case 'Button':
        return (
          <button
            className={
              'btn btn-sm btn-meta-primary ' +
              (gridAlign ? 'text-' + gridAlign + ' ' : '') +
              (readonly ? 'tag-disabled disabled ' : '')
            }
            onClick={() => onPatch(widgetField)}
            tabIndex={tabIndex}
            ref={this.props.innerRef}
          >
            {widgetData[0].value &&
              widgetData[0].value[Object.keys(widgetData[0].value)[0]]}
          </button>
        );
      case 'ProcessButton':
        return (
          <button
            className={
              'btn btn-sm btn-meta-primary ' +
              (gridAlign ? 'text-' + gridAlign + ' ' : '') +
              (readonly ? 'tag-disabled disabled ' : '')
            }
            onClick={this.handleProcess}
            tabIndex={tabIndex}
            ref={this.props.innerRef}
          >
            {caption}
          </button>
        );
      case 'ActionButton':
        return (
          <ActionButton
            data={widgetData[0]}
            windowType={windowType}
            fields={fields}
            dataId={dataId}
            docId={docId}
            activeTab={activeTab}
            onChange={(option) => onPatch(fields[1].field, option)}
            tabIndex={tabIndex}
            dropdownOpenCallback={dropdownOpenCallback}
            ref={this.props.innerRef}
          />
        );
      case 'ProductAttributes':
        return (
          <Attributes
            entity={entity}
            attributeType="pattribute"
            fields={fields}
            dataId={dataId}
            widgetData={widgetData[0]}
            docType={windowType}
            tabId={tabId}
            rowId={rowId}
            viewId={viewId}
            onFocus={this.handleFocus}
            onBlur={this.handleBlur}
            fieldName={widgetField}
            handleBackdropLock={handleBackdropLock}
            patch={(option) => onPatch(widgetField, option)}
            tabIndex={tabIndex}
            autoFocus={autoFocus}
            readonly={readonly}
            rowIndex={rowIndex}
            updateHeight={updateHeight}
          />
        );
      case 'Address':
        return (
          <Attributes
            attributeType="address"
            entity={entity}
            fields={fields}
            dataId={dataId}
            widgetData={widgetData[0]}
            docType={windowType}
            tabId={tabId}
            rowId={rowId}
            fieldName={widgetField}
            handleBackdropLock={handleBackdropLock}
            patch={(option) => onPatch(widgetField, option)}
            tabIndex={tabIndex}
            autoFocus={autoFocus}
            readonly={readonly}
            isModal={isModal}
          />
        );
      case 'Image':
        return (
          <Image
            fields={fields}
            data={widgetData[0]}
            handlePatch={onPatch}
            readonly={readonly}
          />
        );
      case 'ZoomIntoButton':
        return (
          <button
            className={
              'btn btn-sm btn-meta-primary ' +
              (gridAlign ? 'text-' + gridAlign + ' ' : '') +
              (readonly ? 'tag-disabled disabled ' : '')
            }
            onClick={() => handleZoomInto(fields[0].field)}
            tabIndex={tabIndex}
            ref={this.props.innerRef}
          >
            {caption}
          </button>
        );
      case 'Labels': {
        let values = [];

        const entry = widgetData[0];

        if (entry && entry.value && Array.isArray(entry.value.values)) {
          values = entry.value.values;
        }

        return (
          <Labels
            name={widgetField}
            entity={entity}
            subentity={subentity}
            subentityId={subentityId}
            tabId={tabId}
            rowId={rowId}
            windowType={windowType}
            viewId={viewId}
            selected={values}
            className={this.getClassNames()}
            onChange={(value) =>
              onPatch(widgetField, {
                values: value,
              })
            }
            tabIndex={tabIndex}
          />
        );
      }
      default:
        return false;
    }
  }
}

export default React.forwardRef((props, ref) =>
  <WidgetRenderer innerRef={ref} {...props} />
);
