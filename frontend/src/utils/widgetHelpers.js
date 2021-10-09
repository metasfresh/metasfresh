import Moment from 'moment-timezone';
import cx from 'classnames';

import {
  DATE_FORMAT,
  TIME_FORMAT,
  DATE_TIMEZONE_FORMAT,
} from '../constants/Constants';

/*
 * Helper function returning proper date field formatting depending on the
 * given widget type
 *
 * @param {string} widgetType
 * @return {string} format
 */
export function getFormatForDateField(widgetType) {
  let fmt = DATE_TIMEZONE_FORMAT;
  if (widgetType === `Date`) {
    fmt = DATE_FORMAT;
  } else if (widgetType === `Time`) {
    fmt = TIME_FORMAT;
  }

  return fmt;
}

/*
 * Helper function to turn date value into a Moment object format it.
 *
 * @param {object} value
 * @param {string} [FORMAT]
 */
export function getFormattedDate(value, FORMAT) {
  if (Moment.isMoment(value)) {
    return value.format(FORMAT);
  }
  return value ? Moment(value).format(FORMAT) : null;
}

/*
 * This function is to be binded to a class instance, so that we don't have to pass
 * a dozen of parameters but use props instead.
 *
 * @param {string} icon
 * @param {boolean} forcedPrimary
 */
export function getClassNames({ icon, forcedPrimary } = {}) {
  const { widgetData, gridAlign, type, updated, rowId, isModal, isFocused } =
    this.props;
  const { readonly, value, mandatory, validStatus } = widgetData[0];

  const ret = cx(`input-block`, {
    'input-focused': isFocused,
    'input-icon-container': icon,
    'input-disabled': readonly,
    'input-mandatory': mandatory && (value ? value.length === 0 : value !== 0),
    'input-error':
      validStatus &&
      !validStatus.valid &&
      !validStatus.initialValue &&
      !isFocused,
    [`text-xs-${gridAlign}`]: gridAlign,
    [`input-${
      type === 'primary' || forcedPrimary ? 'primary' : 'secondary'
    }`]: true,
    [`pulse-${updated ? 'on' : 'off'}`]: true,
    'input-table': rowId && !isModal,
  });

  return ret;
}

/**
 * @method isNumberField
 * @summary verifies if the widgetType passed as argument is a number field or not. Returns a boolean value
 * @param {string} widgetType
 */
export function isNumberField(widgetType) {
  switch (widgetType) {
    case 'Integer':
    case 'Amount':
    case 'Quantity':
      return true;
    default:
      return false;
  }
}

/**
 * @method formatValueByWidgetType
 * @summary Performs patching at MasterWidget level, shaping in the same time the `value` for various cases
 * @param {string} widgetType
 * @param {string|undefined} value
 */
export function formatValueByWidgetType({ widgetType, value }) {
  const numberField = isNumberField(widgetType);
  if (widgetType === 'Quantity' && value === '') {
    return null;
  } else if (numberField && !value) {
    return '0';
  }
  return value;
}

/**
 * @method validatePrecision
 * @summary Validates the precision based on the widget value and type props
 * @param {string} widgetValue
 * @param {string} widgetType
 * @param {integer} precision
 */
export function validatePrecision({
  widgetValue,
  widgetType,
  precision,
  fieldName,
}) {
  let precisionProcessed = precision;

  if (fieldName === 'qtyToDeliverCatchOverride') return true; // hotfix for catchWeight

  if (!widgetValue) {
    return false;
  }
  if (widgetValue && typeof widgetValue !== 'string') {
    return false;
  }

  if (widgetType === 'Integer' || widgetType === 'Quantity') {
    precisionProcessed = 0;
  }

  return precisionProcessed < (widgetValue.split('.')[1] || []).length
    ? false
    : true;
}

/**
 * @method shouldPatch
 * @summary Checks if the value has actually changed between what was cached before.
 *
 * @param {string} property
 * @param {*} value
 * @param {*} valueTo
 * @param {array} widgetData
 * @param {*} cachedValue - current widget value for the `property` field
 */
export function shouldPatch({
  property,
  value,
  valueTo,
  widgetData,
  cachedValue,
}) {
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
}

/**
 * @method getWidgetField
 * @summary Returns name of the widget
 *
 * @param {boolean} filterWidget - flag if widget is a filter
 * @param {array} fields - widget fields array
 */
export function getWidgetField({ filterWidget = false, fields }) {
  return filterWidget ? fields[0].parameterName : fields[0].field;
}
