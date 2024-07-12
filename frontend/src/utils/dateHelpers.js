/**
 * Date conversions/transforms used across the app should reside in this file
 */

import { DATE_FIELDS } from '../constants/Constants';

import { parseDateWithCurrentTimezone } from './documentListHelper';
import MomentTZ from 'moment-timezone';

/**
 * @method convertDateToReadable
 * @summary Converts the value of the corresponding widgetType to a readable string date
 * @param {*} widgetType - type of widget
 * @param {*} value - value for which we need the conversion
 */
export const convertDateToReadable = (widgetType, value) => {
  if (DATE_FIELDS.indexOf(widgetType) > -1) {
    return parseDateWithCurrentTimezone(value, widgetType);
  }
  return value;
};

export const setTimezoneToMoment = (moment, timeZone) => {
  if (!timeZone) {
    return moment;
  }

  if (moment.tz) {
    if (moment.tz() === timeZone) {
      return moment;
    } else {
      return MomentTZ(moment).tz(timeZone, true);
    }
  } else {
    const momentWithTZ = MomentTZ(
      moment.format('YYYY-MM-DDTHH:mm:ss.SSS'),
      'YYYY-MM-DDTHH:mm:ss.SSS',
      true
    );
    return momentWithTZ.tz(timeZone, true);
  }
};

export const convertMomentToTimezone = (moment, timeZone) => {
  if (!timeZone) {
    return moment;
  }

  if (moment.tz) {
    if (moment.tz() === timeZone) {
      return moment;
    } else {
      return MomentTZ(moment).tz(timeZone, false);
    }
  } else {
    const momentWithTZ = MomentTZ(
      moment.format('YYYY-MM-DDTHH:mm:ss.SSS'),
      'YYYY-MM-DDTHH:mm:ss.SSS',
      true
    );
    return momentWithTZ.tz(timeZone, false);
  }
};
