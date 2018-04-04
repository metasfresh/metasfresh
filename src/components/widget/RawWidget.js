import Moment from 'moment';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import { connect } from 'react-redux';

import { allowShortcut, disableShortcut } from '../../actions/WindowActions';
import { DATE_FORMAT } from '../../constants/Constants';
import ActionButton from './ActionButton';
import Attributes from './Attributes/Attributes';
import Checkbox from './Checkbox';
import DatePicker from './DatePicker';
import DatetimeRange from './DatetimeRange';
import DevicesWidget from './Devices/DevicesWidget';
import Image from './Image';
import Labels from './Labels';
import Link from './Link';
import List from './List/List';
import Lookup from './Lookup/Lookup';

class RawWidget extends Component {
  static propTypes = {
    handleZoomInto: PropTypes.func,
  };

  static defaultProps = {
    handleZoomInto: () => {},
  };

  constructor(props) {
    super(props);

    this.state = {
      isEdited: false,
      cachedValue: undefined,
      errorPopup: false,
    };
  }

  componentDidMount() {
    const { autoFocus, textSelected } = this.props;

    if (this.rawWidget && autoFocus) {
      this.rawWidget.focus();
    }

    if (textSelected) {
      this.rawWidget.select();
    }
  }

  /**
   * Function used specifically for list widgets. It blocks outside clicks, which are
   * then enabled again in handleBlur. This is to avoid closing the list as it's a separate
   * DOM element outside of it's parent's tree.
   */
  focus = () => {
    const { dispatch, handleFocus, disableOnClickOutside, entity } = this.props;

    if (this.rawWidget && this.rawWidget.focus) {
      this.rawWidget.focus();
    }

    dispatch(disableShortcut());

    // don't disable onclickoutside for the attributes widget
    if (entity !== 'pattribute') {
      disableOnClickOutside && disableOnClickOutside();
    }
    handleFocus && handleFocus();
  };

  handleFocus = e => {
    const { dispatch, handleFocus, listenOnKeysFalse } = this.props;

    dispatch(disableShortcut());

    this.setState({
      isEdited: true,
      cachedValue: e.target.value,
    });

    listenOnKeysFalse && listenOnKeysFalse();
    handleFocus && handleFocus();
  };

  handleBlur = (widgetField, value, id) => {
    const {
      dispatch,
      handleBlur,
      listenOnKeysTrue,
      enableOnClickOutside,
    } = this.props;

    enableOnClickOutside && enableOnClickOutside();
    dispatch(allowShortcut());
    handleBlur && handleBlur(this.willPatch(value));

    this.setState({
      isEdited: false,
      cachedValue: undefined,
    });

    listenOnKeysTrue && listenOnKeysTrue();

    if (widgetField) {
      this.handlePatch(widgetField, value, id);
    }
  };

  willPatch = (value, valueTo) => {
    const { widgetData } = this.props;
    const { cachedValue } = this.state;

    return (
      JSON.stringify(widgetData[0].value) !== JSON.stringify(value) ||
      JSON.stringify(widgetData[0].valueTo) !== JSON.stringify(valueTo) ||
      (cachedValue !== undefined &&
        JSON.stringify(cachedValue) !== JSON.stringify(value))
    );
  };

  handleKeyDown = (e, property, value, widgetType) => {
    if ((e.key === 'Enter' || e.key === 'Tab') && widgetType !== 'LongText') {
      this.handlePatch(property, value);
    }
  };

  generateMomentObj = value => {
    if (Moment.isMoment(value)) {
      return value;
    }
    return value ? Moment(value).format(DATE_FORMAT) : null;
  };

  // isForce will be used for Datepicker
  // Datepicker is checking the cached value in datepicker component itself
  // and send a patch request only if date is changed
  handlePatch = (property, value, id, valueTo, isForce) => {
    const { handlePatch } = this.props;

    // Do patch only when value is not equal state
    // or cache is set and it is not equal value
    if ((isForce || this.willPatch(value, valueTo)) && handlePatch) {
      this.setState({
        cachedValue: value,
      });
      return handlePatch(property, value, id, valueTo);
    }

    return null;
  };

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

  handleErrorPopup = value => {
    this.setState({
      errorPopup: value,
    });
  };

  classNames = classObject =>
    Object.entries(classObject)
      .filter(([, classActive]) => classActive)
      .map(([className]) => className)
      .join(' ');

  getClassNames = ({ icon, forcedPrimary } = {}) => {
    const { widgetData, gridAlign, type, updated, rowId, isModal } = this.props;

    const { isEdited } = this.state;

    const { readonly, value, mandatory, validStatus } = widgetData[0];

    return this.classNames({
      'input-block': true,
      'input-icon-container': icon,
      'input-disabled': readonly,
      'input-mandatory':
        mandatory && (value ? value.length === 0 : value !== 0),
      'input-error':
        validStatus &&
        !validStatus.valid &&
        !validStatus.initialValue &&
        !isEdited,
      [`text-xs-${gridAlign}`]: gridAlign,
      [`input-${
        type === 'primary' || forcedPrimary ? 'primary' : 'secondary'
      }`]: true,
      [`pulse-${updated ? 'on' : 'off'}`]: true,
      'input-table': rowId && !isModal,
    });
  };

  renderErrorPopup = reason => {
    return (
      <div className="input-error-popup">{reason ? reason : 'Input error'}</div>
    );
  };

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
      subentityId,
      dropdownOpenCallback,
      autoFocus,
      fullScreen,
      widgetType,
      fields,
      windowType,
      dataId,
      type,
      widgetData,
      rowId,
      tabId,
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
    } = this.props;
    const widgetValue = data || widgetData[0].value;
    const { isEdited } = this.state;

    // TODO: API SHOULD RETURN THE SAME PROPERTIES FOR FILTERS
    const widgetField = filterWidget
      ? fields[0].parameterName
      : fields[0].field;

    const readonly = widgetData[0].readonly;

    let tabIndex = this.props.tabIndex;

    if (fullScreen || readonly || (modalVisible && !isModal)) {
      tabIndex = -1;
    }

    const widgetProperties = {
      ref: c => (this.rawWidget = c),
      className: 'input-field js-input-field',
      value: widgetValue,
      defaultValue,
      placeholder: fields[0].emptyText,
      disabled: readonly,
      onFocus: this.handleFocus,
      tabIndex: tabIndex,
      onChange: e => handleChange && handleChange(widgetField, e.target.value),
      onBlur: e => this.handleBlur(widgetField, e.target.value, id),
      onKeyDown: e =>
        this.handleKeyDown(e, widgetField, e.target.value, widgetType),
    };

    switch (widgetType) {
      case 'Date':
        if (range) {
          // Watch out! The datetimerange widget as exception,
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
              mandatory={widgetData[0].mandatory}
              validStatus={widgetData[0].validStatus}
              onShow={onShow}
              onHide={onHide}
              value={widgetData[0].value}
              valueTo={widgetData[0].valueTo}
              tabIndex={tabIndex}
            />
          );
        } else {
          return (
            <div className={this.getClassNames({ icon: true })}>
              <DatePicker
                key={1}
                field={fields[0].field}
                timeFormat={false}
                dateFormat={dateFormat || true}
                isOpenDatePicker={isOpenDatePicker}
                inputProps={{
                  placeholder: fields[0].emptyText,
                  disabled: readonly,
                  tabIndex: tabIndex,
                }}
                value={widgetValue || widgetData[0].value}
                onChange={date => {
                  const finalDate = date.utc ? date.utc(true) : date;
                  return handleChange(widgetField, finalDate);
                }}
                patch={date =>
                  this.handlePatch(
                    widgetField,
                    this.generateMomentObj(date),
                    null,
                    null,
                    true
                  )
                }
                {...{
                  handleBackdropLock,
                }}
              />
            </div>
          );
        }
      case 'DateTime':
        if (range) {
          // Watch out! The datetimerange widget as exception,
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
              mandatory={widgetData[0].mandatory}
              validStatus={widgetData[0].validStatus}
              onShow={onShow}
              onHide={onHide}
              value={widgetData[0].value}
              valueTo={widgetData[0].valueTo}
              tabIndex={tabIndex}
              timePicker={true}
            />
          );
        } else {
          return (
            <div className={this.getClassNames({ icon: true })}>
              <DatePicker
                field={fields[0].field}
                timeFormat={dateFormat ? false : true}
                dateFormat={dateFormat || true}
                inputProps={{
                  placeholder: fields[0].emptyText,
                  disabled: readonly,
                  tabIndex: tabIndex,
                }}
                value={widgetValue}
                onChange={date => handleChange(widgetField, date)}
                patch={date =>
                  this.handlePatch(
                    widgetField,
                    this.generateMomentObj(date),
                    null,
                    null,
                    true
                  )
                }
                tabIndex={tabIndex}
                handleBackdropLock={handleBackdropLock}
              />
            </div>
          );
        }
      case 'DateRange': {
        return (
          <DatetimeRange
            onChange={(value, valueTo) =>
              this.handlePatch(widgetField, {
                ...(value && { value }),
                ...(valueTo && { valueTo }),
              })
            }
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
      case 'Time':
        return (
          <div className={this.getClassNames({ icon: true })}>
            <DatePicker
              field={fields[0].field}
              timeFormat={true}
              dateFormat={false}
              inputProps={{
                placeholder: fields[0].emptyText,
                disabled: readonly,
                tabIndex: tabIndex,
              }}
              value={widgetValue}
              onChange={date => handleChange(widgetField, date)}
              patch={date =>
                this.handlePatch(
                  widgetField,
                  this.generateMomentObj(date),
                  null,
                  null,
                  true
                )
              }
              tabIndex={tabIndex}
              handleBackdropLock={handleBackdropLock}
            />
          </div>
        );
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
            defaultValue={widgetData}
            placeholder={fields[0].emptyText}
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
            validStatus={widgetData[0].validStatus}
            newRecordCaption={fields[0].newRecordCaption}
            newRecordWindowId={fields[0].newRecordWindowId}
            listenOnKeys={listenOnKeys}
            listenOnKeysFalse={listenOnKeysFalse}
            closeTableField={closeTableField}
            onFocus={this.focus}
            onHandleBlur={this.handleBlur}
            onChange={this.handlePatch}
            onBlurWidget={onBlurWidget}
          />
        );
      case 'List':
        return (
          <List
            {...{
              attribute,
            }}
            dataId={dataId}
            entity={entity}
            subentity={subentity}
            subentityId={subentityId}
            defaultValue={fields[0].emptyText}
            selected={widgetData[0].value || null}
            properties={fields}
            readonly={readonly}
            mandatory={widgetData[0].mandatory}
            windowType={windowType}
            rowId={rowId}
            tabId={tabId}
            onFocus={this.focus}
            onHandleBlur={this.handleBlur}
            onChange={option => this.handlePatch(widgetField, option, id)}
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
          <div
            className={
              this.getClassNames({ icon: true }) +
              (isEdited ? 'input-focused ' : '')
            }
          >
            <input {...widgetProperties} type="text" />
            {icon && <i className="meta-icon-edit input-icon-right" />}
          </div>
        );
      case 'LongText':
        return (
          <div
            className={
              this.getClassNames({
                icon: false,
                forcedPrimary: true,
              }) + (isEdited ? 'input-focused ' : '')
            }
          >
            <textarea {...widgetProperties} />
          </div>
        );
      case 'Password':
        return (
          <div className="input-inner-container">
            <div
              className={
                this.getClassNames({ icon: true }) +
                (isEdited ? 'input-focused ' : '')
              }
            >
              <input
                {...widgetProperties}
                type="password"
                ref={c => (this.rawWidget = c)}
              />
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
            className={
              this.getClassNames() + (isEdited ? 'input-focused ' : '')
            }
          >
            <input {...widgetProperties} type="number" min="0" step="1" />
          </div>
        );
      case 'Number':
      case 'CostPrice':
        return (
          <div
            className={
              this.getClassNames() + (isEdited ? 'input-focused ' : '')
            }
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
            className={
              'input-switch ' +
              (readonly ? 'input-disabled ' : '') +
              (widgetData[0].mandatory && widgetData[0].value.length === 0
                ? 'input-mandatory '
                : '') +
              (widgetData[0].validStatus && !widgetData[0].validStatus.valid
                ? 'input-error '
                : '') +
              (rowId && !isModal ? 'input-table ' : '')
            }
            tabIndex={tabIndex}
            ref={c => (this.rawWidget = c)}
            onKeyDown={e => {
              e.key === ' ' &&
                this.handlePatch(widgetField, !widgetData[0].value, id);
            }}
          >
            <input
              type="checkbox"
              checked={widgetData[0].value}
              disabled={readonly}
              tabIndex="-1"
              onChange={e =>
                this.handlePatch(widgetField, e.target.checked, id)
              }
            />
            <div className="input-slider" />
          </label>
        );
      case 'Label':
        return (
          <div
            className={
              'tag tag-warning ' +
              (gridAlign ? 'text-xs-' + gridAlign + ' ' : '')
            }
            tabIndex={tabIndex}
            ref={c => (this.rawWidget = c)}
          >
            {widgetData[0].value}
          </div>
        );
      case 'Button':
        return (
          <button
            className={
              'btn btn-sm btn-meta-primary ' +
              (gridAlign ? 'text-xs-' + gridAlign + ' ' : '') +
              (readonly ? 'tag-disabled disabled ' : '')
            }
            onClick={() => this.handlePatch(widgetField)}
            tabIndex={tabIndex}
            ref={c => (this.rawWidget = c)}
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
              (gridAlign ? 'text-xs-' + gridAlign + ' ' : '') +
              (readonly ? 'tag-disabled disabled ' : '')
            }
            onClick={this.handleProcess}
            tabIndex={tabIndex}
            ref={c => (this.rawWidget = c)}
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
            onChange={option => this.handlePatch(fields[1].field, option)}
            tabIndex={tabIndex}
            dropdownOpenCallback={dropdownOpenCallback}
            ref={c => (this.rawWidget = c)}
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
            onFocus={this.handleFocus}
            onHandleBlur={this.handleBlur}
            fieldName={widgetField}
            handleBackdropLock={handleBackdropLock}
            patch={option => this.handlePatch(widgetField, option)}
            tabIndex={tabIndex}
            autoFocus={autoFocus}
            readonly={readonly}
          />
        );
      case 'Address':
        return (
          <Attributes
            attributeType="address"
            fields={fields}
            dataId={dataId}
            widgetData={widgetData[0]}
            docType={windowType}
            tabId={tabId}
            rowId={rowId}
            fieldName={widgetField}
            handleBackdropLock={handleBackdropLock}
            patch={option => this.handlePatch(widgetField, option)}
            tabIndex={tabIndex}
            autoFocus={autoFocus}
            readonly={readonly}
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
              (gridAlign ? 'text-xs-' + gridAlign + ' ' : '') +
              (readonly ? 'tag-disabled disabled ' : '')
            }
            onClick={() => handleZoomInto(fields[0].field)}
            tabIndex={tabIndex}
            ref={c => (this.rawWidget = c)}
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
            windowType={windowType}
            viewId={viewId}
            selected={values}
            className={this.getClassNames()}
            onChange={value =>
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
    } = this.props;

    const { errorPopup } = this.state;
    const widgetBody = this.renderWidget();
    const { validStatus } = widgetData[0];

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

    const widgetFieldsName = fields
      .map(field => 'form-field-' + field.field)
      .join(' ');

    return (
      <div
        className={
          'form-group row ' +
          (rowId && !isModal ? 'form-group-table ' : ' ') +
          widgetFieldsName
        }
      >
        {captionElement || null}
        {!noLabel &&
          caption && (
            <div
              key="title"
              className={
                'form-control-label ' +
                (type === 'primary' && !oneLineException
                  ? 'col-sm-12 panel-title'
                  : type === 'primaryLongLabels' ? 'col-sm-6' : 'col-sm-3 ')
              }
              title={caption}
            >
              {fields[0].supportZoomInto ? (
                <span
                  className="zoom-into"
                  onClick={() => handleZoomInto(fields[0].field)}
                >
                  {caption}
                </span>
              ) : (
                caption
              )}
            </div>
          )}
        <div
          className={
            ((type === 'primary' || noLabel) && !oneLineException
              ? 'col-sm-12 '
              : type === 'primaryLongLabels' ? 'col-sm-6' : 'col-sm-9 ') +
            (fields[0].devices ? 'form-group-flex ' : '')
          }
          onMouseEnter={() => this.handleErrorPopup(true)}
          onMouseLeave={() => this.handleErrorPopup(false)}
        >
          <div className="input-body-container">
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
          {fields[0].devices &&
            !widgetData[0].readonly && (
              <DevicesWidget
                devices={fields[0].devices}
                tabIndex={1}
                handleChange={value =>
                  handlePatch && handlePatch(fields[0].field, value)
                }
              />
            )}
        </div>
      </div>
    );
  }
}

RawWidget.propTypes = {
  dispatch: PropTypes.func.isRequired,
  autoFocus: PropTypes.bool,
  textSelected: PropTypes.bool,
  listenOnKeys: PropTypes.bool,
  listenOnKeysFalse: PropTypes.func,
  listenOnKeysTrue: PropTypes.func,
  widgetData: PropTypes.array,
  handleFocus: PropTypes.func,
  handlePatch: PropTypes.func,
  handleBlur: PropTypes.func,
  handleProcess: PropTypes.func,
  handleChange: PropTypes.func,
  handleBackdropLock: PropTypes.func,
  tabId: PropTypes.string,
  viewId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  windowType: PropTypes.string,
  caption: PropTypes.string,
  gridAlign: PropTypes.string,
  type: PropTypes.string,
  updated: PropTypes.bool,
  isModal: PropTypes.bool,
  modalVisible: PropTypes.bool.isRequired,
  filterWidget: PropTypes.bool,
  filterId: PropTypes.string,
  id: PropTypes.number,
  range: PropTypes.bool,
  onShow: PropTypes.func,
  onHide: PropTypes.func,
  subentity: PropTypes.string,
  subentityId: PropTypes.string,
  tabIndex: PropTypes.number,
  dropdownOpenCallback: PropTypes.func,
  fullScreen: PropTypes.string,
  widgetType: PropTypes.string,
  fields: PropTypes.array,
  icon: PropTypes.string,
  entity: PropTypes.string,
  data: PropTypes.any,
  closeTableField: PropTypes.func,
  attribute: PropTypes.bool,
  allowShowPassword: PropTypes.bool, // NOTE: looks like this wasn't used
  buttonProcessId: PropTypes.string, // NOTE: looks like this wasn't used
  onBlurWidget: PropTypes.func,
  defaultValue: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
  noLabel: PropTypes.bool,
  isOpenDatePicker: PropTypes.bool,
};

RawWidget.defaultProps = {
  tabIndex: 0,
};

export default connect(state => ({
  modalVisible: state.windowHandler.modal.visible,
}))(RawWidget);
