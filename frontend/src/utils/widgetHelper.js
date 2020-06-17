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
