/**
 * Date conversions/transforms used across the app should reside in this file
 */

import { DATE_FIELDS } from '../constants/Constants';

import { parseDateWithCurrentTimezone } from './documentListHelper';

/**
 * @method convertDateToReadable
 * @summary Converts the value of the corresponding widgetType to a readable string date
 * @param {*} widgetType - type of widget
 * @param {*} value - value for which we need the conversion
 */
export function convertDateToReadable(widgetType, value) {
  if (DATE_FIELDS.indexOf(widgetType) > -1) {
    return parseDateWithCurrentTimezone(value, widgetType);
  }
  return value;
}
