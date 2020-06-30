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
export function validatePrecision({ widgetValue, widgetType, precision }) {
  let precisionProcessed = precision;

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
