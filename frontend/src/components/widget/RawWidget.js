import React, { createRef, PureComponent } from 'react';
import { CSSTransition } from 'react-transition-group';
import Moment from 'moment';
import classnames from 'classnames';
import counterpart from 'counterpart';

import {
  getWidgetField,
  isDecimalNumberField,
  shouldPatch,
} from '../../utils/widgetHelpers';
import {
  formatDecimalNumberForEditing,
  getSessionNumberDelimiters,
  isAllowedDecimalNumberInput,
  isValidDecimalNumberString,
  normalizeDecimalNumberString,
} from '../../utils/locale';
import { addNotification } from '../../actions/AppActions';
import store from '../../store/store';
import { DATE_TIMEZONE_FORMAT } from '../../constants/Constants';
import BarcodeScannerBtn from '../../components/widget/BarcodeScanner/BarcodeScannerBtn';
import WidgetRenderer from './WidgetRenderer';
import DevicesWidget from './Devices/DevicesWidget';
import Tooltips from '../tooltips/Tooltips';
import PropTypes from 'prop-types';

/** Widget types with their own document-change focus rule; `WidgetRenderer` routes these to `Lookup`/`List`. */
const WIDGETS_WITH_OWN_FOCUS_RULE = ['Lookup', 'List', 'MultiListValue'];

/**
 * Tells whether a widget value is just what the user typed, coming back from the parent's state - and not a value from
 * outside. A document keeps the typed text as is; a filter keeps the dot-decimal, or nothing while the text is no
 * complete number.
 */
const isEchoOfTypedText = (value, typedText, isFilter) => {
  const valueStr = value == null ? '' : String(value);
  if (valueStr === typedText) {
    return true;
  } else if (isFilter) {
    const typedValue = isValidDecimalNumberString(typedText)
      ? normalizeDecimalNumberString(typedText)
      : '';
    return valueStr === (typedValue ?? '');
  } else {
    return false;
  }
};

const computeWidgetTypeClass = (widgetType, fieldsCount) => {
  if (fieldsCount > 1) {
    return 'widgetType-Composed widgetType-Composed-' + fieldsCount;
  } else {
    return 'widgetType-' + widgetType;
  }
};

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
      // a decimal number widget shows the text the user is typing, otherwise the stored value the session way
      typedText: null,
      typedTextTo: null,
      keptValueText: null, // the stored value shown again after a refused number, see refuseInvalidNumber
      errorPopup: false,
      tooltipToggled: false,
      clearedFieldWarning: false,
    };
  }

  componentDidMount() {
    const { autoFocus, textSelected } = this.props;
    const { rawWidget } = this;

    if (autoFocus) {
      this.focus();
    }

    if (textSelected) {
      rawWidget.current.select();
    }

    this.mounted = true;
  }

  componentWillUnmount() {
    this.mounted = false;
  }

  componentDidUpdate(prevProps) {
    // Reset cachedValue when the field value changes from an external source
    // (PATCH response, modal sync via mapDataToState, etc.) but NOT from the
    // user's own typing. When the user is actively editing, isFocused is true
    // and the value change comes from their handleChange → updatePropertyValue.
    if (
      !this.state.isFocused &&
      this.props.widgetData &&
      this.props.widgetData[0] &&
      prevProps.widgetData &&
      prevProps.widgetData[0] &&
      JSON.stringify(this.props.widgetData[0].value) !==
        JSON.stringify(prevProps.widgetData[0].value)
    ) {
      this.resetCachedValue();
    }

    this.forgetTypedTextOnOutsideChange(prevProps);

    // The mount-time focus above never runs again inside a mounted window, so repeat it when the
    // document changes - not while this widget holds the caret, not in a modal (there `dataId` is
    // a pinstance id, not a document), and not in a quick-input row (the header's first field is
    // the one that must get the focus).
    //
    // Lookups and dropdowns are skipped: they repeat their own rule under their own conditions,
    // and focusing them through this widget's ref would bypass those - for a composed lookup the
    // ref is not even reliably the primary sub-field.
    if (
      this.props.autoFocus &&
      !this.props.isModal &&
      this.props.subentity !== 'quickInput' &&
      !WIDGETS_WITH_OWN_FOCUS_RULE.includes(this.props.widgetType) &&
      prevProps.dataId !== this.props.dataId &&
      !this.state.isFocused
    ) {
      this.focus();
    }
  }

  focus = () => {
    const { rawWidget } = this;

    if (rawWidget.current) {
      try {
        rawWidget.current.focus();
        this.setState({ isFocused: true });
      } catch (e) {
        console.error(`Custom widget doesn't have 'focus' function defined`);
      }
    }
  };

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

    // Mark the widget focused synchronously (like `focus()` does). Otherwise, on the
    // single-click "type to edit" path, the first keystroke's value change is processed
    // while isFocused is still false, so componentDidUpdate misreads the user's own typing
    // as an external change and resets cachedValue to it — which makes the on-blur change
    // detection (shouldPatch) conclude nothing changed and skip the PATCH, silently dropping
    // the edit (most visible on single-token values such as a one-digit quantity).
    this.setState({ isFocused: true });

    // Defer only the parent focus callback, to avoid re-entrancy during the focus event.
    setTimeout(() => {
      if (this.mounted) {
        handleFocus && handleFocus();
      }
    }, 0);
  };

  /**
   * @method handleBlurWithParams
   * @summary on blurring the widget field enable shortcuts/key event listeners and patch the field if necessary
   */
  handleBlurWithParams = (widgetField, value, id, valueTo) => {
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
          this.handlePatch(widgetField, value, id, valueTo);
        }
      });
    }
  };

  /**
   * @method forgetTypedTextOnOutsideChange
   * @summary When the value of a decimal number widget changes from outside (e.g. the PATCH response), the widget shows
   *          that value again instead of what the user had typed
   */
  forgetTypedTextOnOutsideChange = (prevProps) => {
    const { widgetType, widgetData, filterWidget } = this.props;
    const { typedText, typedTextTo, keptValueText } = this.state;
    if (!isDecimalNumberField(widgetType)) {
      return;
    }

    const isChanged = (key) =>
      prevProps.widgetData?.[0]?.[key] !== widgetData?.[0]?.[key];
    const isOutsideChange = (key, text) =>
      text !== null &&
      isChanged(key) &&
      !isEchoOfTypedText(widgetData?.[0]?.[key], text, !!filterWidget);

    const isValueChanged = isOutsideChange('value', typedText);
    const isValueToChanged = isOutsideChange('valueTo', typedTextTo);
    const isKeptValueOutdated = keptValueText !== null && isChanged('value');
    if (isValueChanged || isValueToChanged || isKeptValueOutdated) {
      this.setState({
        ...(isValueChanged ? { typedText: null } : {}),
        ...(isValueToChanged ? { typedTextTo: null } : {}),
        ...(isKeptValueOutdated ? { keptValueText: null } : {}),
      });
    }
  };

  /**
   * @method getDecimalEditText
   * @summary The text a decimal number widget shows for editing: what the user typed, else the stored value with the
   *          session's decimal separator (de 3.57 -> '3,57')
   */
  getDecimalEditText = (isValueTo = false) => {
    const { typedText, typedTextTo, keptValueText } = this.state;
    const typed = isValueTo ? typedTextTo : typedText;
    if (typed !== null) {
      return typed;
    } else if (!isValueTo && keptValueText !== null) {
      return keptValueText;
    }

    const { data, widgetData } = this.props;
    const stored = isValueTo
      ? widgetData?.[0]?.valueTo
      : data != null
      ? data
      : widgetData?.[0]?.value;
    return formatDecimalNumberForEditing(stored) ?? '';
  };

  /**
   * @method isUntouchedDecimalEditText
   * @summary Tells whether a value is just the text shown for a stored value the user did not type over -
   *          leaving such a field must not patch anything
   */
  isUntouchedDecimalEditText = (value, valueTo) => {
    const { typedText, typedTextTo } = this.state;
    return (
      typedText === null &&
      typedTextTo === null &&
      value === this.getDecimalEditText(false) &&
      (valueTo == null || valueTo === this.getDecimalEditText(true))
    );
  };

  /**
   * @method getEventValues
   * @summary The value and valueTo of an input event: the edited side from the input, the other side as shown
   */
  getEventValues = (e, isValueTo) => {
    const { widgetType, widgetData, range } = this.props;
    const valueToSet = e.target.value;

    if (isDecimalNumberField(widgetType) && range) {
      return {
        value: !isValueTo ? valueToSet : this.getDecimalEditText(false),
        valueTo: isValueTo ? valueToSet : this.getDecimalEditText(true),
      };
    }
    return {
      value: !isValueTo ? valueToSet : widgetData?.[0]?.value,
      valueTo: isValueTo ? valueToSet : widgetData?.[0]?.valueTo,
    };
  };

  /**
   * @method refuseInvalidNumber
   * @summary A decimal number that is invalid with the session's separators (e.g. '3.57' in German, where the dot only
   *          groups thousands) is not patched: the user is told why, and the field shows the stored value again
   */
  refuseInvalidNumber = ({ property, id, invalidText, isValueToInvalid }) => {
    const { handleChange, filterWidget, range } = this.props;
    const keptValueText =
      formatDecimalNumberForEditing(this.state.cachedValue) ?? '';

    if (this.lastRefusedNumberText !== invalidText) {
      this.lastRefusedNumberText = invalidText;

      const { decimal, thousands } = getSessionNumberDelimiters();
      const params = {
        text: invalidText,
        decimal,
        grouping: thousands,
        example: `1${thousands}234${decimal}56`,
      };
      store.dispatch(
        addNotification(
          counterpart.translate('window.error.invalidNumber.title', {
            fallback: 'Invalid number',
          }),
          counterpart.translate('window.error.invalidNumber.description', {
            ...params,
            fallback: `"${invalidText}" was not taken over: the decimal separator is "${decimal}", "${thousands}" is allowed only to group thousands in groups of three (e.g. ${params.example}).`,
          }),
          5000,
          'error'
        )
      );
    }

    if (filterWidget) {
      // a filter keeps no value for an invalid number, so it gets the shown ends back (it reads them the session way)
      const value = isValueToInvalid
        ? this.getDecimalEditText(false)
        : keptValueText;
      const valueTo = !range
        ? undefined
        : isValueToInvalid
        ? ''
        : this.getDecimalEditText(true);
      this.setState({ typedText: null, typedTextTo: null });
      handleChange?.(property, value, id, valueTo);
    } else {
      // a document is not told: its `edited` state would then ignore later changes from outside until the next patch
      this.setState({ typedText: null, typedTextTo: null, keptValueText });
    }
  };

  /**
   * @method handleBlur
   * @summary Wrapper around `handleBlurWithParams` to grab the missing parameters and avoid anonymous function in event handlers
   */
  handleBlur = (e, isValueTo = false) => {
    const { filterWidget, fields, id } = this.props;

    const { value, valueTo } = this.getEventValues(e, isValueTo);

    const widgetField = getWidgetField({ filterWidget, fields });

    this.handleBlurWithParams(widgetField, value, id, valueTo);
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
   */
  handleKeyDown = (e, isValueTo = false) => {
    const {
      propagateEnterKeyEvent,
      widgetType,
      filterWidget,
      fields,
      closeTableField,
      id,
    } = this.props;
    const { key } = e;

    const { value, valueTo } = this.getEventValues(e, isValueTo);

    const widgetField = getWidgetField({ filterWidget, fields });

    this.updateTypedCharacters(value);

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
      closeTableField?.();
      e.preventDefault();

      return this.handlePatch(widgetField, value, id, valueTo, true);
    }

    if ((key === 'Enter' || key === 'Tab') && !e.shiftKey) {
      if (key === 'Enter' && !propagateEnterKeyEvent) {
        e.preventDefault();
      }

      return key === 'Tab'
        ? this.handleBlur(e)
        : this.handlePatch(widgetField, value, id, valueTo);
    }
  };

  handleChange = (e, isValueTo = false) => {
    const { handleChange, filterWidget, fields, id, widgetType } = this.props;
    if (!handleChange) return;

    const widgetFieldName = getWidgetField({ filterWidget, fields });

    const valueToSet = e.target.value;
    if (isDecimalNumberField(widgetType)) {
      if (!isAllowedDecimalNumberInput(valueToSet)) {
        return; // a decimal number widget is a text input, so we reject the non-numeric keystrokes (and pastes) ourselves
      }
      this.lastRefusedNumberText = null;
      this.setState({
        [isValueTo ? 'typedTextTo' : 'typedText']: valueToSet,
        keptValueText: null,
      });
    }
    // the other end of a range the way it is shown, so that the parent reads both ends the same way
    const { value, valueTo } = this.getEventValues(e, isValueTo);

    this.updateTypedCharacters(value);
    handleChange(widgetFieldName, value, id, valueTo);
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

    // the user typed the number with the separators of his locale (e.g. '3,57' in German), the backend expects '3.57'
    if (isDecimalNumberField(widgetType)) {
      if (!isForce && this.isUntouchedDecimalEditText(value, valueTo)) {
        return Promise.resolve(null);
      }

      const isValueValid = isValidDecimalNumberString(value);
      const isValueToValid = isValidDecimalNumberString(valueTo);
      if (!isValueValid || !isValueToValid) {
        this.refuseInvalidNumber({
          property,
          id,
          invalidText: isValueValid ? valueTo : value,
          isValueToInvalid: isValueValid,
        });
        return Promise.resolve(null);
      }

      value = normalizeDecimalNumberString(value);
      valueTo = normalizeDecimalNumberString(valueTo);
    }

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

    const isDecimalNumber = isDecimalNumberField(this.props.widgetType);
    const widgetProperties = {
      //autocomplete=new-password did not work in chrome for non password fields anymore,
      //switched to autocomplete=off instead
      autoComplete: 'off',
      className: 'input-field js-input-field',
      value: isDecimalNumber ? this.getDecimalEditText(false) : widgetValue,
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
          decimalRangeValues: isDecimalNumber
            ? {
                from: this.getDecimalEditText(false),
                to: this.getDecimalEditText(true),
              }
            : undefined,
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
      widgetSize,
      handleZoomInto,
      dataEntry,
      subentity,
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

    let labelClass = '';
    let fieldClass = '';
    if (quickInput) {
      labelClass = '';
      fieldClass = '';
    } else if (dataEntry) {
      labelClass = 'col-sm-5';
      fieldClass = 'col-sm-7';
    } else if ((type === 'primary' || noLabel) && !oneLineException) {
      labelClass = !noLabel ? 'col-sm-12 panel-title' : '';
      fieldClass = 'col-sm-12';
    } else if (type === 'primaryLongLabels') {
      labelClass = 'col-sm-6';
      fieldClass = 'col-sm-6';
    } else {
      labelClass = 'col-sm-3';
      fieldClass = fieldColSize;
    }

    if (fields[0].devices) {
      fieldClass += ' form-group-flex';
    }

    const labelProps = {};
    if (!noLabel && caption && fields[0].supportZoomInto) {
      labelProps.onClick = () => handleZoomInto(fields[0].field);
    }

    return (
      <div
        className={classnames(
          'form-group',
          {
            row: !quickInput,
            'form-group-table': rowId && !isModal,
          },
          computeWidgetTypeClass(widgetType, fields.length),
          widgetSize ? 'widgetSize-' + widgetSize : '',
          widgetFieldsName
        )}
      >
        {captionElement || null}
        {!noLabel && caption && (
          <label
            className={classnames('form-control-label', labelClass, {
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
            validStatus && !validStatus.valid ? this.showErrorPopup : undefined
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
  widgetSize: PropTypes.string,
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
  propagateEnterKeyEvent: PropTypes.bool,
  maxLength: PropTypes.number,
  isFilterActive: PropTypes.bool,
  isEdited: PropTypes.bool,
  barcodeScannerType: PropTypes.string,
  layoutType: PropTypes.string,
  description: PropTypes.string,
  captionElement: PropTypes.string,
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
