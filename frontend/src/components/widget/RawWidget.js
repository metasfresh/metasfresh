import React, { createRef, PureComponent } from 'react';
import { CSSTransition } from 'react-transition-group';
import Moment from 'moment';
import classnames from 'classnames';

import { getWidgetField, shouldPatch } from '../../utils/widgetHelpers';
import { DATE_TIMEZONE_FORMAT } from '../../constants/Constants';
import BarcodeScannerBtn from '../../components/widget/BarcodeScanner/BarcodeScannerBtn';
import WidgetRenderer from './WidgetRenderer';
import DevicesWidget from './Devices/DevicesWidget';
import Tooltips from '../tooltips/Tooltips';
import PropTypes from 'prop-types';

/**
 * @file Class based component.
 * @module RawWidget
 * @extends Component
 */
export class RawWidget extends PureComponent {
  mounted = false;

  constructor(props) {
    super(props);

    const cachedValue = RawWidget.getCachedValue(props);

    this.rawWidget = createRef(null);

    this.state = {
      isFocused: false,
      cachedValue,
      errorPopup: false,
      tooltipToggled: false,
      clearedFieldWarning: false,
    };
  }

  componentDidMount() {
    const { autoFocus, textSelected } = this.props;
    const { rawWidget } = this;

    if (rawWidget.current && autoFocus) {
      try {
        rawWidget.current.focus();
        this.setState({ isFocused: true });
      } catch (e) {
        console.error(`Custom widget doesn't have 'focus' function defined`);
      }
    }

    if (textSelected) {
      rawWidget.current.select();
    }

    this.mounted = true;
  }

  componentWillUnmount() {
    this.mounted = false;
  }

  /**
   * @method getCachedValue
   * @summary extract cached value from widget props
   *
   * @param {object} props
   */
  static getCachedValue(props) {
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

    return cachedValue;
  }

  /**
   * @method resetCachedValue
   * @summary used by parent components to force resetting cached value in case
   * there's new data but widget is reused
   */
  resetCachedValue = () => {
    const cachedValue = RawWidget.getCachedValue(this.props);

    this.setState({ cachedValue });
  };

  /**
   * @method setWidgetType
   * @summary used for password fields, when user wants to reveal the typed password
   *
   * @param {string} type - toggles between text/password
   */
  setWidgetType = (type) => (this.rawWidget.current.type = type);

  /**
   * @method showErrorPopup
   * @summary shows error message on mouse over
   */
  showErrorPopup = () => this.setState({ errorPopup: true });

  /**
   * @method hideErrorPopup
   * @summary hides error message on mouse out
   */
  hideErrorPopup = () => this.setState({ errorPopup: false });

  /**
   * @method clearFieldWarning
   * @summary Suppress showing the error message, as user already acknowledged it
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
   * @summary toggle tooltip (if it's available)
   * @param {bool} show
   */
  toggleTooltip = (show) => this.setState({ tooltipToggled: show });

  /**
   * @method handleListFocus
   * @summary Function used specifically for list widgets. It blocks outside clicks, which are
   * then enabled again in handleBlur. This is to avoid closing the list as it's a separate
   * DOM element outside of it's parent's tree.
   */
  handleListFocus = () => {
    const { handleFocus, disableOnClickOutside } = this.props;
    const { rawWidget } = this;

    if (rawWidget.current && rawWidget.current.focus) {
      rawWidget.current.focus();
    }

    disableOnClickOutside && disableOnClickOutside();
    handleFocus && handleFocus();
  };

  /**
   * @method handleFocus
   * @summary Focus handler. Disables keydown handler in the parent Table and
   * duplicated focus actions in the parent TableRow
   */
  handleFocus = () => {
    const { handleFocus, listenOnKeysFalse, disableShortcut, widgetType } =
      this.props;

    // fix issue in Cypress with cut underscores - false positive failing tests
    // - commented out because if you focus on an item and you disable the shourtcuts
    // you won't be able to use any shortcut assigned to that specific item/widget
    // - see issue https://github.com/metasfresh/metasfresh/issues/7119
    widgetType === 'LongText' && disableShortcut();

    listenOnKeysFalse && listenOnKeysFalse();

    setTimeout(() => {
      if (this.mounted) {
        this.setState({ isFocused: true }, () => handleFocus && handleFocus());
      }
    }, 0);
  };

  /**
   * @method handleBlurWithParams
   * @summary on blurring the widget field enable shortcuts/key event listeners
   * and patch the field if necessary
   *
   * @param {*} widgetField
   * @param {*} value
   * @param {*} id
   */
  handleBlurWithParams = (widgetField, value, id) => {
    const {
      allowShortcut,
      handleBlur,
      listenOnKeysTrue,
      enableOnClickOutside,
    } = this.props;
    const { isFocused } = this.state;

    if (isFocused) {
      this.setState({ isFocused: false }, () => {
        enableOnClickOutside && enableOnClickOutside();
        allowShortcut();
        handleBlur && handleBlur();
        listenOnKeysTrue && listenOnKeysTrue();

        if (widgetField) {
          this.handlePatch(widgetField, value, id);
        }
      });
    }
  };

  /**
   * @method handleBlur
   * @summary Wrapper around `handleBlurWithParams` to grab the missing
   * parameters and avoid anonymous function in event handlers
   * @param {*} e - DOM event
   */
  handleBlur = (e) => {
    const { filterWidget, fields, id } = this.props;
    const value = e.target.value;
    const widgetField = getWidgetField({ filterWidget, fields });

    this.handleBlurWithParams(widgetField, value, id);
  };

  /**
   * @method updateTypedCharacters
   * @summary updates in the state the number of charactes typed
   * @param {string} typedText
   */
  updateTypedCharacters = (typedText) => {
    const { fieldName } = this.props;
    let existingCharsTyped = { ...this.state.charsTyped };

    existingCharsTyped[fieldName] = typedText.length;
    this.setState({ charsTyped: existingCharsTyped });
  };

  /**
   * @method handleKeyDown
   * @summary key handler for the widgets. For number fields we're suppressing up/down
   *          arrows to enable table row navigation
   * @param {*} e - DOM event
   */
  handleKeyDown = (e) => {
    const { lastFormField, widgetType, filterWidget, fields, closeTableField } =
      this.props;
    const value = e.target.value;
    const { key } = e;
    const widgetField = getWidgetField({ filterWidget, fields });

    this.updateTypedCharacters(e.target.value);

    // for number fields submit them automatically on up/down arrow pressed and blur the field
    const NumberWidgets = [
      'Integer',
      'Amount',
      'Quantity',
      'Number',
      'CostPrice',
    ];
    if (
      (key === 'ArrowUp' || key === 'ArrowDown') &&
      NumberWidgets.includes(widgetType)
    ) {
      closeTableField();
      e.preventDefault();

      return this.handlePatch(widgetField, value, null, null, true);
    }

    if ((key === 'Enter' || key === 'Tab') && !e.shiftKey) {
      if (key === 'Enter' && !lastFormField) {
        e.preventDefault();
      }

      return key === 'Tab'
        ? this.handleBlur(e)
        : this.handlePatch(widgetField, value);
    }
  };

  /**
   * @method handleChange
   * @summary onChange event handler
   * @param {*} e - DOM event
   */
  handleChange = (e) => {
    const { handleChange, filterWidget, fields } = this.props;

    const widgetField = getWidgetField({ filterWidget, fields });

    if (handleChange) {
      this.updateTypedCharacters(e.target.value);
      handleChange(widgetField, e.target.value);
    }
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
    const { handlePatch, inProgress, widgetType, maxLength, widgetData } =
      this.props;
    const { cachedValue } = this.state;
    const willPatch = shouldPatch({
      property,
      value,
      valueTo,
      cachedValue,
      widgetData,
    });

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
   * @method renderErrorPopup
   * @summary this is self explanatory
   * @param {string} reason - the cause of error
   */
  renderErrorPopup = (reason) => {
    return (
      <div className="input-error-popup">{reason ? reason : 'Input error'}</div>
    );
  };

  /**
   * @method renderWidget
   * @summary Renders a single widget
   */
  renderWidget = () => {
    const {
      modalVisible,
      isModal,
      filterWidget,
      id,
      fullScreen,
      fields,
      widgetData,
      data,
      defaultValue,
      fieldName,
      maxLength,
      isFilterActive,
      suppressChange,
    } = this.props;
    let tabIndex = this.props.tabIndex;
    const { isFocused, charsTyped } = this.state;

    let widgetValue = data != null ? data : widgetData[0].value;
    if (widgetValue === null) {
      widgetValue = '';
    }

    // TODO: API SHOULD RETURN THE SAME PROPERTIES FOR FILTERS
    let widgetField = filterWidget ? fields[0].parameterName : fields[0].field;
    if (!widgetField && this.props.widgetType === 'Switch') {
      widgetField = fields[0].fields[0].field;
    }

    const readonly = widgetData[0].readonly;

    if (fullScreen || readonly || (modalVisible && !isModal)) {
      tabIndex = -1;
    }

    // TODO: this logic should be removed and adapted below after widgetType === 'MultiListValue' is added
    const isMultiselect = !!(
      widgetData[0].widgetType === 'List' && widgetData[0].multiListValue
    );

    // dev-note: avoid displaying value when hovering over password widget
    const widgetTitle =
      widgetData[0].widgetType === 'Password' ? null : widgetValue;

    const widgetProperties = {
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
      onChange: this.handleChange,
      onBlur: this.handleBlur,
      onKeyDown: this.handleKeyDown,
      title: widgetTitle,
      id,
    };
    const showErrorBorder = charsTyped && charsTyped[fieldName] > maxLength;
    const charsTypedCount = charsTyped && charsTyped[fieldName];

    return (
      <WidgetRenderer
        {...this.props}
        {...{
          readonly,
          isMultiselect,
          widgetField,
          widgetProperties,
          showErrorBorder,
          isFocused,
          isFilterActive,
          suppressChange,
        }}
        ref={this.rawWidget}
        charsTyped={charsTypedCount}
        onListFocus={this.handleListFocus}
        onBlurWithParams={this.handleBlurWithParams}
        onPatch={this.handlePatch}
        onSetWidgetType={this.setWidgetType}
        onHandleProcess={this.handleProcess}
      />
    );
  };

  /**
   * @method isScanQRbuttonPanel
   * @returns boolean value indicating that we care in the case where the widget is rendered within a panel layout and has a barcodeScannerType (qrcode)
   */
  isScanQRbuttonPanel = () => {
    const { barcodeScannerType, layoutType } = this.props;
    return barcodeScannerType === 'qrCode' && layoutType === 'panel';
  };

  /**
   * @method getAdaptedFieldColSize
   * @returns adaptive size for the case when we have barcodeScannerType and `panel` layout type
   */
  getAdaptedFieldColSize = () =>
    this.isScanQRbuttonPanel() ? 'col-sm-7' : 'col-sm-9';

  /**
   * @method onDetectedQR
   * @summary After the QR code is detected the value of the field is updated with the corresponding string
   * @param {string} qrCode
   */
  onDetectedQR = (qrCode) => {
    const { widgetField, handleChange } = this.props;
    handleChange(widgetField, qrCode);
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

    const fieldColSize = this.getAdaptedFieldColSize();

    const { errorPopup, clearedFieldWarning, tooltipToggled, isFocused } =
      this.state;
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
            : fieldColSize + ' ') +
          (fields[0].devices ? 'form-group-flex' : '');
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
            onMouseEnter={
              validStatus && !validStatus.valid
                ? this.showErrorPopup
                : undefined
            }
            onMouseLeave={this.hideErrorPopup}
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
                focused: isFocused,
              })}
              title={valueDescription}
            >
              <CSSTransition
                key={`trans_${fields[0].fieldName}`}
                className="fade"
                timeout={{ enter: 200, exit: 200 }}
              >
                <div>
                  {errorPopup &&
                    validStatus &&
                    !validStatus.valid &&
                    !validStatus.initialValue &&
                    this.renderErrorPopup(validStatus.reason)}
                </div>
              </CSSTransition>
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
          {/* this is a special case for displaying the scan button on the right side of the field */}
          {this.isScanQRbuttonPanel() && (
            <BarcodeScannerBtn postDetectionExec={this.onDetectedQR} />
          )}
        </div>
      </div>
    );
  }
}

RawWidget.propTypes = {
  inProgress: PropTypes.bool,
  autoFocus: PropTypes.bool,
  textSelected: PropTypes.bool,
  listenOnKeys: PropTypes.bool,
  widgetData: PropTypes.array,
  tabId: PropTypes.string,
  viewId: PropTypes.string,
  rowId: PropTypes.string,
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  windowType: PropTypes.string,
  fieldName: PropTypes.string,
  widgetField: PropTypes.string,
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
  subentity: PropTypes.string,
  subentityId: PropTypes.string,
  tabIndex: PropTypes.number,
  fullScreen: PropTypes.bool,
  widgetType: PropTypes.string,
  fields: PropTypes.array,
  icon: PropTypes.string,
  entity: PropTypes.string,
  data: PropTypes.any,
  attribute: PropTypes.bool,
  allowShowPassword: PropTypes.bool, // NOTE: looks like this wasn't used
  buttonProcessId: PropTypes.string, // NOTE: looks like this wasn't used
  defaultValue: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
  noLabel: PropTypes.bool,
  isOpenDatePicker: PropTypes.bool,
  forceHeight: PropTypes.number,
  dataEntry: PropTypes.bool,
  lastFormField: PropTypes.bool,
  maxLength: PropTypes.number,
  isFilterActive: PropTypes.bool,
  isEdited: PropTypes.bool,
  barcodeScannerType: PropTypes.string,
  layoutType: PropTypes.string,
  description: PropTypes.string,
  captionElement: PropTypes.string,
  fieldFormGroupClass: PropTypes.string,
  fieldLabelClass: PropTypes.string,
  fieldInputClass: PropTypes.string,

  //
  // Callbacks and other functions:
  allowShortcut: PropTypes.func.isRequired,
  disableShortcut: PropTypes.func.isRequired,
  listenOnKeysFalse: PropTypes.func,
  listenOnKeysTrue: PropTypes.func,
  enableOnClickOutside: PropTypes.func,
  disableOnClickOutside: PropTypes.func,
  handleFocus: PropTypes.func,
  handlePatch: PropTypes.func,
  handleBlur: PropTypes.func,
  onBlurWidget: PropTypes.func,
  handleProcess: PropTypes.func,
  handleChange: PropTypes.func,
  handleBackdropLock: PropTypes.func,
  handleZoomInto: PropTypes.func,
  onShow: PropTypes.func,
  onHide: PropTypes.func,
  dropdownOpenCallback: PropTypes.func,
  closeTableField: PropTypes.func,
  typeaheadSupplier: PropTypes.func,
  dropdownValuesSupplier: PropTypes.func,
};
RawWidget.defaultProps = {
  tabIndex: 0,
  handleZoomInto: () => {},
};

export default RawWidget;
