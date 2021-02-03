import React, { Fragment, PureComponent } from 'react';
import Moment from 'moment';
import classnames from 'classnames';
import { get } from 'lodash';

import {
  DATE_FORMAT,
  TIME_FORMAT,
  DATE_TIMEZONE_FORMAT,
  DATE_FIELD_FORMATS,
} from '../../constants/Constants';
import { getClassNames, getFormattedDate } from '../../utils/widgetHelpers';
import { WidgetRendererPropTypes } from './PropTypes';
import { withForwardedRef } from '../hoc/WithRouterAndRef';

import ActionButton from './ActionButton';
import Attributes from './Attributes/Attributes';
import Checkbox from './Checkbox';
import DatePicker from './DatePicker';
import DatetimeRange from './DatetimeRange';
import Image from './Image';
import Labels from './Labels';
import Link from './Link';
import CharacterLimitInfo from './CharacterLimitInfo';
import List from './List/List';
import Lookup from './Lookup/Lookup';
import Switch from './Switch';
import Amount from './Amount';
import Password from './Password';

class WidgetRenderer extends PureComponent {
  constructor(props) {
    super(props);

    this.getClassNames = getClassNames.bind(this);
    this.getFormattedDate = getFormattedDate.bind(this);
  }

  /**
   * @method handleDateChange
   * @summary calls `handleChange` prop function for date fields, to avoid
   * unnecessary anonymous functions
   *
   * @param {date} date - toggles between text/password
   */
  handleDateChange = (date) => {
    const { widgetField, handleChange } = this.props;
    handleChange(widgetField, date);
  };

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
      maxLength,
      updateHeight,
      rowIndex,
      onClickOutside,
      emptyText,
      forceFullWidth,
      forceHeight,

      // from `renderWidget`
      isMultiselect,
      widgetField,
      widgetProperties,
      showErrorBorder,
      isFocused,
      charsTyped,
      readonly,
      onPatch,
      onListFocus,
      onBlurWithParams,
      onSetWidgetType,
      onHandleProcess,
      forwardedRef,
    } = this.props;
    const { tabIndex, onFocus } = widgetProperties;
    const widgetValue = get(widgetProperties, ['value'], null);
    widgetProperties.ref = forwardedRef;

    const selectedValue = widgetData[0].value
      ? widgetData[0].value
      : widgetData[0].defaultValue;

    const listAndLookupsProps = {
      dataId,
      attribute,
      entity,
      subentity,
      subentityId,
      windowType,
      readonly,
      updated,
      filterWidget,
      filterId,
      tabId,
      rowId,
      tabIndex,
      viewId,
      autoFocus,
      onFocus: onListFocus,
      onBlur: onBlurWithParams,
      align: gridAlign,
      mandatory: widgetData[0].mandatory,
      parameterName: fields[0].parameterName,
      validStatus: widgetData[0].validStatus,
      onChange: onPatch,
    };
    const dateProps = {
      field: widgetField,
      key: 1,
      handleChange,
      handleBackdropLock,
      inputProps: {
        placeholder: fields[0].emptyText,
        disabled: readonly,
        tabIndex: tabIndex,
      },
      onChange: this.handleDateChange,
    };
    const dateRangeProps = {
      mandatory: widgetData[0].mandatory,
      validStatus: widgetData[0].validStatus,
      value: widgetData[0].value,
      valueTo: widgetData[0].valueTo,
      tabIndex,
      onShow,
      onHide,
    };
    const attributesProps = {
      entity,
      fields,
      dataId,
      widgetData: widgetData[0],
      docType: windowType,
      tabId,
      rowId,
      fieldName: widgetField,
      handleBackdropLock,
      patch: (option) => onPatch(widgetField, option),
      tabIndex,
      autoFocus,
      readonly,
    };

    switch (widgetType) {
      case 'Date':
        if (range) {
          // TODO: Watch out! The datetimerange widget as exception,
          // is non-controlled input! For further usage, needs
          // upgrade.
          return (
            <DatetimeRange
              {...dateRangeProps}
              onChange={(value, valueTo) =>
                onPatch(
                  widgetField,
                  value ? Moment(value).format(DATE_FORMAT) : null,
                  null,
                  valueTo ? Moment(valueTo).format(DATE_FORMAT) : null
                )
              }
              field={widgetField}
              timeZone={timeZone}
            />
          );
        } else {
          return (
            <div className={this.getClassNames({ icon: true })}>
              <DatePicker
                {...dateProps}
                timeFormat={false}
                dateFormat={dateFormat || true}
                value={widgetValue || widgetData[0].value}
                patch={(date) =>
                  onPatch(
                    widgetField,
                    this.getFormattedDate(date, DATE_FORMAT),
                    null,
                    null,
                    true
                  )
                }
                {...{
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
              {...dateProps}
              timeFormat={true}
              dateFormat={dateFormat || true}
              hasTimeZone={true}
              value={widgetValue || widgetData[0].value}
              patch={(date) =>
                onPatch(
                  widgetField,
                  this.getFormattedDate(date, DATE_TIMEZONE_FORMAT),
                  null,
                  null,
                  true
                )
              }
              {...{
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
              {...dateProps}
              timeFormat={TIME_FORMAT}
              dateFormat={false}
              value={this.getFormattedDate(widgetValue, TIME_FORMAT)}
              patch={(date) =>
                onPatch(
                  widgetField,
                  this.getFormattedDate(date, TIME_FORMAT),
                  null,
                  null,
                  true
                )
              }
              tabIndex={tabIndex}
            />
          </div>
        );
      case 'Timestamp':
        return (
          <div className={this.getClassNames({ icon: true })}>
            <DatePicker
              {...dateProps}
              timeFormat={false}
              dateFormat={DATE_FIELD_FORMATS[widgetType]}
              value={widgetValue}
              patch={(date) =>
                onPatch(
                  widgetField,
                  this.getFormattedDate(date, `x`),
                  null,
                  null,
                  true
                )
              }
              tabIndex={tabIndex}
            />
          </div>
        );
      case 'DateRange': {
        return (
          <DatetimeRange
            {...dateRangeProps}
            onChange={(value, valueTo) => {
              const val = Moment(value).format(DATE_FORMAT);
              const valTo = Moment(valueTo).format(DATE_FORMAT);

              onPatch(widgetField, {
                ...(val && { value: val }),
                ...(valTo && { valueTo: valTo }),
              });
            }}
          />
        );
      }
      case 'Lookup':
        return (
          <Lookup
            {...listAndLookupsProps}
            properties={fields}
            widgetData={widgetData}
            placeholder={emptyText ? emptyText : fields[0].emptyText}
            rank={type}
            isModal={isModal}
            selected={widgetValue}
            initialFocus={initialFocus}
            forceFullWidth={forceFullWidth}
            forceHeight={forceHeight}
            newRecordCaption={fields[0].newRecordCaption}
            newRecordWindowId={fields[0].newRecordWindowId}
            listenOnKeys={listenOnKeys}
            listenOnKeysFalse={listenOnKeysFalse}
            closeTableField={closeTableField}
            onBlurWidget={onBlurWidget}
            onClickOutside={onClickOutside}
          />
        );
      case 'List':
      case 'MultiListValue': {
        const commonProps = {
          ...listAndLookupsProps,
          widgetField,
          defaultValue: fields[0].emptyText,
          properties: fields[0],
          emptyText: fields[0].emptyText,
        };
        const typeProps = {};

        if (widgetType === 'List') {
          typeProps.selected = selectedValue;
          typeProps.isMultiselect = isMultiselect;
        } else {
          typeProps.selected = widgetData[0].value || null;
          typeProps.isMultiselect = true;
        }

        return <List {...commonProps} {...typeProps} />;
      }
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
      case 'LongText': {
        const classNameParams = { icon: true };
        let renderContent = null;

        if (widgetType === 'Text') {
          renderContent = (
            <Fragment>
              <input {...widgetProperties} type="text" />
              {icon && <i className="meta-icon-edit input-icon-right" />}
            </Fragment>
          );
        } else {
          classNameParams.forcedPrimary = true;
          renderContent = <textarea {...widgetProperties} />;
        }
        return (
          <div>
            <div
              className={classnames(this.getClassNames(classNameParams), {
                'border-danger': showErrorBorder,
              })}
            >
              {renderContent}
            </div>
            {charsTyped && charsTyped >= 0 ? (
              <CharacterLimitInfo
                charsTyped={charsTyped}
                maxLength={maxLength}
              />
            ) : null}
          </div>
        );
      }
      case 'Password':
        return (
          <Password
            {...{
              widgetProperties,
              icon,
              allowShowPassword,
              onSetWidgetType,
            }}
            getClassNames={this.getClassNames}
          />
        );
      case 'Integer':
      case 'Amount':
      case 'Quantity':
        return (
          <Amount
            {...{
              widgetData,
              widgetField,
              fields,
              subentity,
              widgetProperties,
              onPatch,
            }}
            getClassNames={this.getClassNames}
          />
        );
      case 'Number':
      case 'CostPrice':
        return (
          <div className={classnames(this.getClassNames(), 'number-field')}>
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
          <Switch
            {...{
              widgetData,
              widgetField,
              rowId,
              isModal,
              tabIndex,
              forwardedRef,
              id,
              readonly,
              onPatch,
            }}
          />
        );
      case 'Label':
        return (
          <div
            className={classnames('tag tag-warning ', {
              [`text-${gridAlign}`]: gridAlign,
            })}
            tabIndex={tabIndex}
            ref={forwardedRef}
          >
            {widgetData[0].value}
          </div>
        );
      case 'Button':
      case 'ZoomIntoButton':
      case 'ProcessButton': {
        let textContent = caption;
        let clickHandler = undefined;

        if (widgetType === 'Button') {
          clickHandler = () => onPatch(widgetField);
          textContent =
            widgetData[0].value &&
            widgetData[0].value[Object.keys(widgetData[0].value)[0]];
        } else if (widgetType === 'ZoomIntoButton') {
          clickHandler = () => handleZoomInto(fields[0].field);
        } else {
          clickHandler = onHandleProcess;
        }

        return (
          <button
            className={classnames('btn btn-sm btn-meta-primary', {
              [`text-${gridAlign}`]: gridAlign,
              'tag-disabled disabled': readonly,
            })}
            onClick={clickHandler}
            tabIndex={tabIndex}
            ref={forwardedRef}
          >
            {textContent}
          </button>
        );
      }
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
            ref={forwardedRef}
          />
        );
      case 'ProductAttributes':
        return (
          <Attributes
            {...attributesProps}
            attributeType="pattribute"
            viewId={viewId}
            onFocus={onFocus}
            rowIndex={rowIndex}
            updateHeight={updateHeight}
          />
        );
      case 'Address':
        return (
          <Attributes
            {...attributesProps}
            attributeType="address"
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
            readonly={readonly}
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

WidgetRenderer.propTypes = WidgetRendererPropTypes;

export default withForwardedRef(WidgetRenderer);
