import Moment from 'moment';
import React, { Component } from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { List as ImmutableList } from 'immutable';

import { RawWidgetPropTypes, RawWidgetDefaultProps } from './PropTypes';
import { getClassNames, generateMomentObj } from './RawWidgetHelpers';
import { allowShortcut, disableShortcut } from '../../actions/WindowActions';
import {
  DATE_FORMAT,
  TIME_FORMAT,
  DATE_TIMEZONE_FORMAT,
  DATE_FIELD_FORMATS,
} from '../../constants/Constants';
import ActionButton from './ActionButton';
import Attributes from './Attributes/Attributes';
import Checkbox from './Checkbox';
import DatePicker from './DatePicker';
import DatetimeRange from './DatetimeRange';
import DevicesWidget from './Devices/DevicesWidget';
import Image from './Image';
import Tooltips from '../tooltips/Tooltips';
import Labels from './Labels';
import Link from './Link';
import CharacterLimitInfo from './CharacterLimitInfo';
import List from './List/List';
import Lookup from './Lookup/Lookup';

/**
 * @file Class based component.
 * @module RawWidget
 * @extends Component
 */
export class RawWidget extends Component {
  constructor(props) {
    super(props);

    const { widgetData } = props;
    let cachedValue = undefined;

    if (widgetData && widgetData[0]) {
      if (widgetData[0].value !== undefined) {
        cachedValue = widgetData[0].value;
      } else if (
        widgetData[0].status &&
        widgetData[0].status.value !== undefined
      ) {
        cachedValue = widgetData[0].status.value;
      }
    }

    this.state = {
      isEdited: false,
      cachedValue,
      errorPopup: false,
      tooltipToggled: false,
      clearedFieldWarning: false,
    };

    this.getClassNames = getClassNames.bind(this);
    this.generateMomentObj = generateMomentObj.bind(this);
  }

  componentDidMount() {
    const { autoFocus, textSelected } = this.props;
    const { rawWidget } = this;

    if (rawWidget && autoFocus) {
      rawWidget.focus();
    }

    if (textSelected) {
      rawWidget.select();
    }
  }

  /**
   *  Re-rendering conditions by widgetType this to prevent unnecessary re-renders
   *  Performance boost
   */
  shouldComponentUpdate(nextProps) {
    switch (this.props.widgetType) {
      case 'YesNo':
        return nextProps.widgetData[0].value !== this.props.widgetData[0].value;

      default:
        return true;
    }
  }

  setRef = (ref) => {
    this.rawWidget = ref;
  };

  /**
   * @method focus
   * @summary Function used specifically for list widgets. It blocks outside clicks, which are
   * then enabled again in handleBlur. This is to avoid closing the list as it's a separate
   * DOM element outside of it's parent's tree.
   */
  focus = () => {
    const { handleFocus, disableOnClickOutside } = this.props;
    const { rawWidget } = this;

    if (rawWidget && rawWidget.focus) {
      rawWidget.focus();
    }

    disableOnClickOutside && disableOnClickOutside();
    handleFocus && handleFocus();
  };

  /**
   * @method handleFocus
   * @summary ToDo: Describe the method.
   * @param {*} e
   */
  handleFocus = () => {
    const { dispatch, handleFocus, listenOnKeysFalse } = this.props;

    dispatch(disableShortcut());
    listenOnKeysFalse && listenOnKeysFalse();

    setTimeout(() => {
      this.setState(
        {
          isEdited: true,
        },
        () => {
          handleFocus && handleFocus();
        }
      );
    }, 0);
  };

  /**
   * @method handleBlurBlur
   * @summary ToDo: Describe the method.
   * @param {*} widgetField
   * @param {*} value
   * @param {*} id
   */
  handleBlur = (widgetField, value, id) => {
    const {
      dispatch,
      handleBlur,
      listenOnKeysTrue,
      enableOnClickOutside,
    } = this.props;

    this.setState(
      {
        isEdited: false,
      },
      () => {
        enableOnClickOutside && enableOnClickOutside();
        dispatch(allowShortcut());
        handleBlur && handleBlur(this.willPatch(widgetField, value));

        listenOnKeysTrue && listenOnKeysTrue();

        if (widgetField) {
          this.handlePatch(widgetField, value, id);
        }
      }
    );
  };

  /**
   * @method updateTypedCharacters
   * @summary updates in the state the number of charactes typed
   * @param {typedText} string
   */
  updateTypedCharacters = (typedText) => {
    const { fieldName } = this.props;
    let existingCharsTyped = { ...this.state.charsTyped };
    existingCharsTyped[fieldName] = typedText.length;
    this.setState({ charsTyped: existingCharsTyped });
    return true;
  };

  /**
   * @method handleKeyDown
   * @summary key handler for the widgets. For number fields we're suppressing up/down
   *          arrows to enable table row navigation
   * @param {*} e
   * @param {*} property
   * @param {*} value
   */
  handleKeyDown = (e, property, value) => {
    const { lastFormField, widgetType, closeTableField } = this.props;
    const { key } = e;
    this.updateTypedCharacters(e.target.value);

    // for number fields submit them automatically on up/down arrow pressed and blur the field
    const NumberWidgets = ImmutableList([
      'Integer',
      'Amount',
      'Quantity',
      'Number',
      'CostPrice',
    ]);
    if (
      (key === 'ArrowUp' || key === 'ArrowDown') &&
      NumberWidgets.includes(widgetType)
    ) {
      closeTableField();
      e.preventDefault();

      this.handleBlur();

      return this.handlePatch(property, value, null, null, true);
    }

    if ((key === 'Enter' || key === 'Tab') && !e.shiftKey) {
      if (key === 'Enter' && !lastFormField) {
        e.preventDefault();
      }
      return this.handlePatch(property, value);
    }
  };

  /**
   * @method willPatch
   * @summary Checks if the value has actually changed between what was cached before.
   * @param {*} property
   * @param {*} value
   * @param {*} valueTo
   */
  willPatch = (property, value, valueTo) => {
    const { widgetData } = this.props;
    const { cachedValue } = this.state;

    // if there's no widget value, then nothing could've changed. Unless
    // it's a widget for actions (think ActionButton)
    const isValue =
      widgetData[0].value !== undefined ||
      (widgetData[0].status && widgetData[0].status.value !== undefined);
    let fieldData = widgetData.find((widget) => widget.field === property);
    if (!fieldData) {
      fieldData = widgetData[0];
    }

    let allowPatching =
      (isValue &&
        (JSON.stringify(fieldData.value) != JSON.stringify(value) ||
          JSON.stringify(fieldData.valueTo) != JSON.stringify(valueTo))) ||
      JSON.stringify(cachedValue) != JSON.stringify(value) ||
      // clear field that had it's cachedValue nulled before
      (cachedValue === null && value === null);

    if (cachedValue === undefined && !value) {
      allowPatching = false;
    }

    return allowPatching;
  };

  /**
   * @method handlePatch
   * @summary Method for handling the actual patching from the widget(input), which in turn
   *          calls the parent method (usually from MasterWidget) if the requirements are met
   *          (value changed and patching is not in progress). `isForce` will be used for Datepicker
   *          Datepicker is checking the cached value in datepicker component itself
   *          and send a patch request only if date is changed
   * @param {*} property
   * @param {*} value
   * @param {*} id
   * @param {*} valueTo
   * @param {*} isForce
   */
  handlePatch = (property, value, id, valueTo, isForce) => {
    const { handlePatch, inProgress, widgetType, maxLength } = this.props;
    const willPatch = this.willPatch(property, value, valueTo);

    if (widgetType === 'LongText' || widgetType === 'Text') {
      value = value.substring(0, maxLength);
      this.updateTypedCharacters(value);
    }

    // Do patch only when value is not equal state
    // or cache is set and it is not equal value
    if ((isForce || willPatch) && handlePatch && !inProgress) {
      if (widgetType === 'ZonedDateTime' && Moment.isMoment(value)) {
        value = Moment(value).format(DATE_TIMEZONE_FORMAT);
      }

      this.setState({
        cachedValue: value,
        clearedFieldWarning: false,
      });

      return handlePatch(property, value, id, valueTo);
    }

    return Promise.resolve(null);
  };

  /**
   * @method handleProcess
   * @summary ToDo: Describe the method.
   */
  handleProcess = () => {
    const {
      handleProcess,
      buttonProcessId,
      tabId,
      rowId,
      dataId,
      windowType,
      caption,
    } = this.props;

    handleProcess &&
      handleProcess(caption, buttonProcessId, tabId, rowId, dataId, windowType);
  };

  /**
   * @method handleErrorPopup
   * @summary ToDo: Describe the method.
   * @param {*} value
   */
  handleErrorPopup = (value) => {
    this.setState({
      errorPopup: value,
    });
  };

  /**
   * @method clearFieldWarning
   * @summary ToDo: Describe the method.
   * @param {*} warning
   */
  clearFieldWarning = (warning) => {
    if (warning) {
      this.setState({
        clearedFieldWarning: true,
      });
    }
  };

  /**
   * @method toggleTooltip
   * @summary ToDo: Describe the method.
   * @param {*} show
   */
  toggleTooltip = (show) => {
    this.setState({
      tooltipToggled: show,
    });
  };

  /**
   * @method renderErrorPopup
   * @summary ToDo: Describe the method.
   * @param {*} reason
   */
  renderErrorPopup = (reason) => {
    return (
      <div className="input-error-popup">{reason ? reason : 'Input error'}</div>
    );
  };

  /**
   * @method renderWidget
   * @summary ToDo: Describe the method.
   */
  renderWidget = () => {
    const {
      handleChange,
      updated,
      modalVisible,
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
      //@TODO Looks like `fields` and `widgetData` are the very same thing 99.9% of the time.
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
      data,
      listenOnKeys,
      listenOnKeysFalse,
      closeTableField,
      handleZoomInto,
      attribute,
      allowShowPassword,
      onBlurWidget,
      defaultValue,
      isOpenDatePicker,
      dateFormat,
      initialFocus,
      timeZone,
      fieldName,
      maxLength,
    } = this.props;

    let widgetValue = data != null ? data : widgetData[0].value;
    const { isEdited, charsTyped } = this.state;

    // TODO: API SHOULD RETURN THE SAME PROPERTIES FOR FILTERS
    const widgetField = filterWidget
      ? fields[0].parameterName
      : fields[0].field;
    const readonly = widgetData[0].readonly;
    let tabIndex = this.props.tabIndex;

    if (widgetValue === null) {
      widgetValue = '';
    }

    if (fullScreen || readonly || (modalVisible && !isModal)) {
      tabIndex = -1;
    }

    // TODO: this logic should be removed and adapted below after widgetType === 'MultiListValue' is added
    const isMultiselect =
      widgetData[0].widgetType === 'List' && widgetData[0].multiListValue
        ? true
        : false;
    // TODO:  ^^^^^^^^^^^^^

    const widgetProperties = {
      ref: this.setRef,
      //autocomplete=new-password did not work in chrome for non password fields anymore,
      //switched to autocomplete=off instead
      autoComplete: 'off',
      className: 'input-field js-input-field',
      value: widgetValue,
      defaultValue,
      placeholder: fields[0].emptyText,
      disabled: readonly,
      onFocus: this.handleFocus,
      tabIndex: tabIndex,
      onChange: (e) =>
        handleChange &&
        this.updateTypedCharacters(e.target.value) &&
        handleChange(widgetField, e.target.value),
      onBlur: (e) => this.handleBlur(widgetField, e.target.value, id),
      onKeyDown: (e) =>
        this.handleKeyDown(e, widgetField, e.target.value, widgetType),
      title: widgetValue,
      id,
    };
    const showErrorBorder = charsTyped && charsTyped[fieldName] > maxLength;
    let selectedValue = widgetData[0].value
      ? widgetData[0].value
      : widgetData[0].defaultValue;

    switch (widgetType) {
      case 'Date':
        if (range) {
          // TODO: Watch out! The datetimerange widget as exception,
          // is non-controlled input! For further usage, needs
          // upgrade.
          return (
            <DatetimeRange
              onChange={(value, valueTo) =>
                this.handlePatch(
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
                  this.handlePatch(
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
                this.handlePatch(
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
              value={widgetValue}
              onChange={(date) => handleChange(widgetField, date)}
              patch={(date) =>
                this.handlePatch(
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
                this.handlePatch(
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

              this.handlePatch(widgetField, {
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
            }}
            entity={entity}
            subentity={subentity}
            subentityId={subentityId}
            recent={[]}
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
            onBlur={this.handleBlur}
            onChange={this.handlePatch}
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
            onBlur={this.handleBlur}
            onChange={this.handlePatch}
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
            onBlur={this.handleBlur}
            onChange={this.handlePatch}
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
              isEdited,
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
                  'input-focused': isEdited,
                },
                {
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
                  'input-focused': isEdited,
                },
                {
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
                  'input-focused': isEdited,
                }
              )}
            >
              <input {...widgetProperties} type="password" ref={this.setRef} />
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
              'input-focused': isEdited,
            })}
          >
            <input
              {...widgetProperties}
              type="number"
              min={0}
              precision={widgetField === 'CableLength' ? 2 : 1}
              step={subentity === 'quickInput' ? 0.1 : 1}
            />
          </div>
        );
      case 'Number':
      case 'CostPrice':
        return (
          <div
            className={classnames(this.getClassNames(), 'number-field', {
              'input-focused': isEdited,
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
            handlePatch={this.handlePatch}
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
            ref={this.setRef}
            onKeyDown={(e) => {
              e.key === ' ' &&
                this.handlePatch(widgetField, !widgetData[0].value, id);
            }}
          >
            <input
              type="checkbox"
              checked={widgetData[0].value}
              disabled={readonly}
              tabIndex="-1"
              onChange={(e) =>
                this.handlePatch(widgetField, e.target.checked, id)
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
            ref={this.setRef}
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
            onClick={() => this.handlePatch(widgetField)}
            tabIndex={tabIndex}
            ref={this.setRef}
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
            ref={this.setRef}
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
            onChange={(option) => this.handlePatch(fields[1].field, option)}
            tabIndex={tabIndex}
            dropdownOpenCallback={dropdownOpenCallback}
            ref={this.setRef}
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
            patch={(option) => this.handlePatch(widgetField, option)}
            tabIndex={tabIndex}
            autoFocus={autoFocus}
            readonly={readonly}
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
            patch={(option) => this.handlePatch(widgetField, option)}
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
            handlePatch={this.handlePatch}
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
            ref={this.setRef}
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
              this.handlePatch(widgetField, {
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
  };

  render() {
    const {
      caption,
      description,
      captionElement,
      fields,
      type,
      noLabel,
      widgetData,
      rowId,
      isModal,
      handlePatch,
      widgetType,
      handleZoomInto,
      dataEntry,
      subentity,
      fieldFormGroupClass,
      fieldLabelClass,
      fieldInputClass,
    } = this.props;
    const {
      errorPopup,
      clearedFieldWarning,
      tooltipToggled,
      isEdited,
    } = this.state;
    const widgetBody = this.renderWidget();
    const { validStatus, warning } = widgetData[0];
    const quickInput = subentity === 'quickInput';

    // We have to hardcode that exception in case of having
    // wrong two line rendered one line widgets
    const oneLineException =
      ['Switch', 'YesNo', 'Label', 'Button'].indexOf(widgetType) > -1;

    // Unsupported widget type
    if (!widgetBody) {
      // eslint-disable-next-line no-console
      console.warn(
        'The %c' + widgetType,
        'font-weight:bold;',
        'is unsupported type of widget.'
      );

      return false;
    }

    // No display value or not displayed
    if (!widgetData[0].displayed || widgetData[0].displayed !== true) {
      return false;
    }
    const valueDescription =
      widgetData[0].value && widgetData[0].value.description
        ? widgetData[0].value.description
        : null;

    const widgetFieldsName = fields
      .map((field) => 'form-field-' + field.field)
      .join(' ');

    let labelClass;
    let fieldClass;
    let formGroupClass = '';

    if (quickInput) {
      labelClass = fieldLabelClass;
      fieldClass = fieldInputClass;
      formGroupClass = fieldFormGroupClass;
    } else {
      labelClass = dataEntry ? 'col-sm-5' : '';
      if (!labelClass) {
        labelClass =
          type === 'primary' && !oneLineException
            ? 'col-sm-12 panel-title'
            : type === 'primaryLongLabels'
            ? 'col-sm-6'
            : 'col-sm-3';
      }

      fieldClass = dataEntry ? 'col-sm-7' : '';
      if (!fieldClass) {
        fieldClass =
          ((type === 'primary' || noLabel) && !oneLineException
            ? 'col-sm-12 '
            : type === 'primaryLongLabels'
            ? 'col-sm-6'
            : 'col-sm-9 ') + (fields[0].devices ? 'form-group-flex' : '');
      }
    }

    const labelProps = {};

    if (!noLabel && caption && fields[0].supportZoomInto) {
      labelProps.onClick = () => handleZoomInto(fields[0].field);
    }

    return (
      <div
        className={classnames(
          'form-group',
          formGroupClass,
          {
            'form-group-table': rowId && !isModal,
          },
          widgetFieldsName
        )}
      >
        <div className="row">
          {captionElement || null}
          {!noLabel && caption && (
            <label
              className={classnames('form-control-label', labelClass, {
                'input-zoom': quickInput && fields[0].supportZoomInto,
                'zoom-into': fields[0].supportZoomInto,
              })}
              title={description || caption}
              {...labelProps}
            >
              {caption}
            </label>
          )}
          <div
            className={fieldClass}
            onMouseEnter={() => this.handleErrorPopup(true)}
            onMouseLeave={() => this.handleErrorPopup(false)}
          >
            {!clearedFieldWarning && warning && (
              <div
                className={classnames('field-warning', {
                  'field-warning-message': warning,
                  'field-error-message': warning && warning.error,
                })}
                onMouseEnter={() => this.toggleTooltip(true)}
                onMouseLeave={() => this.toggleTooltip(false)}
              >
                <span>{warning.caption}</span>
                <i
                  className="meta-icon-close-alt"
                  onClick={() => this.clearFieldWarning(warning)}
                />
                {warning.message && tooltipToggled && (
                  <Tooltips action={warning.message} type="" />
                )}
              </div>
            )}

            <div
              className={classnames('input-body-container', {
                focused: isEdited,
              })}
              title={valueDescription}
            >
              <ReactCSSTransitionGroup
                transitionName="fade"
                transitionEnterTimeout={200}
                transitionLeaveTimeout={200}
              >
                {errorPopup &&
                  validStatus &&
                  !validStatus.valid &&
                  !validStatus.initialValue &&
                  this.renderErrorPopup(validStatus.reason)}
              </ReactCSSTransitionGroup>
              {widgetBody}
            </div>
            {fields[0].devices && !widgetData[0].readonly && (
              <DevicesWidget
                devices={fields[0].devices}
                tabIndex={1}
                handleChange={(value) =>
                  handlePatch && handlePatch(fields[0].field, value)
                }
              />
            )}
          </div>
        </div>
      </div>
    );
  }
}

RawWidget.propTypes = RawWidgetPropTypes;
RawWidget.defaultProps = RawWidgetDefaultProps;

export default connect((state) => ({
  modalVisible: state.windowHandler.modal.visible,
  timeZone: state.appHandler.me.timeZone,
}))(RawWidget);
